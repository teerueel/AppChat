package tds.appchat.modelo.contactos;

import java.util.List;

import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.util.TipoMensaje;

// Utilizaremos el patrón Composite para la jerarquía de contactos y grupo.
public interface Contacto {
    public void agregarMensaje(String texto, TipoMensaje tipo);
    
    public String getTelefono();
    public String getNombre(); // Método para obtener el nombre del contacto
    public String getImagen();
    public boolean isAgregado();
    public void setAgregado(boolean agregado);
    public void setNombre(String nombre);
    public List<Mensaje> getMensajes(); // Método para obtener la lista de mensajes
}
