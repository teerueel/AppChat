package tds.appchat.persistencia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.util.TipoMensaje;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorMensajeTDS implements IAdaptadorMensajeDAO {
    
    private ServicioPersistencia servPersistencia;
    private static AdaptadorMensajeTDS instance;

    public static AdaptadorMensajeTDS getInstance() {
        if (instance == null) {
            instance = new AdaptadorMensajeTDS();
        }
        return instance;
    }

    private AdaptadorMensajeTDS() {
        this.servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
    }
    @Override
    public void registrarMensaje(Mensaje mensaje) {
        // Lógica para registrar un mensaje en la base de datos
        Entidad eMensaje = null;
        try {
            eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
        } catch (NullPointerException e) {
            // La entidad no está registrada, se puede registrar
        }
        if (eMensaje != null) return;

        // Crear la entidad de mensaje
        eMensaje = new Entidad();
        eMensaje.setNombre("Mensaje");
        eMensaje.setPropiedades(new ArrayList<Propiedad>(
                Arrays.asList(new Propiedad("texto", mensaje.getTexto()),
                        new Propiedad("tipo", mensaje.getTipo().name()),
                        new Propiedad("fecha", mensaje.getFecha().toString()),
                        new Propiedad("emoji", Integer.toString(mensaje.getEmoji())))));
        
        // Guardar la entidad en la base de datos
        eMensaje = servPersistencia.registrarEntidad(eMensaje);
        mensaje.setId(eMensaje.getId());
    }

    @Override
    public void eliminarMensaje(Mensaje mensaje) {
        // Lógica para eliminar un mensaje de la base de datos
    }

    @Override
    public void modificarMensaje(Mensaje mensaje) {
        // Lógica para modificar un mensaje en la base de datos
        Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());

        for (Propiedad p : eMensaje.getPropiedades()) {
            if (p.getNombre().equals("texto")) {
                p.setValor(mensaje.getTexto());
            } else if (p.getNombre().equals("tipo")) {
                p.setValor(mensaje.getTipo().toString());
            } else if (p.getNombre().equals("fecha")) {
                p.setValor(mensaje.getFecha().toString());
            } else if (p.getNombre().equals("emoji")) {
                p.setValor(Integer.toString(mensaje.getEmoji()));
            }
            servPersistencia.modificarPropiedad(p);
        }
    }

    @Override
    public Mensaje obtenerMensaje(int id) {
       // Si la entidad está en el pool la devolvemos
       if(PoolDAO.INSTANCIA.contiene(id)) {
           return (Mensaje) PoolDAO.INSTANCIA.getObjeto(id);
       }

       Entidad eMensaje;
       LocalDateTime fecha;
       String texto;
       TipoMensaje tipo;
       int emoji;

       eMensaje = servPersistencia.recuperarEntidad(id);
       
       texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
       System.out.println("Tipo: " + servPersistencia.recuperarPropiedadEntidad(eMensaje, "tipo"));
       tipo = TipoMensaje.valueOf(servPersistencia.recuperarPropiedadEntidad(eMensaje, "tipo"));
       fecha = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, "fecha"));
       emoji = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoji"));

         Mensaje mensaje = new Mensaje(texto, fecha, tipo, emoji);
         mensaje.setId(id);

         PoolDAO.INSTANCIA.addObjeto(id, mensaje);

         return mensaje;


    }

    @Override
    public List<Mensaje> obtenerMensajes(int idContacto) {
        List<Entidad> eMensajes = servPersistencia.recuperarEntidades("Mensaje");
        List<Mensaje> mensajes = new LinkedList<Mensaje>();

        for(Entidad eMensaje : eMensajes){
            mensajes.add(obtenerMensaje(eMensaje.getId()));
        }
        return mensajes;
    }

}
