package tds.appchat.modelo.contactos;

import java.util.ArrayList;
import java.util.List;

import tds.appchat.modelo.Mensaje;

public class Grupo implements Contacto {
    private String nombreGrupo;
    private List<Contacto> contactos;

    public Grupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
        this.contactos = new ArrayList<Contacto>();
    }

    public Grupo(String nombreGrupo, List<Contacto> contactos) {
        this.nombreGrupo = nombreGrupo;
        this.contactos = contactos;
    }

    @Override
    public String getNombre() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    public void agregarContacto(Contacto contacto) {
        this.contactos.add(contacto);
    }

    @Override
    public void agregarMensaje(Mensaje mensaje) {
        for (Contacto contacto : contactos) {
            contacto.agregarMensaje(mensaje);
        }
    }

    @Override
    public String getTelefono() {
        return null; // Los grupos no tienen un teléfono único
    }
}
