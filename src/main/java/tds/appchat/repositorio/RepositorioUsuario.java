package tds.appchat.repositorio;

import java.util.Optional;

import tds.appchat.modelo.Usuario;

public interface RepositorioUsuario {

    
     boolean crearUsuario(String email, String nombre, String password);

     Usuario obtenerUsuario(int id);

     boolean eliminarUsuario(int id);
     
     Optional<Usuario> autenticarUsuario(String email, String password);
}
