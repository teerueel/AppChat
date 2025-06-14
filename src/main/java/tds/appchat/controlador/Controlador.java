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

	// Se utiliza para iniciar sesión en el sistema
	public boolean iniciarSesion(String tlf, String password) {
		if (CatalogoUsuarios.INSTANCIA.autenticarUsuario(tlf, password).isPresent()) {
			Sesion.INSTANCIA.setUsuarioActual(CatalogoUsuarios.INSTANCIA.autenticarUsuario(tlf, password).get());
			GestorVentanas.INSTANCIA.inicializarTrasRegistro();
			return true;
		}
		return false;
	}
	// Se utiliza para cerrar sesión en el sistema
	public void cerrarSesion() {
		Sesion.INSTANCIA.cerrarSesion();
	}
	
	// Se utiliza para saber si hay una sesión iniciada
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
	// Crea un nuevo contacto individual y lo añade a la lista de contactos del usuario actual
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

	// Crea un nuevo grupo y lo añade a la lista de grupos del usuario actual
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

	// Añade contactos al grupo
	public void agregarContactosGrupo(List<Contacto> contactos, Grupo grupo) {
		if (contactos.isEmpty()) {
			return;
		}
		grupo.agregarContactos(contactos);
		adaptadorGrupo.modificarContacto(grupo);
	}
	// Elimina ciertos contactos de un grupo y actualiza el grupo en la base de datos
	public void eliminarContactos(List<Contacto> contactos, Grupo grupo) {
		grupo.eliminarContactos(contactos);
		adaptadorGrupo.modificarContacto(grupo);
	}

	// Elimina un contacto individual de la lista de contactos del usuario actual y lo elimina de la base de datos
	public void eliminarContacto(Contacto contacto) {
		adaptadorContacto.eliminarContacto(contacto);
		Sesion.INSTANCIA.getUsuarioActual().eliminarContacto(contacto);
		adaptadorUsuario.modificarUsuario(Sesion.INSTANCIA.getUsuarioActual());
	}

	// Devuelve una lista con los contactos restantes que no están en el grupo
	public List<Contacto> getContactosRestantes(Grupo grupo) {
		return Sesion.INSTANCIA.getUsuarioActual().getContactosRestantes(grupo);
		
	}

	// Sirve para dar un nombre a un contacto no agregado.
	public void marcarAgregadoContacto(String nombre, Contacto contacto) {
		contacto.setNombre(nombre);
		contacto.setAgregado(true);
		adaptadorContacto.modificarContacto(contacto);

	}

	
	public Map<Contacto, Mensaje> getUltimosMensajes() {
		if (!Sesion.INSTANCIA.haySesion()) {
			return null;
		}
		return Sesion.INSTANCIA.getUsuarioActual().getUltimosMensajes();
	}

	// Envía un mensaje de texto a un contacto individual o a un grupo.
	public void enviarMensaje(String texto, Contacto seleccionado) {
		if (seleccionado == null) {
			return;
		}
		Mensaje mensaje = new Mensaje(texto, TipoMensaje.ENVIADO);
		adaptadorMensaje.registrarMensaje(mensaje);
		Mensaje mensajeRecibido = new Mensaje(texto, TipoMensaje.RECIBIDO);

		seleccionado.agregarMensaje(mensaje);
		for(Contacto c : seleccionado.getContactos()) {
			adaptadorContacto.modificarContacto(c);
			recibirMensaje(mensajeRecibido, c);
		}
		if(seleccionado.getContactos().size() > 1) {
			adaptadorGrupo.modificarContacto((Grupo) seleccionado);
		}

		GestorVentanas.INSTANCIA.getVentanaApp().updatePanelIzquierdo();
	}

	// Envía un emoji a un contacto individual o a un grupo.
	public void enviarEmoji(int emoji, Contacto seleccionado) {
		if (seleccionado == null) {
			return;
		}
		Mensaje mensaje = new Mensaje(emoji, TipoMensaje.ENVIADO);
		adaptadorMensaje.registrarMensaje(mensaje);
		Mensaje mensajeRecibido = new Mensaje(emoji, TipoMensaje.RECIBIDO);

		seleccionado.agregarEmoji(mensaje);
		for(Contacto c : seleccionado.getContactos()) {
			adaptadorContacto.modificarContacto(c);
			recibirEmoji(mensajeRecibido, c);
		}
		if(seleccionado.getContactos().size() > 1) {
			adaptadorGrupo.modificarContacto((Grupo) seleccionado);
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

	

	public List<String> buscarMensajes(String txt, String tlf, String nombre, String tipo) {

		List<Contacto> listaContactos;
		List<Mensaje> mensajesContacto;
		// Primero obtenemos todos los mensajes del contacto indicado, o de todos los contactos si
		// el teléfono y el nombre están vacíos
		
		listaContactos = Sesion.INSTANCIA.getUsuarioActual().buscarContacto(tlf, nombre);
		mensajesContacto = listaContactos.stream()
				.map(c -> c.getMensajes()).flatMap(List::stream).toList();

		// Ahora filtramos los mensajes del contacto según su tipo y si contienenen la cadena de texto deseada
		// y los convertimos a su propio contenido en String
		List<String> listaMensajes;

		if (tipo.equals("Ambos")) {
			listaMensajes = mensajesContacto.stream().filter(m -> m.getTexto().contains(txt))
					.map(m -> m.getTexto()).toList();
		} else {
			listaMensajes = mensajesContacto.stream().filter(m -> m.getTexto().contains(txt))
					.filter(m -> m.getTipo().toString().toLowerCase().equals(tipo.toLowerCase())).map(m -> m.getTexto())
					.toList();
		}
		
		return listaMensajes;
	}
	
	public void setPremium(boolean premium) {
		Sesion.INSTANCIA.getUsuarioActual().setPremium(premium);
		adaptadorUsuario.modificarUsuario(Sesion.INSTANCIA.getUsuarioActual());
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
