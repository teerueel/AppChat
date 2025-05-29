package tds.appchat.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import tds.appchat.modelo.*;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.ContactoIndividual;
import tds.appchat.modelo.contactos.Grupo;
import tds.appchat.modelo.util.TipoMensaje;
import tds.appchat.persistencia.DAOException;
import tds.appchat.persistencia.FactoriaDAO;
import tds.appchat.persistencia.IAdaptadorContactoDAO;
import tds.appchat.persistencia.IAdaptadorMensajeDAO;
import tds.appchat.persistencia.IAdaptadorUsuarioDAO;
import tds.appchat.repositorio.*;
import tds.appchat.sesion.Sesion;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.util.SelectorImagen;

public enum Controlador {
	INSTANCIA;

	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoDAO adaptadorContacto;
	private IAdaptadorContactoDAO adaptadorGrupo;
	private IAdaptadorMensajeDAO adaptadorMensaje;

	/**
	 * Constructor privado para el singleton
	 */
	Controlador() {
		// Inicializar el controlador si es necesario
		inicializarAdaptadores();

	}

	/**
	 * Registra un nuevo usuario en el sistema
	 * 
	 * @param email     Email del usuario
	 * @param nombre    Nombre del usuario
	 * @param password  Contraseña del usuario
	 * @param esCreador Indica si el usuario tiene rol creador
	 * @return true si el registro fue exitoso, false en caso contrario
	 */

	// Se utiliza para registrar un nuevo usuario en el sistema
	public boolean registrarUsuario(String email, String nombre, String password, String tlf, String imagen,
			String saludo) {
		Usuario user = new Usuario(nombre, email, password, tlf, saludo, imagen);
		adaptadorUsuario.registrarUsuario(user);
		CatalogoUsuarios.INSTANCIA.addUsuario(user);
		return true;
	}

	// Se utiliza para saber si un telefono ya está registrado en el sistema
	public boolean tlfRegistrado(String tlf) {
		return CatalogoUsuarios.INSTANCIA.tlfRegistrado(tlf);
	}

	// Se utiliza para saber si un email ya está registrado en el sistema
	public boolean emailRegistrado(String email) {
		return CatalogoUsuarios.INSTANCIA.emailRegistrado(email);
	}

	public String seleccionarImagenPerfil() {
		return SelectorImagen.INSTANCIA.seleccionarImagenPerfil();
	}

	public boolean iniciarSesion(String tlf, String password) {
		if (CatalogoUsuarios.INSTANCIA.autenticarUsuario(tlf, password).isPresent()) {
			Sesion.INSTANCIA.setUsuarioActual(CatalogoUsuarios.INSTANCIA.autenticarUsuario(tlf, password).get());
			return true;
		}
		return false;
	}

	public void cerrarSesion() {
		Sesion.INSTANCIA.cerrarSesion();
	}

	public boolean haySesion() {
		return Sesion.INSTANCIA.haySesion();
	}

	public void actualizarTiempoUso(long tiempo) {
		if (Sesion.INSTANCIA.haySesion()) {
			System.out.println("Tiempo de uso: " + tiempo);
			Sesion.INSTANCIA.getUsuarioActual().getStats().aumentarTiempoUso(tiempo);
			Sesion.INSTANCIA.setTiempoInicioSesion(System.currentTimeMillis());
		}
	}

	public boolean nuevoContacto(String nombre, String telefono) {
		if (Sesion.INSTANCIA.getUsuarioActual().contactoRegistrado(telefono).isPresent()) {
			return false;
		}
		if (!CatalogoUsuarios.INSTANCIA.tlfRegistrado(telefono)) {
			return false;
		}
		ContactoIndividual contactoActual = new ContactoIndividual(nombre);
		Usuario user = CatalogoUsuarios.INSTANCIA.buscarUsuario(telefono).get();
		contactoActual.setIdUsuario(user.getId());
		adaptadorContacto.registrarContacto(contactoActual);
		Sesion.INSTANCIA.getUsuarioActual().addContacto(contactoActual);
		adaptadorUsuario.modificarUsuario(Sesion.INSTANCIA.getUsuarioActual());
		return true;
	}

	public boolean nuevoGrupo(String nombre, String imagen, List<Contacto> contactos) {
		if (Sesion.INSTANCIA.getUsuarioActual().grupoRegistrado(nombre).isPresent()) {
			return false;
		}
		Grupo grupo = new Grupo(nombre, imagen, contactos);
		adaptadorGrupo.registrarContacto(grupo);
		Sesion.INSTANCIA.getUsuarioActual().addGrupo(grupo);
		adaptadorUsuario.modificarUsuario(Sesion.INSTANCIA.getUsuarioActual());
		return true;
	}

	public void agregarContactosGrupo(List<Contacto> contactos, Grupo grupo) {
		if (contactos.isEmpty()) {
			return;
		}
		grupo.agregarContactos(contactos);
		adaptadorGrupo.modificarContacto(grupo);
	}

	public void eliminarContactos(List<Contacto> contactos, Grupo grupo) {
		grupo.eliminarContactos(contactos);
		adaptadorGrupo.modificarContacto(grupo);
	}

	public void eliminarContacto(Contacto contacto) {
		adaptadorContacto.eliminarContacto(contacto);
		Sesion.INSTANCIA.getUsuarioActual().eliminarContacto(contacto);
		adaptadorUsuario.modificarUsuario(Sesion.INSTANCIA.getUsuarioActual());
	}

