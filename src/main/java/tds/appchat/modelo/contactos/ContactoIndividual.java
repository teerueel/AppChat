package tds.appchat.modelo.contactos;

import java.util.ArrayList;
import java.util.List;

import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.Usuario;

public class ContactoIndividual implements Contacto {
    
    private Usuario usuario;
    private String nombre;
    private List<Mensaje> mensajesRecibidos;


    public ContactoIndividual(String nombre) {
        this.nombre = nombre;
        this.usuario = null;
        this.mensajesRecibidos = new ArrayList<Mensaje>();
    }

    public ContactoIndividual(Usuario usuario, String nombre) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.mensajesRecibidos = new ArrayList<Mensaje>();
    }

   

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Mensaje> getMensajesRecibidos() {
        return mensajesRecibidos;
    }

    public void setMensajesRecibidos(List<Mensaje> mensajesRecibidos) {
        this.mensajesRecibidos = mensajesRecibidos;
    }

    @Override
    public void agregarMensaje(Mensaje mensaje) {
        this.mensajesRecibidos.add(mensaje);
    }

    @Override
    public String getTelefono() {
        return this.usuario.getTelefono();
    }
    

   

}
