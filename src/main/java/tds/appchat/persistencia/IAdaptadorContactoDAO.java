package tds.appchat.persistencia;

import java.util.List;

import tds.appchat.modelo.contactos.Contacto;

public interface IAdaptadorContactoDAO {

    public void registrarContacto(Contacto contacto);
    public void eliminarContacto(Contacto contacto);
    public void modificarContacto(Contacto contacto);
    public Contacto obtenerContacto(int id);
    public List<Contacto> obtenerContactos();
    

}