	public List<Contacto> getContactosRestantes(Grupo grupo) {
		if (grupo == null) {
			return Sesion.INSTANCIA.getUsuarioActual().getContactosIndividuales();
		}
		return Sesion.INSTANCIA.getUsuarioActual().getContactosIndividuales().stream()
				.filter(c -> !grupo.getContactos().contains(c) && !c.equals(grupo)).toList();
	}

	public Map<Contacto, Mensaje> getUltimosMensajes() {
		if (!Sesion.INSTANCIA.haySesion()) {
			return null;
		}
		return Sesion.INSTANCIA.getUsuarioActual().getUltimosMensajes();
	}

	public void enviarMensaje(String texto, Contacto seleccionado) {
		if (seleccionado == null) {
			return;
		}
		Mensaje mensaje = new Mensaje(texto, TipoMensaje.ENVIADO);
		adaptadorMensaje.registrarMensaje(mensaje);
		Mensaje mensajeRecibido = new Mensaje(texto, TipoMensaje.RECIBIDO);

		if (seleccionado instanceof ContactoIndividual) {
			seleccionado.agregarMensaje(mensaje);
			adaptadorContacto.modificarContacto(seleccionado);
			recibirMensaje(mensajeRecibido, seleccionado);

		}

		else if (seleccionado instanceof Grupo) {
			Grupo grupo = (Grupo) seleccionado;
			grupo.getContactos().stream().forEach(c -> {
					c.agregarMensaje(mensaje);
					adaptadorContacto.modificarContacto(c);
					recibirMensaje(mensajeRecibido, c);
					
				});
			adaptadorGrupo.modificarContacto(grupo);
		}
		GestorVentanas.INSTANCIA.getVentanaApp().updatePanelIzquierdo();
	}

	public void enviarEmoji(int emoji, Contacto seleccionado) {
		if (seleccionado == null) {
			return;
		}
		Mensaje mensaje = new Mensaje(emoji, TipoMensaje.ENVIADO);
		adaptadorMensaje.registrarMensaje(mensaje);
		Mensaje mensajeRecibido = new Mensaje(emoji, TipoMensaje.RECIBIDO);

		if (seleccionado instanceof ContactoIndividual) {
			seleccionado.agregarEmoji(mensaje);
			adaptadorContacto.modificarContacto(seleccionado);
			recibirEmoji(mensajeRecibido, seleccionado);

		}

		else if (seleccionado instanceof Grupo) {
			Grupo grupo = (Grupo) seleccionado;

			grupo.getContactos().stream().forEach(c -> {
					c.agregarEmoji(mensaje);
					adaptadorContacto.modificarContacto(c);
					recibirEmoji(mensajeRecibido, c);
				});
			adaptadorGrupo.modificarContacto(grupo);
		}
		GestorVentanas.INSTANCIA.getVentanaApp().updatePanelIzquierdo();
	}

	public void recibirMensaje(Mensaje mensaje, Contacto contacto) {

		Usuario user = null;
		if (CatalogoUsuarios.INSTANCIA.buscarUsuario(contacto.getTelefono()).isPresent()) {
			user = CatalogoUsuarios.INSTANCIA.buscarUsuario(contacto.getTelefono()).get();
		}
		Optional<Contacto> c = user.contactoRegistrado(Sesion.INSTANCIA.getUsuarioActual().getTelefono());
		if (c.isPresent()) {
			adaptadorMensaje.registrarMensaje(mensaje);
			c.get().agregarMensaje(mensaje);
			adaptadorContacto.modificarContacto(c.get());

		} else {
			Contacto nuevoContacto = new ContactoIndividual(Sesion.INSTANCIA.getUsuarioActual().getId(),
					Sesion.INSTANCIA.getUsuarioActual().getTelefono(), false);
			adaptadorContacto.registrarContacto(nuevoContacto);
			adaptadorMensaje.registrarMensaje(mensaje);
			nuevoContacto.agregarMensaje(mensaje);
			adaptadorContacto.modificarContacto(nuevoContacto);
			user.addContacto(nuevoContacto);
			adaptadorUsuario.modificarUsuario(user);
		}

	}

	public void recibirEmoji(Mensaje mensaje, Contacto contacto) {

		Usuario user = null;
		if (CatalogoUsuarios.INSTANCIA.buscarUsuario(contacto.getTelefono()).isPresent()) {
			user = CatalogoUsuarios.INSTANCIA.buscarUsuario(contacto.getTelefono()).get();
		}
		Optional<Contacto> c = user.contactoRegistrado(Sesion.INSTANCIA.getUsuarioActual().getTelefono());
		if (c.isPresent()) {
			adaptadorMensaje.registrarMensaje(mensaje);
			c.get().agregarEmoji(mensaje);
			adaptadorContacto.modificarContacto(c.get());

		} else {
			Contacto nuevoContacto = new ContactoIndividual(Sesion.INSTANCIA.getUsuarioActual().getId(),
					Sesion.INSTANCIA.getUsuarioActual().getTelefono(), false);
			adaptadorContacto.registrarContacto(nuevoContacto);
			adaptadorMensaje.registrarMensaje(mensaje);
			nuevoContacto.agregarEmoji(mensaje);
			adaptadorContacto.modificarContacto(nuevoContacto);
			user.addContacto(nuevoContacto);
			adaptadorUsuario.modificarUsuario(user);
		}

	}

	public void agregarContacto(String nombre, Contacto contacto) {
		contacto.setNombre(nombre);
		contacto.setAgregado(true);
		adaptadorContacto.modificarContacto(contacto);

	}
	
	public List<String> buscarMensajes(String txt, String tlf, String nombre){
		List<String> listaMensajes = new ArrayList<>();
		listaMensajes.add("mensajin");
		return listaMensajes;
	}

	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorContacto = factoria.getContactoDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();

	}

}
