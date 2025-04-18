package tds.appchat.modelo.contactos;

import java.util.ArrayList;
import java.util.List;

import tds.appchat.modelo.Mensaje;

public class Grupo implements Contacto {
    private String nombreGrupo;
    private List<Contacto> contactos;
    private String imagen;

    public Grupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
        this.contactos = new ArrayList<Contacto>();

    }

    public Grupo(String nombreGrupo, String imagen, List<Contacto> contactos) {
        this.nombreGrupo = nombreGrupo;
        this.imagen = imagen;
        this.contactos = new ArrayList<Contacto>();
        contactos.stream().forEach(contacto -> {
            this.contactos.add(contacto);
            }
        );   
    }

    @Override
    public String getNombre() {
        return nombreGrupo;
    }

    @Override
    public String getImagen() {
        return imagen;
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

    

    public void  eliminarContactos(List<Contacto> contactos) {
        
        for (Contacto contacto : contactos) {
            
            this.contactos.remove(contacto);
        }
    }

    public void agregarContactos(List<Contacto> contactos) {
        for (Contacto contacto : contactos) {
            this.contactos.add(contacto);
        }
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
