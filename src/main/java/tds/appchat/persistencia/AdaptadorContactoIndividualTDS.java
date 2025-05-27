package tds.appchat.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.ContactoIndividual;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorContactoIndividualTDS implements IAdaptadorContactoDAO {

    private static ServicioPersistencia servPersistencia;

    private static AdaptadorContactoIndividualTDS instancia;

    public static AdaptadorContactoIndividualTDS getInstance() {
        if (instancia == null) {
            instancia = new AdaptadorContactoIndividualTDS();
            
        }
        return instancia;
    }

    private AdaptadorContactoIndividualTDS() {
        servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
    }
    
    @Override
    public void registrarContacto(Contacto contacto) {
        // Implementación para registrar un contacto en TDS
        Entidad eContacto = null;
        try {
            eContacto = servPersistencia.recuperarEntidad(contacto.getId());
        } catch (NullPointerException e) {
            // La entidad no está registrada, se puede registrar
        }
        if (eContacto != null) return;

         AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getInstance();
        for (Mensaje mensaje : contacto.getMensajes()) {
            adaptadorMensaje.registrarMensaje(mensaje);
        } 

        // Crear la entidad de contacto
        eContacto = new Entidad();

        eContacto.setNombre("Contacto");
        eContacto.setPropiedades(new ArrayList<beans.Propiedad>(
                Arrays.asList(
                        new beans.Propiedad("nombre", contacto.getNombre()),
                        new beans.Propiedad("agregado", String.valueOf(contacto.isAgregado())),
                        new beans.Propiedad("mensajes", obtenerCodigoMensajes(contacto.getMensajes())),
                        new beans.Propiedad("usuario", Integer.toString(((ContactoIndividual) contacto).getIdUsuario())))));
        				new beans.Propiedad("imagen", contacto.getImagen());
        eContacto = servPersistencia.registrarEntidad(eContacto);
        contacto.setId(eContacto.getId());
        
        
    }

    @Override
    public void eliminarContacto(Contacto contacto) {
        // Implementación para eliminar un contacto en TDS
        Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getId());
        servPersistencia.borrarEntidad(eContacto);
    }

    @Override
    public void modificarContacto(Contacto contacto) {
        // Implementación para modificar un contacto en TDS
        Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getId());

        for(Propiedad p : eContacto.getPropiedades()) {
            if(p.getNombre().equals("nombre")) {
                p.setValor(contacto.getNombre());
            } else if(p.getNombre().equals("agregado")) {
                p.setValor(String.valueOf(contacto.isAgregado()));
            } else if(p.getNombre().equals("mensajes")) {
                p.setValor(obtenerCodigoMensajes(contacto.getMensajes()));
            }
            servPersistencia.modificarPropiedad(p);
        }
    }

    @Override
    public ContactoIndividual obtenerContacto(int id) {
        // Implementación para obtener un contacto por ID en TDS
        
        if (PoolDAO.INSTANCIA.contiene(id)) {
            return (ContactoIndividual) PoolDAO.INSTANCIA.getObjeto(id);
        }

        Entidad eContacto;
        List<Mensaje> mensajes = new ArrayList<Mensaje>();
        String nombre;
        boolean agregado;
        int idUsuario;

        eContacto = servPersistencia.recuperarEntidad(id);

        //recuperar propiedades que no son objetos

        
        nombre = servPersistencia.recuperarPropiedadEntidad(eContacto, "nombre");
    
        agregado = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eContacto, "agregado"));
        idUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContacto, "usuario"));

        ContactoIndividual contacto = new ContactoIndividual(idUsuario, nombre, agregado);
        contacto.setId(id);
        
        PoolDAO.INSTANCIA.addObjeto(id,contacto);

        // Recuperar mensajes
            mensajes = obtenerMensajesDesdeCodigos(
               servPersistencia.recuperarPropiedadEntidad(eContacto, "mensajes"));
        for (Mensaje mensaje : mensajes) {
            contacto.agregarMensaje(mensaje);
         }

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

    //---------------Funciones auxiliares------------------//

    private String obtenerCodigoMensajes(List<Mensaje> mensajes) {
        String lineas = "";
        for (Mensaje mensaje : mensajes) {
           lineas += mensaje.getId() + " ";
        }
        return lineas.trim();
    }
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
