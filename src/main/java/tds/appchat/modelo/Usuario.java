package tds.appchat.modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.ContactoIndividual;
import tds.appchat.modelo.contactos.Grupo;
import tds.appchat.modelo.util.TipoMensaje;
import tds.appchat.sesion.Sesion;

public class Usuario {

   
    private Integer id;  
    private String nombre;
    private String telefono;
    private String saludo;
    private String email;
    private String password;
    private EstadisticasUsuario stats;
    private boolean Premium;
    private String imagen;
    private List<Contacto> contactos;

    

    
    public Usuario() {
        this.stats = new EstadisticasUsuario();
        this.contactos = new ArrayList<Contacto>();
        this.Premium    = false;
    }


    public Usuario(int id,  String nombre, String email, String password, EstadisticasUsuario stats) {
        this();
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.stats = stats;
        
    }

    public Usuario(int id,  String nombre, String email, String password) {
        this();
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public Usuario(int id, String nombre, String email, String password, String telefono, String saludo) {
        this(id, nombre, email, password);
        this.telefono = telefono;
        this.saludo = saludo;
    }

    public Usuario(int id, String nombre, String email, String password, String telefono, String saludo, String imagen) {
        this(id, nombre, email, password, telefono, saludo);
        this.imagen = imagen;
    }

   

    public int getId() {
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }


    

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EstadisticasUsuario getStats() {
        return this.stats;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSaludo() {
        return this.saludo;
    }  

    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }

    public boolean isPremium() {
        return this.Premium;
    }

    public void setPremium(boolean premium) {
        this.Premium = premium;
    }

    public String getImagen() {
        return this.imagen;
    }  

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Contacto> getContactos() {
        return this.contactos;
    }

    public List<Contacto> getContactosIndividuales() {
        return this.contactos.stream().filter(c -> c instanceof ContactoIndividual).toList();
    }

    public List<Contacto> getGrupos() {
        return this.contactos.stream().filter(c -> c instanceof Grupo).toList();
    }

    // Se utiliza para añadir un contacto a la lista de contactos del usuario
    public boolean addContacto(Usuario user, String nombre) {
        Contacto contacto = new ContactoIndividual(user, nombre);
        return this.contactos.add(contacto);
    }


    public Optional<Contacto> contactoRegistrado(String telefono) {
        return this.getContactosIndividuales().stream().filter(u -> u.getTelefono().equals(telefono)).findAny();   
    }

    public Optional<Contacto> grupoRegistrado(String nombreGrupo) {
        return this.getGrupos().stream().filter(u -> u.getNombre().equals(nombreGrupo)).findAny();   
    }


    public List<String> getContactosUsuario() {
        // TODO Auto-generated method stub
        return this.contactos.stream().map(c -> c.getNombre()).toList();
    }

    public boolean addGrupo(String nombre, String imagen, List<Contacto> contactos) {
        // TODO Auto-generated method stub
        Grupo grupo = new Grupo(nombre, imagen, contactos);
        return this.contactos.add(grupo);
    }

    //Elimina un contacto de la lista de contactos del usuario, también de todos los grupos
    // a los que pertenezca
    public void eliminarContacto(Contacto contacto) {
        this.contactos.remove(contacto);
        getGrupos().stream().filter(g-> ((Grupo) g).getContactos().contains(contacto)).forEach(g -> ((Grupo) g).eliminarContacto(contacto));
    }

    public Map<Contacto, Mensaje> getUltimosMensajes() {
        return this.getContactosIndividuales().stream()
                .filter(c -> ((ContactoIndividual) c).getUltimoMensaje().isPresent())
                .sorted(Comparator.comparing(c -> ((ContactoIndividual) c).getUltimoMensaje().get().getFecha()).reversed())
                .collect(Collectors.toMap(
                        c -> c,
                        c -> ((ContactoIndividual) c).getUltimoMensaje().get(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new));
    }

    public void recibirMensaje(String texto, String telefono) {
        Optional<Contacto> contacto = this.contactoRegistrado(telefono);
        if (contacto.isPresent()) {
            contacto.get().agregarMensaje(texto, TipoMensaje.RECIBIDO);
        } else {
            Contacto nuevoContacto = new ContactoIndividual(Sesion.INSTANCIA.getUsuarioActual(), telefono, false);
            nuevoContacto.agregarMensaje(texto, TipoMensaje.RECIBIDO);
            this.contactos.add(nuevoContacto);
        }   
    }

    public void recibirEmoji(int emoji, String telefono) {
        Optional<Contacto> contacto = this.contactoRegistrado(telefono);
        if (contacto.isPresent()) {
            contacto.get().agregarEmoji(emoji, TipoMensaje.RECIBIDO);
        } else {
            Contacto nuevoContacto = new ContactoIndividual(Sesion.INSTANCIA.getUsuarioActual(), telefono, false);
            nuevoContacto.agregarEmoji(emoji, TipoMensaje.RECIBIDO);
            this.contactos.add(nuevoContacto);
        }   
    }
    
    

    public void aumentarTiempoTotal(long tiempo) {
        this.stats.aumentarTiempoUso(tiempo);
    }

    public void actualizarRacha(boolean acierto) {
        this.stats.actualizarRacha(acierto);
    }


}
