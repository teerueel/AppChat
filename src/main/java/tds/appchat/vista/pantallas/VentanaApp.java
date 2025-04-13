package tds.appchat.vista.pantallas;

import javax.swing.*;

import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.Ventana;

import java.awt.*;
import java.awt.event.*;

public class VentanaApp extends JFrame implements Ventana, Recargable {

    // Componentes de la interfaz
    private JTextArea txtConversation;
    private JList<String> lstChats;
    private JTextField contactField;
    private JTextField txtMessageInput;
    private JPanel topPanel;

    // Constructor
    public VentanaApp() {
        inicializarComponentes();
    }
    
    public void inicializarComponentes() {
        setTitle("AppChat - Ventana Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Panel superior: muestra desde la selección de contacto hasta usuario actual
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Cambio: alineación centrada
        contactField = new JTextField(10);
        // Botón con icono para iniciar conversación (se usa texto "Enviar" por simplicidad)
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
        
        // Panel izquierda: Lista de últimos mensajes enviados/recibidos por contacto o teléfono
        JPanel leftPanel = new JPanel(new BorderLayout());
        lstChats = new JList<>(new String[]{
            "Mensaje de: Contacto 1", 
            "Mensaje de: 123456789", 
            "Mensaje de: Contacto 3"
        });
        leftPanel.add(new JScrollPane(lstChats), BorderLayout.CENTER);
        
        // Panel derecha: reemplazar la creación actual por una pestaña con dos paneles
        // Creación del panel de conversación
        JPanel conversationPanel = new JPanel(new BorderLayout());
        txtConversation = new JTextArea();
        txtConversation.setEditable(false);
        conversationPanel.add(new JScrollPane(txtConversation), BorderLayout.CENTER);
        JPanel inputPanel = new JPanel(new BorderLayout());
        txtMessageInput = new JTextField();
        JButton btnSendMessage = new JButton("Enviar Mensaje");
        inputPanel.add(txtMessageInput, BorderLayout.CENTER);
        inputPanel.add(btnSendMessage, BorderLayout.EAST);
        conversationPanel.add(inputPanel, BorderLayout.SOUTH);

        // Creación del panel de mensajes compartidos
        JPanel sharedPanel = new JPanel(new BorderLayout());
        JTextArea txtShared = new JTextArea();
        txtShared.setEditable(false);
        sharedPanel.add(new JScrollPane(txtShared), BorderLayout.CENTER);

        // Agregar ambos paneles a un JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Conversación", conversationPanel);
        tabbedPane.addTab("Mensajes Compartidos", sharedPanel);

        // Reemplazar el panel derecho por uno que incluya el tabbedPane
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // División entre panel izquierda y derecha
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(200);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        
        // Añadir manejadores de eventos
        
        // Al pulsar Enviar en el panel superior: inicia conversación con el contacto o teléfono introducido
        btnEnviarContacto.addActionListener(e -> {
            String contacto = contactField.getText().trim();
            if (!contacto.isEmpty()){
                txtConversation.setText("Iniciando conversación con: " + contacto + "\n");
            }
        });
        
        // Al seleccionar un contacto de la lista, cargar conversación simulada
        lstChats.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                String selected = lstChats.getSelectedValue();
                if (selected != null){
                    txtConversation.setText("Conversación con: " + selected + "\n");
                }
            }
        });
        
        // Enviar mensaje en la conversación
        btnSendMessage.addActionListener(e -> {
            String mensaje = txtMessageInput.getText().trim();
            if (!mensaje.isEmpty()){
                txtConversation.append("Yo: " + mensaje + "\n");
                txtMessageInput.setText("");
            }
        });
        
        // Al pulsar la imagen del usuario, simular cambio de imagen
        lblUserImage.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(VentanaApp.this, "Cambiar imagen de perfil");
            }
        });
        
        // Botones Buscar, Contactos y Premium pueden abrir ventanas (simulación)
        btnBuscar.addActionListener(e -> JOptionPane.showMessageDialog(VentanaApp.this, "Abrir ventana de búsqueda"));
        btnGestion.addActionListener(e -> JOptionPane.showMessageDialog(VentanaApp.this, "Abrir gestión de contactos"));
        btnPremium.addActionListener(e -> JOptionPane.showMessageDialog(VentanaApp.this, "Convertirse a usuario Premium"));
    }
    
    // Implementación de la interfaz Recargable
    @Override
    public void recargar() {
        // Simulación de recarga de la ventana, por ejemplo, refrescar lista de chats
        topPanel.removeAll();
        inicializarComponentes();
        topPanel.revalidate();
        topPanel.repaint();
    }
    
   
  
    

    @Override
    public JPanel getPanelPrincipal() {
        // TODO Auto-generated method stub
        return topPanel;
    }

    @Override
    public void alMostrar() {
        // TODO Auto-generated method stub
    }

    @Override
    public void alOcultar() {
        // TODO Auto-generated method stub
       
    }

    @Override
    public TipoVentana getTipo() {
        // TODO Auto-generated method stub
        return TipoVentana.APP;
    }
}
