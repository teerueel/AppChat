package tds.appchat.vista.pantallas;

import javax.swing.*;
import java.awt.*;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.sesion.Sesion;
import tds.appchat.vista.componentes.PanelDerechoContactos;
import tds.appchat.vista.componentes.PanelIzquierdoContactos;
import tds.appchat.vista.componentes.TarjetaAddContacto;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.core.TipoVentana;

public class VentanaContactos extends JFrame implements Ventana, Recargable {

    private JPanel panelPrincipal;
    private  JPanel panelIzquierdo;
    private  JPanel panelDerecho;
    private JSplitPane splitPane;

    public VentanaContactos() {
        inicializarComponentes();
    }

    public void inicializarComponentes() {
        setTitle("Contactos - Ventana de Contactos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Panel principal de la ventana
        panelPrincipal = new JPanel(new BorderLayout());

        // Panel izquierdo: Lista de Contactos y Grupos con botón inferior "Añadir Contacto"
        panelIzquierdo = new PanelIzquierdoContactos();
      
        // Panel derecho: Área para mostrar contactos del grupo seleccionado y botón "Añadir Grupo"
        panelDerecho = new PanelDerechoContactos();
        
        // Utilizar un JSplitPane para dividir los dos paneles de manera equitativa
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(350);
        
        panelPrincipal.add(splitPane, BorderLayout.CENTER);
        
        // Agregar panelPrincipal al content pane
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
    }
    
    // Nuevo método sincronizador: se invoca cada vez que se cambia la selección en el panel izquierdo
    public  void updatePanelDerecho() {
        
        panelDerecho = new PanelDerechoContactos();
        splitPane.setRightComponent(panelDerecho);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    @Override
    public void recargar() {
        panelPrincipal.removeAll();
        inicializarComponentes();
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    @Override
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    @Override
    public void alMostrar() {
        // ...acciones al mostrar la ventana...
    }

    @Override
    public void alOcultar() {
        // ...acciones al ocultar la ventana...
    }

    @Override
    public TipoVentana getTipo() {
        return TipoVentana.CONTACTOS; // suponiendo que CONTACTOS es un tipo definido en TipoVentana
    }

    public  Contacto getSelectedContacto() {
        return ((PanelIzquierdoContactos) panelIzquierdo).getSelectedContacto();
    }
}
