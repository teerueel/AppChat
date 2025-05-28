package tds.appchat.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.appchat.modelo.Usuario;
import tds.appchat.modelo.contactos.Contacto;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
   
    private ServicioPersistencia servPersistencia;
    private static AdaptadorUsuarioTDS instance = null;

    public static AdaptadorUsuarioTDS getInstance() {
        if (instance == null) {
            instance = new AdaptadorUsuarioTDS();
        }
        return instance;
    }

    private AdaptadorUsuarioTDS() {
        servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
    }
    @Override
    public void registrarUsuario(Usuario user) {
        // Implementación para registrar un usuario en la base de datos
        Entidad eUsuario = null;

        //Si la entidad está registrada no la registra de nuevo
        try{
            eUsuario = servPersistencia.recuperarEntidad(user.getId());
        } catch (NullPointerException e) {}
            // La entidad no está registrada, se puede registrar
        if(eUsuario != null) return;

        // Registrar primero los atributos que son objetos
        IAdaptadorContactoDAO adaptadorContacto, adaptadorGrupo;
        adaptadorContacto = AdaptadorContactoIndividualTDS.getInstance();
        for(Contacto contacto : user.getContactosIndividuales()) {
            adaptadorContacto.registrarContacto(contacto);
        }
        adaptadorGrupo = AdaptadorGrupoTDS.getInstance();
        for(Contacto grupo : user.getGrupos()) {
            adaptadorGrupo.registrarContacto(grupo);
        }

        // crear la entidad de usuario
        eUsuario = new Entidad();
        eUsuario.setNombre("Usuario");
        eUsuario.setPropiedades(new ArrayList<Propiedad>(
                Arrays.asList(new Propiedad("telefono", user.getTelefono()),
                        new Propiedad("nombre", user.getNombre()),
                        new Propiedad("password", user.getPassword()),
                        new Propiedad("contactos", obtenerCodigo(user.getContactosIndividuales())),
                        new Propiedad("grupos", obtenerCodigo(user.getGrupos())),                        
                        new Propiedad("imagen", user.getImagen()),
                        new Propiedad("saludo", user.getSaludo()),
                        new Propiedad("email", user.getEmail()))));
        
        // Guardar la entidad en la base de datos
        eUsuario = servPersistencia.registrarEntidad(eUsuario);
        
        // Asignar el ID de la entidad al usuario
        user.setId(eUsuario.getId());
        
        
    }

    @Override
    public void eliminarUsuario(Usuario user) {
        // Implementación para eliminar un usuario de la base de datos
       
    }

    @Override
    public void modificarUsuario(Usuario user) {
        // Implementación para modificar un usuario en la base de datos
        
        Entidad eUsuario = servPersistencia.recuperarEntidad(user.getId());

        for (Propiedad prop : eUsuario.getPropiedades()) {
            if (prop.getNombre().equals("telefono")) {
                prop.setValor(user.getTelefono());
            } else if (prop.getNombre().equals("nombre")) {
                prop.setValor(user.getNombre());
            } else if (prop.getNombre().equals("password")) {
                prop.setValor(user.getPassword());
            } else if (prop.getNombre().equals("imagen")) {
                prop.setValor(user.getImagen());
            } else if (prop.getNombre().equals("saludo")) {
                prop.setValor(user.getSaludo());
            } else if (prop.getNombre().equals("email")) {
                prop.setValor(user.getEmail());
            } else if (prop.getNombre().equals("contactos")) {
                prop.setValor(obtenerCodigo(user.getContactosIndividuales()));
            } else if (prop.getNombre().equals("grupos")) {
                prop.setValor(obtenerCodigo(user.getGrupos()));
            }
            servPersistencia.modificarPropiedad(prop);
        }
    }

    @Override
    public Usuario obtenerUsuario(int id) {
        // Si la entidad está en el pool la devolvemos
        
        if(PoolDAO.INSTANCIA.contiene(id)) {
            return (Usuario) PoolDAO.INSTANCIA.getObjeto(id);
        }

        // Si no, la recupera de la BBDD
        Entidad eUsuario;
        List<Contacto> contactos = new LinkedList<Contacto>();
        List<Contacto> grupos = new LinkedList<Contacto>();
        String telefono;
        String nombre;
        String password;
        String imagen;
        String saludo;
        String email;

        // recuperar la entidad de usuario
        eUsuario = servPersistencia.recuperarEntidad(id);

        // recuperar las propiedades que no son objetos
        telefono = servPersistencia.recuperarPropiedadEntidad(eUsuario, "telefono");
        nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
        password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
        imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagen");
        saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, "saludo");
        email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");

        Usuario user = new Usuario(nombre, email, password, telefono, saludo, imagen);  
        user.setId(id);
        // Añadir el Usuario al pool antes de llamar a a otros adaptadores
        PoolDAO.INSTANCIA.addObjeto(id, user);

        // recuperar propiedades que son objetos
        
        contactos = obtenerDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"), false);
        for(Contacto contacto : contactos) {
            user.addContacto(contacto);
        } 
        grupos = obtenerDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "grupos"), true);
        for(Contacto grupo : grupos) {
        	user.addGrupo(grupo);
        }
        return user;
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        // Implementación para obtener todos los usuarios desde la base de datos
        List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("Usuario");
        List<Usuario> usuarios = new LinkedList<Usuario>();

        for (Entidad eUsuario : eUsuarios){
           
            usuarios.add(obtenerUsuario(eUsuario.getId()));
        }

        return usuarios;
    }

    //--------- Funciones auxiliares ----------//
    private String obtenerCodigo(List<Contacto> contactos) {
        String aux = "";
        for (Contacto contacto : contactos) {
            aux += contacto.getId() + " ";
        }
        return aux.trim();
    }

    private List<Contacto> obtenerDesdeCodigos(String codigos, boolean grupos) {
        List<Contacto> contactos = new LinkedList<Contacto>();
        // Comprueba si la cadena es null o vacía
        if(codigos == null || codigos.trim().isEmpty() || codigos.equals("0")){
            return contactos;
        }
        StringTokenizer st = new StringTokenizer(codigos, " ");
        IAdaptadorContactoDAO adaptadorContacto;
        if (grupos) {
        	adaptadorContacto = AdaptadorGrupoTDS.getInstance();
        } else {
        	adaptadorContacto = AdaptadorContactoIndividualTDS.getInstance();
        }
        while (st.hasMoreTokens()) {
            contactos.add(adaptadorContacto.obtenerContacto(Integer.valueOf((String) st.nextElement())));
        }
        return contactos;
    }




}
