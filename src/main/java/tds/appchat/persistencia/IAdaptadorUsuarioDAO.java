package tds.appchat.persistencia;

import java.util.List;
import tds.appchat.modelo.Usuario;

public interface IAdaptadorUsuarioDAO {
    public void registrarUsuario(Usuario user);
    public void eliminarUsuario(Usuario user);
    public void modificarUsuario(Usuario user);
    public Usuario obtenerUsuario(int id);
    public List<Usuario> obtenerUsuarios();


}
