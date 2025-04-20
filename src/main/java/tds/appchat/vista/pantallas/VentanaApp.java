package tds.appchat.vista.pantallas;

import javax.swing.*;

import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.vista.componentes.PanelDerechoContactos;
import tds.appchat.vista.componentes.PanelDerechoMensajes;
import tds.appchat.vista.componentes.PanelIzquierdoContactos;
import tds.appchat.vista.componentes.PanelIzquierdoMensajes;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.util.EstilosApp;


import java.awt.*;
import java.awt.event.*;

public class VentanaApp extends JFrame implements Ventana, Recargable {

    // Componentes de la interfaz

    private JPanel panelPrincipal;
    private JPanel topPanel;
    private JPanel panelContactos;
    private JPanel panelMensajes;
    private JSplitPane splitPane;
    

    // Constructor
    public VentanaApp() {
        inicializarComponentes();
    }
    
    public void inicializarComponentes() {
        setTitle("AppChat - Ventana Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Inicializar panelPrincipal
        panelPrincipal = new JPanel(new BorderLayout());
        
        // Panel superior: solo contiene los botones y campos de acción
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
       
        JButton btnBuscar = new JButton("Buscar");
        JButton btnGestion = new JButton("Contactos");
        JButton btnPremium = new JButton("Premium");
        // Aplicar estética de la APP a cada botón
        
        btnBuscar.setFont(EstilosApp.FUENTE_BOTON);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setBackground(EstilosApp.COLOR_PRIMARIO);

        
        btnBuscar.addActionListener(e -> {
           
            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.BUSCAR);
        });
        
        btnGestion.setFont(EstilosApp.FUENTE_BOTON);
        btnGestion.setForeground(Color.WHITE);
        btnGestion.setBackground(EstilosApp.COLOR_PRIMARIO);

        btnGestion.addActionListener(e -> {
            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.CONTACTOS);
            
        });

        
        btnPremium.setFont(EstilosApp.FUENTE_BOTON);
        btnPremium.setForeground(Color.WHITE);
        btnPremium.setBackground(EstilosApp.COLOR_PRIMARIO);

        btnPremium.addActionListener(e -> JOptionPane.
        showMessageDialog(VentanaApp.this, "Funcionalidad dispoible próximamente"));
        
        
        topPanel.add(btnBuscar);
        topPanel.add(btnGestion);
        topPanel.add(btnPremium);
        
        topPanel.setBackground(EstilosApp.COLOR_FONDO);
        
        
        
        // Agregar topPanel a panelPrincipal
        panelPrincipal.add(topPanel, BorderLayout.NORTH);
        
        // Panel de contactos con cajitas y scroll vertical
         this.panelContactos = new PanelIzquierdoMensajes();
       
        
        // Actualización: Creación del Panel Mensajes sin usar BubbleText
         this.panelMensajes = new PanelDerechoMensajes();
       
        
        // Envolver panelMensajes en un JScrollPane para garantizar su visualización
        JScrollPane scrollMensajes = new JScrollPane(panelMensajes);
        scrollMensajes.setPreferredSize(new Dimension(200, 500));
        
        // Reemplazar adición individual por un JSplitPane para que ambos paneles sean iguales
         this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.panelContactos, this.panelMensajes);
        splitPane.setResizeWeight(0.5);
        // Agregar splitPane a panelPrincipal en la zona central
        panelPrincipal.add(splitPane, BorderLayout.CENTER);
        
        // Finalmente, agregar panelPrincipal al content pane
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
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
        // ...
    }

    @Override
    public void alOcultar() {
        // ...
    }

    @Override
    public TipoVentana getTipo() {
        return TipoVentana.APP;
    }
    
    public  void updatePanelDerecho() {
        this.panelMensajes = new PanelDerechoMensajes();
        splitPane.setRightComponent(this.panelMensajes);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    public void updatePanelIzquierdo(){
        this.panelContactos = new PanelIzquierdoMensajes();
        splitPane.setLeftComponent(this.panelContactos);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
        this.updatePanelDerecho();
    }

    public Contacto getSelectedContacto(){
        return ((PanelIzquierdoMensajes) this.panelContactos).getSelectedContacto();
    }

    
}