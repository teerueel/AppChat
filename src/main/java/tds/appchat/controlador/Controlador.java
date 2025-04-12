package tds.appchat.controlador;


import tds.appchat.modelo.*;


import tds.appchat.repositorio.*;
import tds.appchat.sesion.Sesion;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.core.TipoVentana;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.JsonNode;

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
    public boolean registrarUsuario(String email, String nombre, String password){
        return GestorUsuario.INSTANCIA.crearUsuario(email, nombre, password);
    }
    
    public boolean iniciarSesion(String email, String password) {
    	if(GestorUsuario.INSTANCIA.autenticarUsuario(email, password).isPresent()) {
    		Sesion.INSTANCIA.setUsuarioActual(GestorUsuario.INSTANCIA.autenticarUsuario(email, password).get());
    		return true;
    	}
    	return false;
    }
    
    public void cerrarSesion() {
    	Sesion.INSTANCIA.cerrarSesion();
    }
    
    
  

    public void actualizarTiempoUso(long tiempo){
        if(Sesion.INSTANCIA.haySesion()){
            System.out.println("Tiempo de uso: " + tiempo);
            Sesion.INSTANCIA.getUsuarioActual().aumentarTiempoTotal(tiempo);
            Sesion.INSTANCIA.setTiempoInicioSesion(System.currentTimeMillis());
        }
    }
  
}
