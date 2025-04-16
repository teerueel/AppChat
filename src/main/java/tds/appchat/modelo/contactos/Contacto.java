package tds.appchat.modelo.contactos;

import tds.appchat.modelo.Mensaje;

// Utilizaremos el patrón Composite para la jerarquía de contactos y grupo.
public interface Contacto {
    public void agregarMensaje(Mensaje mensaje);
}
