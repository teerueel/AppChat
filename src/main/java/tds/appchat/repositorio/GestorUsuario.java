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
        usuarios.put(0, new Usuario(0,  "admin", "admin@gmail.com", "admin"));
    }

    
    public boolean emailRegistrado(String email) {
    	return usuarios.values().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
    
    @Override
    public boolean crearUsuario(String email, String nombre, String password){
        Usuario user = new Usuario(usuarios.size(), nombre, email, password);
        if(emailRegistrado(email)) return false;
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
    public Optional<Usuario> autenticarUsuario(String email, String password) {
    	System.out.println(email);
    	System.out.println(password);
    	return usuarios.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email) 
    			&& u.getPassword().equals(password)).findAny();
    }


}
