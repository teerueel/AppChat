package tds.appchat.vista.pantallas;

import tds.appchat.controlador.Controlador;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.vista.util.ImagenUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Ventana de inicio de sesión.
 */
public class VentanaLogin implements Ventana {
    private JPanel panelPrincipal;
    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    
    public VentanaLogin() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout(0, 0));
        panelPrincipal.setBackground(EstilosApp.COLOR_FONDO);
        
        // Panel central con scroll para contenido
        JPanel contenidoPanel = new JPanel();
        contenidoPanel.setLayout(new BoxLayout(contenidoPanel, BoxLayout.Y_AXIS));
        contenidoPanel.setBackground(EstilosApp.COLOR_FONDO);
        contenidoPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        
        // Panel de login
        JPanel panelLogin = crearPanelLogin();
        
        // Centrar el panel de login
        JPanel panelCentrador = new JPanel(new GridBagLayout());
        panelCentrador.setOpaque(false);
        panelCentrador.add(panelLogin);
        
        contenidoPanel.add(panelCentrador);
        
        // Scroll para el contenido
        JScrollPane scrollPane = new JScrollPane(contenidoPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(EstilosApp.COLOR_FONDO);
        
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelLogin() {
        // Panel principal del login
        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
        panelLogin.setBackground(EstilosApp.COLOR_TARJETA);
        panelLogin.setBorder(new EmptyBorder(40, 50, 40, 50));
        
        // Aplicar efecto de sombra y bordes redondeados
        panelLogin.putClientProperty("JComponent.roundRect", true);
        panelLogin.putClientProperty("JComponent.shadowType", "raised");
        
        // Título e imagen
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitulo.setOpaque(false);
        
        // Logo en el formulario
        JLabel logoLabel = new JLabel();
        Image imgLogo = ImagenUtil.cargarImagen("/images/appchat_logo.png");
        if (imgLogo != null) {
            logoLabel.setIcon(new ImageIcon(imgLogo.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        }
        
        JLabel labelTitulo = new JLabel("Iniciar Sesión");
        labelTitulo.setFont(EstilosApp.FUENTE_TITULO);
        labelTitulo.setForeground(EstilosApp.COLOR_PRIMARIO);
        
        panelTitulo.add(logoLabel);
        panelTitulo.add(Box.createRigidArea(new Dimension(15, 0)));
        panelTitulo.add(labelTitulo);
        
        // Descripción
        JTextArea descripcion = new JTextArea(
            "Accede a tu cuenta para conectarte y chatear con tus amigos."
        );
        descripcion.setEditable(false);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setOpaque(false);
        descripcion.setFont(EstilosApp.FUENTE_SUBTITULO);
        descripcion.setForeground(EstilosApp.COLOR_TEXTO_SECUNDARIO);
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        descripcion.setBorder(new EmptyBorder(15, 0, 30, 0));
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setOpaque(false);
        panelFormulario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFormulario.setMaximumSize(new Dimension(400, 200));
        
        // Campo de usuario
        JLabel labelUsuario = new JLabel("Correo electrónico");
        labelUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelUsuario.setForeground(EstilosApp.COLOR_TEXTO);
        labelUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        campoUsuario = new JTextField(20);
        campoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoUsuario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));
        campoUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Efecto focus en campo usuario
        campoUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campoUsuario.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(EstilosApp.COLOR_PRIMARIO, 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                campoUsuario.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        // Campo de contraseña
        JLabel labelPassword = new JLabel("Contraseña");
        labelPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelPassword.setForeground(EstilosApp.COLOR_TEXTO);
        labelPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        campoPassword = new JPasswordField(20);
        campoPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoPassword.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));
        campoPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Efecto focus en campo contraseña
        campoPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campoPassword.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(EstilosApp.COLOR_PRIMARIO, 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                campoPassword.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        // Opciones adicionales
        JPanel panelOpciones = new JPanel(new BorderLayout());
        panelOpciones.setOpaque(false);
        panelOpciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelOpciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JCheckBox recordarCheck = new JCheckBox("Recordarme");
        recordarCheck.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        recordarCheck.setForeground(EstilosApp.COLOR_TEXTO_SECUNDARIO);
        recordarCheck.setOpaque(false);
        
        JLabel olvidoPassword = new JLabel("¿Olvidaste tu contraseña?");
        olvidoPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        olvidoPassword.setForeground(EstilosApp.COLOR_PRIMARIO);
        olvidoPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panelOpciones.add(recordarCheck, BorderLayout.WEST);
        panelOpciones.add(olvidoPassword, BorderLayout.EAST);
        
        // Botón de login
        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(EstilosApp.FUENTE_BOTON);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnLogin.setBorder(new EmptyBorder(15, 30, 15, 30));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setFocusPainted(false);
        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        btnLogin.addActionListener(e -> {
           /* // Simulación de autenticación
            if (campoUsuario.getText().equals("admin") && 
                    String.valueOf(campoPassword.getPassword()).equals("admin")) {
                GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.CURSOS);
            } else {
                JOptionPane.showMessageDialog(panelPrincipal, 
                        "Credenciales inválidas. Prueba con admin/admin", 
                        "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            }*/

        	if(Controlador.INSTANCIA.iniciarSesion(campoUsuario.getText(), String.valueOf(campoPassword.getPassword()))){
        		JOptionPane.showMessageDialog(panelPrincipal, 
                        "Iniciaste sesión.", 
                        "Has iniciado sesión", JOptionPane.INFORMATION_MESSAGE);
        	}
            else {
                JOptionPane.showMessageDialog(panelPrincipal, 
                        "Credenciales inválidas.", 
                        "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Opciones de registro
        JPanel panelRegistro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelRegistro.setOpaque(false);
        panelRegistro.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel textoRegistro = new JLabel("¿No tienes una cuenta?");
        textoRegistro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textoRegistro.setForeground(EstilosApp.COLOR_TEXTO_SECUNDARIO);
        
        JLabel linkRegistro = new JLabel("Regístrate");
        linkRegistro.setFont(new Font("Segoe UI", Font.BOLD, 14));
        linkRegistro.setForeground(EstilosApp.COLOR_PRIMARIO);
        linkRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.REGISTRO);
            }
        });
        
        panelRegistro.add(textoRegistro);
        panelRegistro.add(Box.createRigidArea(new Dimension(5, 0)));
        panelRegistro.add(linkRegistro);
        
        // Agregar campos al panel de formulario
        panelFormulario.add(labelUsuario);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(campoUsuario);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));
        panelFormulario.add(labelPassword);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(campoPassword);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 15)));
        panelFormulario.add(panelOpciones);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 25)));
        panelFormulario.add(btnLogin);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 25)));
        panelFormulario.add(panelRegistro);
        
        // Armar el panel completo
        panelLogin.add(panelTitulo);
        panelLogin.add(descripcion);
        panelLogin.add(panelFormulario);
        
        return panelLogin;
    }
    
    @Override
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    
    @Override
    public void alMostrar() {
        campoUsuario.setText("");
        campoPassword.setText("");
        campoUsuario.requestFocus();
    }
    
    @Override
    public void alOcultar() {
        // No se requiere acción especial al ocultar
    }
    
    @Override
    public TipoVentana getTipo() {
        return TipoVentana.LOGIN;
    }
}
