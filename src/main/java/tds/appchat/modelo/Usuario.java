package tds.appchat.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {

   
    private Integer id;
    
   

    
    
    private String nombre;
    
 
    private String email;
    
    
    private String password;
    
 
    private EstadisticasUsuario stats;
    

    
    public Usuario() {
        this.stats = new EstadisticasUsuario();
        
    }
    
    public Usuario( String nombre, String email, String password) {
        this();
       
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public Usuario(int id,  String nombre, String email, String password, EstadisticasUsuario stats) {
        this.id = id;
       
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.stats = stats;
        
    }

    public Usuario(int id,  String nombre, String email, String password) {
        this(id, nombre, email, password, new EstadisticasUsuario());
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


    
    public void aumentarTiempoTotal(long tiempo) {
        this.stats.aumentarTiempoUso(tiempo);
    }

    public void actualizarRacha(boolean acierto) {
        this.stats.actualizarRacha(acierto);
    }
}
