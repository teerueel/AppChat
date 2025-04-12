package tds.appchat.vista.core;

import javax.swing.JPanel;

/**
 * Interfaz que deben implementar todas las ventanas de la aplicación.
 */
public interface Ventana {
    /**
     * Obtiene el panel principal de la ventana.
     * @return JPanel que contiene la ventana
     */
    JPanel getPanelPrincipal();
    
    /**
     * Método que se ejecuta cuando la ventana se muestra.
     */
    void alMostrar();
    
    /**
     * Método que se ejecuta cuando la ventana se oculta.
     */
    void alOcultar();
    
    /**
     * Obtiene el tipo de ventana.
     * @return Tipo de ventana
     */
    TipoVentana getTipo();
}
