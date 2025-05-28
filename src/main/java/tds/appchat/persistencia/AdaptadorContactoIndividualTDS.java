package tds.appchat.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.ContactoIndividual;

public class AdaptadorContactoIndividualTDS extends AdaptadorContactoTDSBase {

	private static AdaptadorContactoIndividualTDS instancia;

	public static AdaptadorContactoIndividualTDS getInstance() {
		if (instancia == null) {
			instancia = new AdaptadorContactoIndividualTDS();

		}
		return instancia;
	}

	private AdaptadorContactoIndividualTDS() {
		super();
	}

	@Override
	protected Entidad crearEntidad(Contacto contacto) {
		Entidad eContacto = new Entidad();

		eContacto.setNombre("Contacto");
		eContacto.setPropiedades(new ArrayList<beans.Propiedad>(Arrays.asList(
				new beans.Propiedad("nombre", contacto.getNombre()),
				new beans.Propiedad("agregado", String.valueOf(contacto.isAgregado())),
				new beans.Propiedad("mensajes", obtenerCodigo(contacto)),
				new beans.Propiedad("usuario", Integer.toString(((ContactoIndividual) contacto).getIdUsuario())))));
		return eContacto;
	}

	@Override
	protected Contacto construirContactoDesdeEntidad(Entidad entidad) {
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		String nombre;
		boolean agregado;
		int idUsuario;
		// recuperar propiedades que no son objetos

		nombre = servPersistencia.recuperarPropiedadEntidad(entidad, "nombre");
		agregado = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(entidad, "agregado"));
		
		idUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(entidad, "usuario"));

		ContactoIndividual contacto = new ContactoIndividual(idUsuario, nombre, agregado);
		// Recuperar mensajes
		mensajes = obtenerMensajesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(entidad, "mensajes"));
		for (Mensaje mensaje : mensajes) {
			contacto.agregarMensaje(mensaje);
		}

		return contacto;
	}

	@Override
	protected String obtenerCodigo(Contacto contacto) {
		String lineas = "";
		for (Mensaje mensaje : ((ContactoIndividual) contacto).getMensajes()) {
			lineas += mensaje.getId() + " ";
		}
		return lineas.trim();
	}

	// ---------------Funciones auxiliares------------------//

	private List<Mensaje> obtenerMensajesDesdeCodigos(String codigos) {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		StringTokenizer st = new StringTokenizer(codigos, " ");
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getInstance();
		while (st.hasMoreTokens()) {
			mensajes.add(adaptadorMensaje.obtenerMensaje(Integer.valueOf((String) st.nextElement())));
		}
		return mensajes;
	}

}
