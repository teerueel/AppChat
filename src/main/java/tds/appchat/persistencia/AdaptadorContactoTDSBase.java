package tds.appchat.persistencia;

import java.util.ArrayList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.appchat.modelo.contactos.Contacto;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public abstract class AdaptadorContactoTDSBase implements IAdaptadorContactoDAO{
	
	protected static ServicioPersistencia servPersistencia;

	public AdaptadorContactoTDSBase() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void registrarContacto(Contacto contacto) {
		Entidad eContacto = null;
		try {
			eContacto = servPersistencia.recuperarEntidad(contacto.getId());
		} catch (NullPointerException e) {
			// La entidad no está registrada, se puede registrar
		}
		if (eContacto != null)
			return;
		
		eContacto = crearEntidad(contacto);
        eContacto = servPersistencia.registrarEntidad(eContacto);
		contacto.setId(eContacto.getId());
	}
	
	@Override
	public void modificarContacto(Contacto contacto) {
		Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getId());

		for (Propiedad p : eContacto.getPropiedades()) {
			if (p.getNombre().equals("nombre")) {
				p.setValor(contacto.getNombre());
			} else if (p.getNombre().equals("imagen")) {
				p.setValor(contacto.getImagen());
			} else if (p.getNombre().equals("mensajes")) {
				p.setValor(obtenerCodigo(contacto));
			} else if(p.getNombre().equals("agregado")) {
                p.setValor(String.valueOf(contacto.isAgregado()));
            } else if(p.getNombre().equals("contactos")) {
                p.setValor(obtenerCodigo(contacto));
            }
			servPersistencia.modificarPropiedad(p);
		}
	}
	
	@Override
	public void eliminarContacto(Contacto contacto) {
		Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getId());
		servPersistencia.borrarEntidad(eContacto);
	}
	
	@Override
    public final Contacto obtenerContacto(int id) {
        if (PoolDAO.INSTANCIA.contiene(id)) {
            return (Contacto) PoolDAO.INSTANCIA.getObjeto(id);
        }

        Entidad entidad = servPersistencia.recuperarEntidad(id);

        Contacto contacto = construirContactoDesdeEntidad(entidad);
        contacto.setId(id);

        PoolDAO.INSTANCIA.addObjeto(id, contacto);
        return contacto;
    }
	
	@Override
    public List<Contacto> obtenerContactos() {
        // Implementación para obtener todos los contactos en TDS
        List<Contacto> contactos = new ArrayList<Contacto>();
        List<Entidad> entidades = servPersistencia.recuperarEntidades("Contacto");
        for (Entidad entidad : entidades) {
            contactos.add(obtenerContacto(entidad.getId()));
        }
        return contactos;
    }
	
	// Métodos que cada subclase implementa con sus detalles
    protected abstract Contacto construirContactoDesdeEntidad(Entidad entidad);
	protected abstract Entidad crearEntidad(Contacto contacto);
	protected abstract String obtenerCodigo(Contacto contacto);
	
	
}
