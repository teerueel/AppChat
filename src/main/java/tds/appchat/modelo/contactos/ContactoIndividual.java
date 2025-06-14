package tds.appchat.modelo.contactos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.Usuario;
import tds.appchat.modelo.util.TipoMensaje;
import tds.appchat.repositorio.CatalogoUsuarios;

public class ContactoIndividual implements Contacto {
    
	private int id;
    private int idUsuario;
    private String nombre;
    private List<Mensaje> mensajes;
    private boolean agregado;


    public ContactoIndividual(String nombre) {
        this.nombre = nombre;
        this.mensajes = new LinkedList<Mensaje>();
        
        this.agregado = true;
    }

    public ContactoIndividual(int idUsuario, String nombre) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.mensajes = new ArrayList<Mensaje>();
        this.agregado = true;
    }

    public ContactoIndividual(int idUsuario, String nombre, boolean agregado) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.mensajes = new ArrayList<Mensaje>();
        
        this.agregado = agregado;
    }

   public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
    public void agregarMensaje(Mensaje mensaje) {
        this.mensajes.add(mensaje);
    }

    @Override
    public void agregarEmoji(Mensaje mensaje) {
        this.mensajes.add(mensaje);
    }

    @Override
    public String getTelefono() {
        if(CatalogoUsuarios.INSTANCIA.getUsuario(idUsuario).isPresent()){
            return CatalogoUsuarios.INSTANCIA.getUsuario(idUsuario).get().getTelefono();
        }
        return null;
    }
    @Override
    public String getImagen(){
        if(CatalogoUsuarios.INSTANCIA.getUsuario(idUsuario).isPresent()){
            return CatalogoUsuarios.INSTANCIA.getUsuario(idUsuario).get().getImagen();
        }
        return null;
    }

    public String getSaludo(){
        if(CatalogoUsuarios.INSTANCIA.getUsuario(idUsuario).isPresent()){
            return CatalogoUsuarios.INSTANCIA.getUsuario(idUsuario).get().getSaludo();
        }
        return null;
    }
    
    public boolean isAgregado() {
        return agregado;
    }
    
    public void setAgregado(boolean agregado) {
        this.agregado = agregado;
    }

    @Override
    public List<Contacto> getContactos() {
        // Un contacto individual no tiene contactos, por lo que devolvemos una lista vacía
        ArrayList<Contacto> lista = new ArrayList<Contacto>();
        lista.add(this);
        return lista;
    }

    //devuelve el último mensaje 
    public Optional<Mensaje> getUltimoMensaje(){
        return this.mensajes.stream().
                max((m1, m2) -> m1.getFecha().compareTo(m2.getFecha()));
    }



    
    

   

}
