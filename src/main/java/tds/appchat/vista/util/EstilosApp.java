package tds.appchat.vista.util;

import java.awt.Color;
import java.awt.Font;

/**
 * Clase que centraliza todos los estilos de la aplicación.
 * Contiene constantes para colores, fuentes y otros elementos visuales.
 */
public class EstilosApp {
    // Nombre de la aplicación
    public static final String NOMBRE_APP = "AppChat";
    
    // Colores de la aplicación
    public static final Color COLOR_PRIMARIO = new Color(0, 128, 0); // Verde principal
    public static final Color COLOR_SECUNDARIO = new Color(0, 153, 255); // Azul secundario
    public static final Color COLOR_FONDO = new Color(249, 249, 249);      // Fondo claro
    public static final Color COLOR_TARJETA = new Color(255, 255, 255);    // Blanco para tarjetas
    public static final Color COLOR_TEXTO = new Color(51, 51, 51);         // Negro suave para texto
    public static final Color COLOR_TEXTO_SECUNDARIO = new Color(119, 119, 119); // Gris para texto secundario
    public static final Color COLOR_BOTON = new Color(198, 40, 40);        // Color de botones
    public static final Color COLOR_BOTON_HOVER = new Color(229, 57, 53);  // Color cuando el cursor está sobre el botón
    public static final Color COLOR_BORDE = new Color(230, 230, 230);      // Color para bordes
    
    // Fuentes de la aplicación
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 32);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FUENTE_TARJETA_TITULO = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FUENTE_TARJETA_TEXTO = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font FUENTE_NAVBAR = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    
    /**
     * Aplica configuraciones de estilo UI al UIManager
     */
    public static void aplicarEstilosUI() {
        javax.swing.UIManager.put("Button.arc", 12);
        javax.swing.UIManager.put("Component.arc", 12);
        javax.swing.UIManager.put("Button.foreground", COLOR_PRIMARIO);
        javax.swing.UIManager.put("Panel.background", COLOR_FONDO);
        javax.swing.UIManager.put("TextField.arc", 8);
        javax.swing.UIManager.put("PasswordField.arc", 8);
        javax.swing.UIManager.put("ComboBox.arc", 8);
    }
}
