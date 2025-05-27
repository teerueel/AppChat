package tds.appchat.persistencia;

import java.util.List;

import tds.appchat.modelo.Mensaje;

public interface IAdaptadorMensajeDAO {

    public void registrarMensaje(Mensaje mensaje);
    public void eliminarMensaje(Mensaje mensaje);
    public void modificarMensaje(Mensaje mensaje);
    public Mensaje obtenerMensaje(int id);
    public List<Mensaje> obtenerMensajes(int idContacto);

}