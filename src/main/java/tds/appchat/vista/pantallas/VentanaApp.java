package tds.appchat.vista.pantallas;

import javax.swing.*;
import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.Ventana;
import tds.BubbleText;

import java.awt.*;
import java.awt.event.*;

public class VentanaApp extends JFrame implements Ventana, Recargable {

    // Componentes de la interfaz

    private JPanel panelPrincipal;
    private JPanel topPanel;
    private JPanel contactsPanel;
    private JTextField contactField;

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
        contactField = new JTextField(10);
        JButton btnEnviarContacto = new JButton("Enviar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnGestion = new JButton("Contactos");
        JButton btnPremium = new JButton("Premium");
        JLabel lblUserName = new JLabel("Usuario Actual");
        JLabel lblUserImage = new JLabel("[Imagen]");
        lblUserImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        topPanel.add(contactField);
        topPanel.add(btnEnviarContacto);
        topPanel.add(btnBuscar);
        topPanel.add(btnGestion);
        topPanel.add(btnPremium);
        topPanel.add(lblUserName);
        topPanel.add(lblUserImage);
        // Añadir toques verdes: cambiar fondo del topPanel
        topPanel.setBackground(new Color(200, 255, 200));
        
        // Manejadores de eventos en botones (simulación)
        btnEnviarContacto.addActionListener(e -> {
            String contacto = contactField.getText().trim();
            if (!contacto.isEmpty()){
                JOptionPane.showMessageDialog(VentanaApp.this, "Iniciando conversación con: " + contacto);
            }
        });
        
        btnBuscar.addActionListener(e -> JOptionPane.showMessageDialog(VentanaApp.this, "Abrir ventana de búsqueda"));
        btnGestion.addActionListener(e -> JOptionPane.showMessageDialog(VentanaApp.this, "Abrir gestión de contactos"));
        btnPremium.addActionListener(e -> JOptionPane.showMessageDialog(VentanaApp.this, "Convertirse a usuario Premium"));
        
        lblUserImage.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(VentanaApp.this, "Cambiar imagen de perfil");
            }
        });
        
        // Agregar topPanel a panelPrincipal
        panelPrincipal.add(topPanel, BorderLayout.NORTH);
        
        // Panel de contactos con cajitas y scroll vertical
        contactsPanel = new JPanel();
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));
        // Ajuste: aplicar margen interno al contactsPanel para mover su contenido más a la derecha
        contactsPanel.setBorder(BorderFactory.createEmptyBorder(30, 200, 10, 50));
        // Creación de cajitas de contacto (se aumentó la cantidad para provocar overflow)
        for (int i = 1; i <= 20; i++) {
            JPanel contactBox = new JPanel();
            contactBox.setLayout(new BoxLayout(contactBox, BoxLayout.Y_AXIS));
            // Borde titulado en verde
            contactBox.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 128, 0)),
                "Contacto " + i,
                0, 0, null, new Color(0, 128, 0)
            ));
            contactBox.add(new JLabel("Nombre: Contacto " + i));
            contactBox.add(new JLabel("Mensaje: Mensaje de prueba " + i));
            contactBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            contactsPanel.add(contactBox);
        }
        JScrollPane scrollPane = new JScrollPane(contactsPanel);
        scrollPane.setPreferredSize(new Dimension(400, 500)); // se aumenta el tamaño
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // borde para hacer visible el scrollPane
        
        // Actualización: Creación del Panel Mensajes sin usar BubbleText
        JPanel panelMensajes = new JPanel();
        panelMensajes.setLayout(new BoxLayout(panelMensajes, BoxLayout.Y_AXIS));
        // Borde titulado con toques verdes
        panelMensajes.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 128, 0)),
            "Mensajes",
            0, 0, null, new Color(0, 128, 0)
        ));
        // Agregar mensajes simples con JLabel y fuente en tono verde oscuro
        JLabel msg1 = new JLabel("Hola, ¿cómo estás?");
        msg1.setForeground(new Color(0, 100, 0));
        JLabel msg2 = new JLabel("Bien, gracias. ¿Y tú?");
        msg2.setForeground(new Color(0, 100, 0));
        JLabel msg3 = new JLabel("Todo bien, enviando prueba.");
        msg3.setForeground(new Color(0, 100, 0));
        panelMensajes.add(msg1);
        panelMensajes.add(msg2);
        panelMensajes.add(msg3);
        
        // Envolver panelMensajes en un JScrollPane para garantizar su visualización
        JScrollPane scrollMensajes = new JScrollPane(panelMensajes);
        scrollMensajes.setPreferredSize(new Dimension(200, 500));
        
        // Reemplazar adición individual por un JSplitPane para que ambos paneles sean iguales
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, scrollMensajes);
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaApp ventana = new VentanaApp();
            ventana.setVisible(true);
        });
    }
}