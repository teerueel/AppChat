package tds.appchat.controlador;


import java.util.List;
import java.util.Map;

import tds.appchat.modelo.*;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.Grupo;
import tds.appchat.repositorio.*;
import tds.appchat.sesion.Sesion;

import tds.appchat.vista.util.SelectorImagen;



public enum Controlador {
    INSTANCIA;
    
    /**
     * Registra un nuevo usuario en el sistema
     * @param email Email del usuario
     * @param nombre Nombre del usuario
     * @param password Contraseña del usuario
     * @param esCreador Indica si el usuario tiene rol creador
     * @return true si el registro fue exitoso, false en caso contrario
     */

     // Se utiliza para registrar un nuevo usuario en el sistema
    public boolean registrarUsuario(String email, String nombre, String password, String tlf, String imagen, String saludo){
        return GestorUsuario.INSTANCIA.crearUsuario(email, nombre, password, tlf, imagen, saludo);
    }

    // Se utiliza para saber si un telefono ya está registrado en el sistema
    public boolean tlfRegistrado(String tlf) {
    	return GestorUsuario.INSTANCIA.tlfRegistrado(tlf);
    }
    // Se utiliza para saber si un email ya está registrado en el sistema
    public boolean emailRegistrado(String email) {
    	return GestorUsuario.INSTANCIA.emailRegistrado(email);
    }

    public String seleccionarImagenPerfil(){
        return SelectorImagen.INSTANCIA.seleccionarImagenPerfil();
    }
    
    public boolean iniciarSesion(String tlf, String password) {
    	if(GestorUsuario.INSTANCIA.autenticarUsuario(tlf, password).isPresent()) {
    		Sesion.INSTANCIA.setUsuarioActual(GestorUsuario.INSTANCIA.autenticarUsuario(tlf, password).get());
    		return true;
    	}
    	return false;
    }
    
    public void cerrarSesion() {
    	Sesion.INSTANCIA.cerrarSesion();
    }

    public boolean haySesion(){
        return Sesion.INSTANCIA.haySesion();
    }
    

    public void actualizarTiempoUso(long tiempo){
        if(Sesion.INSTANCIA.haySesion()){
            System.out.println("Tiempo de uso: " + tiempo);
            Sesion.INSTANCIA.getUsuarioActual().aumentarTiempoTotal(tiempo);
            Sesion.INSTANCIA.setTiempoInicioSesion(System.currentTimeMillis());
        }
    }

    public boolean nuevoContacto(String nombre, String telefono){
        if(Sesion.INSTANCIA.getUsuarioActual().contactoRegistrado(telefono).isPresent()){
            return false;
        }
        if(!GestorUsuario.INSTANCIA.tlfRegistrado(telefono)){
            return false;
        }
        Usuario user = GestorUsuario.INSTANCIA.getUsuario(telefono).get();
        return Sesion.INSTANCIA.getUsuarioActual().addContacto(user,nombre);
    }

    public boolean nuevoGrupo(String nombre, String imagen, List<Contacto> contactos){
        if(Sesion.INSTANCIA.getUsuarioActual().grupoRegistrado(nombre).isPresent()){
            return false;  
        }
        return Sesion.INSTANCIA.getUsuarioActual().addGrupo(nombre, imagen, contactos);
    }

    public void agregarContactosGrupo(List<Contacto> contactos, Grupo grupo){
        if(contactos.isEmpty()){
            return;
        }
         grupo.agregarContactos(contactos);
    }

    public void eliminarContactos(List<Contacto> contactos, Grupo grupo){
        grupo.eliminarContactos(contactos);
    }

    public void eliminarContacto(Contacto contacto){
        Sesion.INSTANCIA.getUsuarioActual().eliminarContacto(contacto);
    }

    public List<Contacto> getContactosRestantes(Grupo grupo){
        if(grupo == null){
            return Sesion.INSTANCIA.getUsuarioActual().getContactosIndividuales();
        }
        return Sesion.INSTANCIA.getUsuarioActual().getContactosIndividuales().stream()
        .filter(c -> !grupo.getContactos().contains(c) && !c.equals(grupo)).toList();
    }

    public Map<Contacto, Mensaje> getUltimosMensajes(){
        if(!Sesion.INSTANCIA.haySesion()){
            return null;
        }
        return Sesion.INSTANCIA.getUsuarioActual().getUltimosMensajes();
    }
  
}
