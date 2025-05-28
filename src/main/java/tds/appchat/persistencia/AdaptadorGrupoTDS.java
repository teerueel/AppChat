package tds.appchat.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.Grupo;

public class AdaptadorGrupoTDS extends AdaptadorContactoTDSBase {

	private static AdaptadorGrupoTDS instancia;

	public static AdaptadorGrupoTDS getInstance() {
		if (instancia == null) {
			instancia = new AdaptadorGrupoTDS();

		}
		return instancia;
	}

	private AdaptadorGrupoTDS() {
		super();
	}

	@Override
	protected Entidad crearEntidad(Contacto contacto) {
		Entidad eGrupo = new Entidad();
		eGrupo.setNombre("Contacto");

		eGrupo.setPropiedades(
				new ArrayList<beans.Propiedad>(Arrays.asList(new beans.Propiedad("nombre", contacto.getNombre()),
						new beans.Propiedad("contactos", obtenerCodigo(contacto)),
						new beans.Propiedad("imagen", contacto.getImagen()))));
		return eGrupo;
	}

	@Override
	protected Contacto construirContactoDesdeEntidad(Entidad entidad) {
		List<Contacto> participantes = new ArrayList<Contacto>();
		String nombre, imagen;

		// recuperar propiedades que no son objetos

		nombre = servPersistencia.recuperarPropiedadEntidad(entidad, "nombre");
		imagen = servPersistencia.recuperarPropiedadEntidad(entidad, "imagen");

		Grupo grupo = new Grupo(nombre, imagen);
		
		// Recuperar contactos del grupo
		
		participantes = obtenerContactosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(entidad, "contactos"));
		for (Contacto participante : participantes) {
			grupo.agregarContacto(participante);
		}

		return grupo;
	}
	
	@Override
	protected String obtenerCodigo(Contacto contacto) {
		String lineas = "";
		for (Contacto participante : ((Grupo) contacto).getContactos()) {
			lineas += participante.getId() + " ";
		}
		return lineas.trim();
	}

	// ---------------Funciones auxiliares------------------//

	private List<Contacto> obtenerContactosDesdeCodigos(String codigoContactos) {
		List<Contacto> contactos = new LinkedList<Contacto>();
		StringTokenizer st = new StringTokenizer(codigoContactos, " ");
		AdaptadorContactoIndividualTDS adaptadorContacto = AdaptadorContactoIndividualTDS.getInstance();
		while (st.hasMoreTokens()) {
			contactos.add(adaptadorContacto.obtenerContacto(Integer.valueOf((String) st.nextElement())));
		}
		return contactos;
	}

}
