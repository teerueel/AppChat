package tds.appchat.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beans.Entidad;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.ContactoIndividual;
import tds.appchat.modelo.contactos.Grupo;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorGrupoTDS implements IAdaptadorContactoDAO {

	private static ServicioPersistencia servPersistencia;

	private static AdaptadorGrupoTDS instancia;

	public static AdaptadorGrupoTDS getInstance() {
		if (instancia == null) {
			instancia = new AdaptadorGrupoTDS();

		}
		return instancia;
	}

	private AdaptadorGrupoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void registrarContacto(Contacto contacto) {
		Entidad eGrupo = null;
		try {
			eGrupo = servPersistencia.recuperarEntidad(contacto.getId());
		} catch (NullPointerException e) {
			// La entidad no está registrada, se puede registrar
		}
		if (eGrupo != null)
			return;

		eGrupo = new Entidad();
		eGrupo.setNombre("Contacto");
		eGrupo.setPropiedades(
				new ArrayList<beans.Propiedad>(Arrays.asList(new beans.Propiedad("nombre", contacto.getNombre()),
						new beans.Propiedad("contactos", obtenerCodigoContactos(((Grupo) contacto).getContactos())),
						new beans.Propiedad("imagen", contacto.getImagen()))));
		eGrupo = servPersistencia.registrarEntidad(eGrupo);
		contacto.setId(eGrupo.getId());
	}

	@Override
	public void eliminarContacto(Contacto contacto) {

	}

	@Override
	public void modificarContacto(Contacto contacto) {

	}

	@Override
	public Grupo obtenerContacto(int id) {
		return null;
	}

	@Override
	public List<Contacto> obtenerContactos() {
		return null;
	}

	// ---------------Funciones auxiliares------------------//

	private String obtenerCodigoContactos(List<Contacto> contactos) {
		String lineas = "";
		for (Contacto contacto : contactos) {
			lineas += contacto.getId() + " ";
		}
		return lineas.trim();
	}

}
