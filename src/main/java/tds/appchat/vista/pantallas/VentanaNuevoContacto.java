package tds.appchat.vista.pantallas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.vista.core.GestorVentanas;

public class VentanaNuevoContacto extends JFrame implements Ventana{

    private JPanel panelPrincipal;
   
    private JTextField campoTelefono;
   private JTextField campoUsuario; // Cambiado a campoUsuario para mayor claridad

    public VentanaNuevoContacto() {
        inicializarComponentes();
    }

    public void inicializarComponentes() {
        setTitle("Nuevo Contacto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 180);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(EstilosApp.COLOR_FONDO);

        // Instrucciones en la parte superior
        JLabel lblInstrucciones = new JLabel("Introduzca el nombre de su contacto y su teléfono", SwingConstants.CENTER);
        lblInstrucciones.setFont(EstilosApp.FUENTE_SUBTITULO);
        lblInstrucciones.setForeground(EstilosApp.COLOR_TEXTO);
        lblInstrucciones.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelPrincipal.add(lblInstrucciones, BorderLayout.NORTH);

         // Campo de usuario
         JLabel labelUsuario = new JLabel("Nombre");
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
         
         // Efecto focus en campoUsuario
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
         
         // Emulación para campoTelefono
         JLabel labelTelefono = new JLabel("Teléfono");
         labelTelefono.setFont(new Font("Segoe UI", Font.BOLD, 14));
         labelTelefono.setForeground(EstilosApp.COLOR_TEXTO);
         labelTelefono.setAlignmentX(Component.LEFT_ALIGNMENT);
         
         campoTelefono = new JTextField(20);
         campoTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 16));
         campoTelefono.setBorder(BorderFactory.createCompoundBorder(
             new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
             new EmptyBorder(10, 15, 10, 15)
         ));
         campoTelefono.setAlignmentX(Component.LEFT_ALIGNMENT);
         campoTelefono.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
         
         campoTelefono.addFocusListener(new FocusAdapter() {
             @Override
             public void focusGained(FocusEvent e) {
                 campoTelefono.setBorder(BorderFactory.createCompoundBorder(
                     new LineBorder(EstilosApp.COLOR_PRIMARIO, 1, true),
                     new EmptyBorder(10, 15, 10, 15)
                 ));
             }
             
             @Override
             public void focusLost(FocusEvent e) {
                 campoTelefono.setBorder(BorderFactory.createCompoundBorder(
                     new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                     new EmptyBorder(10, 15, 10, 15)
                 ));
             }
         });
         
         // Nuevo: Panel para agrupar campos y sus etiquetas, al estilo de VentanaLogin
         JPanel panelCampos = new JPanel();
         panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
         panelCampos.setBackground(EstilosApp.COLOR_FONDO);
         panelCampos.setBorder(new EmptyBorder(10, 20, 10, 20));
         
         panelCampos.add(labelUsuario);
         panelCampos.add(Box.createRigidArea(new Dimension(0, 8)));
         panelCampos.add(campoUsuario);
         panelCampos.add(Box.createRigidArea(new Dimension(0, 20)));
         panelCampos.add(labelTelefono);
         panelCampos.add(Box.createRigidArea(new Dimension(0, 8)));
         panelCampos.add(campoTelefono);
         
         // Agregar el panelCampos al panelPrincipal en la región central (entre instrucciones y botones)
         panelPrincipal.add(panelCampos, BorderLayout.CENTER);
         
         // Panel inferior: botones de "Aceptar" y "Cancelar"
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(EstilosApp.COLOR_FONDO);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(EstilosApp.FUENTE_BOTON);
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnAceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(EstilosApp.FUENTE_BOTON);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Acciones de botones
        btnAceptar.addActionListener(e -> {
            // Aquí se implementaría la lógica para registrar el nuevo contacto.
            JOptionPane.showMessageDialog(VentanaNuevoContacto.this, "Funcionalidad sin implementar");
        });

        btnCancelar.addActionListener(e -> {
            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.CONTACTOS);
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
    }

  
   
    @Override
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    @Override
    public void alMostrar() {
        campoUsuario.setText("");
        
        campoUsuario.requestFocus();
        campoTelefono.setText("");
        campoTelefono.requestFocus();
       
    }

    @Override
    public void alOcultar() {
        // ...
    }

    @Override
    public TipoVentana getTipo() {
        return TipoVentana.NUEVO_CONTACTO; // Asegúrese de definir NUEVO_CONTACTO en TipoVentana
    }
}
