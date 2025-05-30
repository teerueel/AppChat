package tds.appchat.vista.core;

import tds.appchat.vista.componentes.Cabecera;
import tds.appchat.vista.pantallas.*;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.vista.util.ImagenUtil;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestor de ventanas implementado como Singleton Enumerado.
 * Se encarga de manejar la navegación entre las diferentes ventanas de la aplicación.
 */
public enum GestorVentanas {
    INSTANCIA;
    
    private JFrame frameContenedor;
    private Map<TipoVentana, Ventana> ventanas;
    private Ventana ventanaActual;
    private JPanel panelContenedor;
    private Cabecera cabecera; // Añadir un campo para usar siempre la misma cabecera
    
    /**
     * Constructor del enum que inicializa las ventanas
     */
    GestorVentanas() {
        ventanas = new HashMap<>();
        inicializarVentanas();
    }
    
    /**
     * Inicializa la aplicación creando y configurando el frame principal.
     */
    public void inicializarAplicacion() {
        // Crear el frame principal básico
        frameContenedor = new JFrame(EstilosApp.NOMBRE_APP);
        frameContenedor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameContenedor.setSize(1920, 1080);
        frameContenedor.setLocationRelativeTo(null);
        frameContenedor.setIconImage(ImagenUtil.cargarImagen("/images/appchat_icon.png"));
        
        // Crear panel contenedor principal
        panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(EstilosApp.COLOR_FONDO);
        panelContenedor.setBorder(new EmptyBorder(0, 0, 0, 0));
        frameContenedor.setContentPane(panelContenedor);
        
        // Crear la cabecera una sola vez
        cabecera = new Cabecera();
        panelContenedor.add(cabecera, BorderLayout.NORTH);

        // Mostrar la ventana principal por defecto
        mostrarVentana(TipoVentana.PRINCIPAL);
        
        // Mostrar la ventana principal
        frameContenedor.setVisible(true);
    }
    
    /**
     * Inicializa el frame contenedor principal.
     * @param frame JFrame principal de la aplicación
     * @param panelContenedor Panel donde se mostrarán las diferentes ventanas
     */
    public void inicializar(JFrame frame, JPanel panelContenedor) {
        this.frameContenedor = frame;
        this.panelContenedor = panelContenedor;
    }
    
    /**
     * Inicializa las ventanas disponibles en la aplicación.
     */
    private void inicializarVentanas() {
        // Inicializar todas las ventanas disponibles
        ventanas.put(TipoVentana.PRINCIPAL, new VentanaPrincipal());
        ventanas.put(TipoVentana.LOGIN, new VentanaLogin());
        ventanas.put(TipoVentana.REGISTRO, new VentanaRegistro());
        ventanas.put(TipoVentana.CONTACTOS, new VentanaContactos());
        ventanas.put(TipoVentana.PERFIL, new VentanaPerfil());
        ventanas.put(TipoVentana.APP, new VentanaApp());
        ventanas.put(TipoVentana.ESTADISTICAS, new VentanaEstadisticas());
        ventanas.put(TipoVentana.NUEVO_CONTACTO, new VentanaNuevoContacto());
        ventanas.put(TipoVentana.BUSCAR, new VentanaBuscar());
        ventanas.put(TipoVentana.NUEVO_GRUPO, new VentanaNuevoGrupo());
        ventanas.put(TipoVentana.PREMIUM, new VentanaPremium());
    }
    
    /**
     * Muestra una ventana específica.
     * @param tipo Tipo de ventana a mostrar
     */
    public void mostrarVentana(TipoVentana tipo) {
        panelContenedor.removeAll();
        panelContenedor.add(cabecera, BorderLayout.NORTH); // Reutilizar la misma cabecera
        
        // Obtener la ventana a mostrar
        Ventana nuevaVentana = ventanas.get(tipo);
        
        // Si la ventana implementa Recargable, la recargamos antes de mostrarla
        if (nuevaVentana instanceof Recargable) {
            ((Recargable) nuevaVentana).recargar();
        }
        
        panelContenedor.add(nuevaVentana.getPanelPrincipal(), BorderLayout.CENTER);
        panelContenedor.revalidate();
        panelContenedor.repaint();

        // Refrescar la cabecera para que muestre la sesión actualizada
        cabecera.recargar();
        
        if (ventanaActual != null) {
            ventanaActual.alOcultar();
        }
        
        if (nuevaVentana != null) {
            ventanaActual = nuevaVentana;
            ventanaActual.alMostrar();
            
            frameContenedor.setTitle(EstilosApp.NOMBRE_APP + " - " + tipo.toString());
        }
    }
    
    /**
     * Obtiene la ventana actual.
     * @return La ventana actual
     */
    public Ventana getVentanaActual() {
        return ventanaActual;
    }

    public VentanaContactos getVentanaContactos(){
        return (VentanaContactos) ventanas.get(TipoVentana.CONTACTOS);
    }

    public VentanaApp getVentanaApp(){
        return (VentanaApp) ventanas.get(TipoVentana.APP);
    }
}
