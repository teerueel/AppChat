package tds.appchat.repositorio;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import tds.appchat.modelo.Usuario;

public enum GestorUsuario implements RepositorioUsuario {
    INSTANCIA;
    
    private Map<Integer, Usuario> usuarios;

    /**
     * Constructor del enum que inicializa el mapa de usuarios
     */
    GestorUsuario() {
        usuarios = new HashMap<Integer, Usuario>();
        usuarios.put(0, new Usuario(0,  "admin", "admin@gmail.com", "admin", 
        "00", "Hola soy admin", "/images/avatar_default.png"));
        usuarios.put(1, new Usuario(1,  "admin2", "admin2@gmail.com", "admin",
         "01", "Hola soy admin", "/images/avatar_default.png"));
         usuarios.put(2, new Usuario(2, "messi", "messi@gmail.com", "messi",
         "messitlf", "Hola soy messi", "/images/messi.jpg"));
         usuarios.put(3, new Usuario(3, "cristiano", "cristiano@gmail.com", "cristiano",
         "cristianotlf", "SIUUUUU", "/images/cristiano.png"));
         usuarios.put(4, new Usuario(4, "mbappe", "mbappe@gmail.com", "mbappe",
         "mbappetlf", "Hola soy mbappe", "/images/mbappe.jpg"));
    }

    
    public boolean tlfRegistrado(String tlf) {
    	return usuarios.values().stream().anyMatch(u -> u.getTelefono().equalsIgnoreCase(tlf));
    }

    public boolean emailRegistrado(String email) {
    	return usuarios.values().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    
    
    @Override
    public boolean crearUsuario(String email, String nombre, String password,String tlf, String imagen , String saludo) {
        Usuario user = new Usuario(usuarios.size(), nombre, email, password, tlf, saludo, imagen);
        if(tlfRegistrado(tlf) || emailRegistrado(email)) return false;
         usuarios.put(user.getId(), user);
         return true;
    }

    @Override
    public Usuario obtenerUsuario(int id) {
        return usuarios.get(id);
    }

    @Override
    public boolean eliminarUsuario(int id) {
        return usuarios.remove(id) != null;
    }
    
    @Override
    public Optional<Usuario> autenticarUsuario(String tlf, String password) {
    	
    	return usuarios.values().stream().filter(u -> u.getTelefono().equalsIgnoreCase(tlf) 
    			&& u.getPassword().equals(password)).findAny();
    }

    public Optional<Usuario> getUsuario(String tlf){
        return usuarios.values().stream().filter(u -> u.getTelefono().equalsIgnoreCase(tlf)).findAny();
    }


}
