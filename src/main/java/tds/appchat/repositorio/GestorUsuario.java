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
        usuarios.put(0, new Usuario(0,  "admin", "admin@gmail.com", "admin", "000000000", "Hola soy admin", null));
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


}
