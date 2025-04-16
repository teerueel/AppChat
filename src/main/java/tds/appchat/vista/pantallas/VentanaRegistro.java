package tds.appchat.vista.pantallas;

import tds.appchat.controlador.Controlador;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.vista.util.ImagenUtil;
import com.toedter.calendar.JDateChooser;  

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Ventana de registro de usuarios.
 */
public class VentanaRegistro implements Ventana {
    private JPanel panelPrincipal;
    private JTextField campoUsuario;
    private JTextField campoEmail;
    private JTextField campoTlf;
    private JTextField campoSaludo;
    private JPasswordField campoPassword;
    private JPasswordField campoConfirmPassword;
    private JDateChooser dateChooserNacimiento; // Campo para la fecha usando JDateChooser
    private JLabel lblAvatar;
    private String path = "/images/avatar_default.png"; //PATH de la imagen seleccionada
    
    public VentanaRegistro() {
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
        
        // Panel de registro
        JPanel panelRegistro = crearPanelRegistro();
        
        // Centrar el panel de registro
        JPanel panelCentrador = new JPanel(new GridBagLayout());
        panelCentrador.setOpaque(false);
        panelCentrador.add(panelRegistro);
        
        contenidoPanel.add(panelCentrador);
        
        // Scroll para el contenido
        JScrollPane scrollPane = new JScrollPane(contenidoPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(EstilosApp.COLOR_FONDO);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Nueva línea para mostrar el scrollbar siempre
        
        // Agregar componentes al panel principal
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelRegistro() {
        // Panel principal del registro
        JPanel panelRegistro = new JPanel();
        panelRegistro.setLayout(new BoxLayout(panelRegistro, BoxLayout.Y_AXIS));
        panelRegistro.setBackground(EstilosApp.COLOR_TARJETA);
        panelRegistro.setBorder(new EmptyBorder(40, 50, 40, 50));
        
        // Aplicar efecto de sombra y bordes redondeados
        panelRegistro.putClientProperty("JComponent.roundRect", true);
        panelRegistro.putClientProperty("JComponent.shadowType", "raised");
        
        // Título e imagen
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitulo.setOpaque(false);
        
        // Logo en el formulario
        JLabel logoLabel = new JLabel();
        Image imgLogo = ImagenUtil.cargarImagen("/images/appchat_logo.png");
        if (imgLogo != null) {
            logoLabel.setIcon(new ImageIcon(imgLogo.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        }
        
        JLabel labelTitulo = new JLabel("Crear Cuenta");
        labelTitulo.setFont(EstilosApp.FUENTE_TITULO);
        labelTitulo.setForeground(EstilosApp.COLOR_PRIMARIO);
        
        panelTitulo.add(logoLabel);
        panelTitulo.add(Box.createRigidArea(new Dimension(15, 0)));
        panelTitulo.add(labelTitulo);
        
        // Descripción
        JTextArea descripcion = new JTextArea(
            "Regístrate para unirte a AppChat y chatear en tiempo real con tus contactos."
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
        panelFormulario.setMaximumSize(new Dimension(400, 400));
        
        // Nuevo panel para la foto
        JPanel panelFoto = new JPanel();
        panelFoto.setLayout(new BoxLayout(panelFoto, BoxLayout.Y_AXIS));
        panelFoto.setOpaque(false);
        panelFoto.setAlignmentX(Component.CENTER_ALIGNMENT);

         // Crear avatar con borde circular
        lblAvatar = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (getIcon() != null) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    int diameter = Math.min(getWidth(), getHeight());
                    int x = (getWidth() - diameter) / 2;
                    int y = (getHeight() - diameter) / 2;
                    
                    // Dibujar círculo
                    g2.setColor(EstilosApp.COLOR_PRIMARIO);
                    g2.fillOval(x, y, diameter, diameter);
                    
                    // Clip para la imagen
                    g2.setClip(new java.awt.geom.Ellipse2D.Float(x, y, diameter, diameter));
                    getIcon().paintIcon(this, g2, x, y);
                    g2.dispose();
                }
            }
        };
        
        ImageIcon avatarIcon = new ImageIcon(ImagenUtil.cargarImagen(path)
                .getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        lblAvatar.setIcon(avatarIcon);
        lblAvatar.setPreferredSize(new Dimension(120, 120));
        lblAvatar.setMaximumSize(new Dimension(120, 120));
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelFoto.add(lblAvatar);
        panelFoto.add(Box.createVerticalStrut(10));
        
        JButton btnSeleccionarFoto = new JButton("Seleccionar foto");
        btnSeleccionarFoto.setFont(EstilosApp.FUENTE_BOTON);
        btnSeleccionarFoto.setForeground(Color.WHITE);
        btnSeleccionarFoto.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnSeleccionarFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnSeleccionarFoto.addActionListener(e -> {
            // Llamar al controlador para seleccionar imagen; se asume que retorna una URL o path
            
            path = Controlador.INSTANCIA.seleccionarImagenPerfil();
            if(path != null && !path.isEmpty()){
                try {
                    // ...existing code reemplazado...
                    Image image = ImagenUtil.cargarImagenDesdeArchivo(path);
                    if(image != null){
                        // Escalar la imagen a 120x120 para que se visualice correctamente
                        Image scaled = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                        lblAvatar.setIcon(new ImageIcon(scaled));
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            
        });
        
        panelFoto.add(btnSeleccionarFoto);
        panelFoto.add(Box.createVerticalStrut(30));
        
        // Agregar el panelFoto al inicio del panelFormulario previo a los demás campos
        panelFormulario.add(panelFoto);

        // Campo de usuario
        JLabel labelUsuario = new JLabel("Nombre de usuario");
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
        campoUsuario.addFocusListener(crearEfectoFocus(campoUsuario));

        //Campo teléfono 
        JLabel labelTlf = new JLabel("Teléfono");
        labelTlf.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelTlf.setForeground(EstilosApp.COLOR_TEXTO);
        labelTlf.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        campoTlf = new JTextField(20);
        campoTlf.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoTlf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));
        campoTlf.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoTlf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Efecto focus en campo telefono
        campoTlf.addFocusListener(crearEfectoFocus(campoTlf));
        
        // Campo de email
        JLabel labelEmail = new JLabel("Correo electrónico");
        labelEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelEmail.setForeground(EstilosApp.COLOR_TEXTO);
        labelEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        campoEmail = new JTextField(20);
        campoEmail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoEmail.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));
        campoEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Efecto focus en campo email
        campoEmail.addFocusListener(crearEfectoFocus(campoEmail));


        // Campo de saludo
        JLabel labelSaludo = new JLabel("Saludo");
        labelSaludo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelSaludo.setForeground(EstilosApp.COLOR_TEXTO);
        labelSaludo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        campoSaludo = new JTextField(20);
        campoSaludo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoSaludo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));
        campoSaludo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoSaludo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Efecto focus en campo email
        campoSaludo.addFocusListener(crearEfectoFocus(campoSaludo));
        
        panelFormulario.add(labelSaludo);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(campoSaludo);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Nueva sección: Fecha de Nacimiento usando JDateChooser
        JLabel labelFechaNacimiento = new JLabel("Fecha de Nacimiento");
        labelFechaNacimiento.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelFechaNacimiento.setForeground(EstilosApp.COLOR_TEXTO);
        labelFechaNacimiento.setAlignmentX(Component.LEFT_ALIGNMENT);
        dateChooserNacimiento = new JDateChooser();
        dateChooserNacimiento.setDateFormatString("dd/MM/yyyy");

        dateChooserNacimiento.setPreferredSize(new Dimension(400, 45));
        dateChooserNacimiento.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        dateChooserNacimiento.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelFormulario.add(labelFechaNacimiento);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(dateChooserNacimiento);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));
        
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
        campoPassword.addFocusListener(crearEfectoFocus(campoPassword));
        
        // Campo de confirmación de contraseña
        JLabel labelConfirmPassword = new JLabel("Confirmar contraseña");
        labelConfirmPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelConfirmPassword.setForeground(EstilosApp.COLOR_TEXTO);
        labelConfirmPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        campoConfirmPassword = new JPasswordField(20);
        campoConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoConfirmPassword.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));
        campoConfirmPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoConfirmPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Efecto focus en campo confirmación contraseña
        campoConfirmPassword.addFocusListener(crearEfectoFocus(campoConfirmPassword));
        
        // Opciones de login
        JPanel panelLogin = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelLogin.setOpaque(false);
        panelLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel textoLogin = new JLabel("¿Ya tienes una cuenta?");
        textoLogin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textoLogin.setForeground(EstilosApp.COLOR_TEXTO_SECUNDARIO);
        
        JLabel linkLogin = new JLabel("Iniciar sesión");
        linkLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        linkLogin.setForeground(EstilosApp.COLOR_PRIMARIO);
        linkLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.LOGIN);
            }
        });
        
        panelLogin.add(textoLogin);
        panelLogin.add(Box.createRigidArea(new Dimension(5, 0)));
        panelLogin.add(linkLogin);
        
        // Agregar campos al panel de formulario
        panelFormulario.add(labelUsuario);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(campoUsuario);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));

        panelFormulario.add(labelTlf);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(campoTlf);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));
        
        panelFormulario.add(labelEmail);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(campoEmail);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));

        panelFormulario.add(labelSaludo);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(campoSaludo);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));

        panelFormulario.add(labelFechaNacimiento);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(dateChooserNacimiento);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));
        
        panelFormulario.add(labelPassword);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(campoPassword);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 20)));
        
        panelFormulario.add(labelConfirmPassword);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 8)));
        panelFormulario.add(campoConfirmPassword);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 15))); 
        
        // Añadir panel para seleccionar rol
       

        // Botón de registro
        JButton btnRegistrar = new JButton("Crear Cuenta");
        btnRegistrar.setFont(EstilosApp.FUENTE_BOTON);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnRegistrar.setBorder(new EmptyBorder(15, 30, 15, 30));
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRegistrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        btnRegistrar.addActionListener(e -> {
            // Validación de campos
            if (campoUsuario.getText().isEmpty() || campoEmail.getText().isEmpty() 
            ||  campoTlf.getText().isEmpty() 
            || campoPassword.getPassword().length == 0 || campoConfirmPassword.getPassword().length == 0) {
                JOptionPane.showMessageDialog(panelPrincipal, 
                        "Rellena los campos obligatorios", 
                        "Error de registro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!campoEmail.getText().matches("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(panelPrincipal, 
                        "Por favor, introduce un correo válido (nombre@dominio).", 
                        "Correo inválido", JOptionPane.ERROR_MESSAGE);
                campoUsuario.requestFocus();
                return;
            }
            
            // Validar coincidencia de contraseñas
            if (!String.valueOf(campoPassword.getPassword()).equals(
                    String.valueOf(campoConfirmPassword.getPassword()))) {
                JOptionPane.showMessageDialog(panelPrincipal, 
                        "Las contraseñas no coinciden", 
                        "Error de registro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            // Simulación de registro exitoso
            boolean exito = Controlador.INSTANCIA.registrarUsuario(campoEmail.getText(), campoUsuario.getText(), 
                    String.valueOf(campoPassword.getPassword()), campoTlf.getText(), path,
                    campoSaludo.getText());
            if(exito && path != null && !path.isEmpty()){
            JOptionPane.showMessageDialog(panelPrincipal, 
                    "Registro completado con éxito. Ya puedes iniciar sesión.", 
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            }
            else if(Controlador.INSTANCIA.emailRegistrado(campoEmail.getText())){
                JOptionPane.showMessageDialog(panelPrincipal, 
                        "El correo ya está registrado.", 
                        "Error de registro", JOptionPane.ERROR_MESSAGE);
            }
            else if(Controlador.INSTANCIA.tlfRegistrado(campoTlf.getText())){
                JOptionPane.showMessageDialog(panelPrincipal, 
                        "El teléfono ya está registrado.", 
                        "Error de registro", JOptionPane.ERROR_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(panelPrincipal, 
                "Registro fallido.", 
                "Registro fallido ", JOptionPane.INFORMATION_MESSAGE);
            }
            
            // Redirigir a login
            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.LOGIN);
        });

        
      
        
        
       
        
        panelFormulario.add(btnRegistrar);
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 25)));
        panelFormulario.add(panelLogin);
        
        // Armar el panel completo
        panelRegistro.add(panelTitulo);
        panelRegistro.add(descripcion);
        panelRegistro.add(panelFormulario);
        
        return panelRegistro;
    }
    
    // Método auxiliar para crear el efecto de focus en los campos
    private FocusAdapter crearEfectoFocus(JTextField campo) {
        return new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(EstilosApp.COLOR_PRIMARIO, 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));
            }
        };
    }
    
    @Override
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    
    @Override
    public void alMostrar() {
        campoUsuario.setText("");
        campoEmail.setText("");
        campoPassword.setText("");
        campoConfirmPassword.setText("");
        campoTlf.setText("");
        campoSaludo.setText("");
        campoUsuario.requestFocus();
        // Limpiar el campo de fecha de nacimiento
        dateChooserNacimiento.setDate(null);
        // Limpiar la foto
        lblAvatar.setIcon(new ImageIcon(ImagenUtil.cargarImagen("/images/avatar_default.png")
                .getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        path = "/images/avatar_default.png"; // Restablecer el path a la imagen por defecto

    }
    
    @Override
    public void alOcultar() {
        // No se requiere acción especial al ocultar
    }
    
    @Override
    public TipoVentana getTipo() {
        return TipoVentana.REGISTRO;
    }
}
