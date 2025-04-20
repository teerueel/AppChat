package tds.appchat.vista.componentes;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import tds.appchat.controlador.Controlador;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.vista.util.ImagenUtil;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.util.EstilosApp;

public class TarjetaContactoMensaje extends JPanel {
    
    private Contacto contacto;
    private Mensaje mensaje;
    
    public TarjetaContactoMensaje(Contacto contacto, Mensaje mensaje) {
        this.contacto = contacto;
        this.mensaje = mensaje;
        initComponents();
    }
    
    private void initComponents() {
        // Obtener mapa de últimos mensajes
        
        
        setLayout(new BorderLayout(10, 10));
        setBorder(new CompoundBorder(
                new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        setBackground(EstilosApp.COLOR_TARJETA);
        
        // Imagen del contacto
        JLabel lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(80, 80));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        Image image = ImagenUtil.cargarImagen(contacto.getImagen());
        if(image == null) {
            image = ImagenUtil.cargarImagen("/images/avatar_default.png");
        }
        Image scaled = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(scaled));
        add(lblImagen, BorderLayout.WEST);
        
        // Panel de la información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(EstilosApp.COLOR_TARJETA);
        
        // Nombre del contacto
        JLabel lblNombre = new JLabel(contacto.getNombre());
        lblNombre.setFont(EstilosApp.FUENTE_TARJETA_TITULO);
        lblNombre.setForeground(EstilosApp.COLOR_TEXTO);
        panelInfo.add(lblNombre);
        
        // Texto del mensaje y fecha (formateada)
        String mensajeTexto = (mensaje != null) ? mensaje.getTexto() : "Sin mensajes";
        String fechaTexto = "";
        if(mensaje != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // modificado para incluir hora
            fechaTexto = mensaje.getFecha().format(dtf);
        }
        
        JLabel lblMensaje = new JLabel(mensajeTexto);
        lblMensaje.setFont(EstilosApp.FUENTE_TARJETA_TEXTO);
        lblMensaje.setForeground(EstilosApp.COLOR_TEXTO);
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(lblMensaje);
        
        JLabel lblFecha = new JLabel(fechaTexto);
        lblFecha.setFont(EstilosApp.FUENTE_NORMAL);
        lblFecha.setForeground(EstilosApp.COLOR_TEXTO_SECUNDARIO);
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(lblFecha);
        
        add(panelInfo, BorderLayout.CENTER);

        if(!contacto.isAgregado()){
           JButton btnAgregar = new JButton("+");
            btnAgregar.setFont(EstilosApp.FUENTE_BOTON);
            btnAgregar.setForeground(Color.WHITE);
            btnAgregar.setBackground(EstilosApp.COLOR_PRIMARIO);
            btnAgregar.setMinimumSize(new Dimension(50, 50));
            btnAgregar.setMaximumSize(new Dimension(50, 50));
            btnAgregar.setPreferredSize(new Dimension(50, 50));
            btnAgregar.addActionListener(e -> {
                Frame parentFrame = JOptionPane.getFrameForComponent(this);
                DialogAgregarContacto dialog = new DialogAgregarContacto(parentFrame, this.contacto);
                dialog.setVisible(true);
                if(dialog.isConfirmed()){
                    String nombreContacto = dialog.getContactoNombre();
                    // Aquí se implementa la lógica para agregar el contacto utilizando el nombre introducido.
                    System.out.println("Contacto a agregar: " + nombreContacto);
                }
                GestorVentanas.INSTANCIA.getVentanaApp().updatePanelIzquierdo();
            });
            this.add(btnAgregar, BorderLayout.EAST);    
        }
        
    }
}