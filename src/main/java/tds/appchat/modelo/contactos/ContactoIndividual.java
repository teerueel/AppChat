package tds.appchat.modelo.contactos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.Usuario;
import tds.appchat.modelo.util.TipoMensaje;

public class ContactoIndividual implements Contacto {
    
    private Usuario usuario;
    private String nombre;
    private List<Mensaje> mensajes;
    private boolean agregado;


    public ContactoIndividual(String nombre) {
        this.nombre = nombre;
        this.usuario = null;
        this.mensajes = new LinkedList<Mensaje>();
        
        this.agregado = true;
    }

    public ContactoIndividual(Usuario usuario, String nombre) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.mensajes = new ArrayList<Mensaje>();
        this.agregado = true;
    }

    public ContactoIndividual(Usuario usuario, String nombre, boolean agregado) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.mensajes = new ArrayList<Mensaje>();
        
        this.agregado = agregado;
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

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajesRecibidos(List<Mensaje> mensajesRecibidos) {
        this.mensajes = mensajesRecibidos;
    }

    @Override
    public void agregarMensaje(String texto, TipoMensaje tipo) {
        Mensaje mensaje = new Mensaje(texto, tipo);
        this.mensajes.add(mensaje);
    }

    @Override
    public void agregarEmoji(int emoji, TipoMensaje tipo) {
        Mensaje mensaje = new Mensaje(emoji,tipo);
        this.mensajes.add(mensaje);
    }

    @Override
    public String getTelefono() {
        return this.usuario.getTelefono();
    }
    @Override
    public String getImagen(){
        return this.usuario.getImagen();
    }

    public String getSaludo(){
        return this.usuario.getSaludo();
    }

    public boolean isAgregado() {
        return agregado;
    }
    
    public void setAgregado(boolean agregado) {
        this.agregado = agregado;
    }

    //devuelve el último mensaje 
    public Optional<Mensaje> getUltimoMensaje(){
        return this.mensajes.stream().
                max((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()));
    }



    
    

   

}
