package tds.appchat.vista.pantallas;

import tds.appchat.sesion.Sesion;
import tds.appchat.modelo.Usuario;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.vista.util.ImagenUtil;
import tds.appchat.vista.core.GestorVentanas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaPerfil implements Ventana {
    private JPanel panelPrincipal;
    private JPanel panelContenido;

    public VentanaPerfil() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel principal
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(EstilosApp.COLOR_FONDO);
        
        // Panel de contenido con scroll
        panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(EstilosApp.COLOR_FONDO);
        panelContenido.setBorder(new EmptyBorder(30, 30, 30, 30));

        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(EstilosApp.COLOR_FONDO);
        
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void construirPanelPerfil() {
        panelContenido.removeAll();
        panelContenido.revalidate();
        panelContenido.repaint();
        
        if (!Sesion.INSTANCIA.haySesion()) {
            JPanel panelMensaje = new JPanel(new BorderLayout());
            panelMensaje.setBackground(EstilosApp.COLOR_TARJETA);
            panelMensaje.setBorder(new EmptyBorder(20, 20, 20, 20));
            
            JLabel lblMensaje = new JLabel("Debe iniciar sesión para ver su perfil");
            lblMensaje.setFont(EstilosApp.FUENTE_TITULO);
            lblMensaje.setHorizontalAlignment(JLabel.CENTER);
            panelMensaje.add(lblMensaje, BorderLayout.CENTER);
            
            panelContenido.add(Box.createVerticalStrut(50));
            panelContenido.add(panelMensaje);
            panelContenido.add(Box.createVerticalGlue());
            return;
        }
        
        // Panel para mostrar la información del usuario
        JPanel panelPerfil = new JPanel();
        panelPerfil.setLayout(new BoxLayout(panelPerfil, BoxLayout.Y_AXIS));
        panelPerfil.setBackground(EstilosApp.COLOR_FONDO);
        panelPerfil.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Título
        JLabel lblTitulo = new JLabel("Perfil de usuario");
        lblTitulo.setFont(EstilosApp.FUENTE_TITULO);
        lblTitulo.setForeground(EstilosApp.COLOR_TEXTO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPerfil.add(lblTitulo);
        panelPerfil.add(Box.createVerticalStrut(20));
        
        // Panel para avatar con borde redondeado y efecto de sombra
        JPanel panelAvatar = new JPanel();
        panelAvatar.setLayout(new BoxLayout(panelAvatar, BoxLayout.Y_AXIS));
        panelAvatar.setOpaque(false);
        panelAvatar.setMaximumSize(new Dimension(150, 150));
        panelAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Crear avatar con borde circular
        JLabel lblAvatar = new JLabel() {
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
        
        ImageIcon avatarIcon = new ImageIcon(ImagenUtil.cargarImagenDesdeArchivo(Sesion.INSTANCIA.getUsuarioActual().getImagen())
                .getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        lblAvatar.setIcon(avatarIcon);
        lblAvatar.setPreferredSize(new Dimension(120, 120));
        lblAvatar.setMaximumSize(new Dimension(120, 120));
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelAvatar.add(lblAvatar);
        panelPerfil.add(panelAvatar);
        panelPerfil.add(Box.createVerticalStrut(30));
        
        // Información del usuario con mejor diseño
        Usuario user = Sesion.INSTANCIA.getUsuarioActual();
       
        
        // Panel de tarjeta para información
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setLayout(new BoxLayout(panelTarjeta, BoxLayout.Y_AXIS));
        panelTarjeta.setBackground(EstilosApp.COLOR_TARJETA);
        panelTarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)));
        panelTarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTarjeta.setMaximumSize(new Dimension(500, 300));
        
        // Etiquetas con mejor formato
        JLabel lblNombre = crearEtiquetaInfo("Nombre", user.getNombre());
        JLabel lblCorreo = crearEtiquetaInfo("Correo", user.getEmail());
        
        
        panelTarjeta.add(lblNombre);
        panelTarjeta.add(Box.createVerticalStrut(12));
        panelTarjeta.add(new JSeparator());
        panelTarjeta.add(Box.createVerticalStrut(12));
        panelTarjeta.add(lblCorreo);
        panelTarjeta.add(Box.createVerticalStrut(12));
        panelTarjeta.add(new JSeparator());
        panelTarjeta.add(Box.createVerticalStrut(12));
       
        
        panelPerfil.add(panelTarjeta);
        
        // Añadir botón para ver estadísticas
        JButton btnEstadisticas = new JButton("Ver Estadísticas");
        btnEstadisticas.setFont(EstilosApp.FUENTE_BOTON);
        btnEstadisticas.setForeground(Color.WHITE);
        btnEstadisticas.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnEstadisticas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEstadisticas.addActionListener(e -> {
            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.ESTADISTICAS);
        });
        panelPerfil.add(Box.createVerticalStrut(20));
        panelPerfil.add(btnEstadisticas);
        
        // Añadir espacio al final
        panelPerfil.add(Box.createVerticalStrut(30));
        
        panelContenido.add(panelPerfil);
    }
    
    /**
     * Crea una etiqueta con formato para mostrar información del perfil
     */
    private JLabel crearEtiquetaInfo(String titulo, String valor) {
        JLabel etiqueta = new JLabel("<html><b>" + titulo + ":</b> " + valor + "</html>");
        etiqueta.setFont(EstilosApp.FUENTE_NORMAL);
        etiqueta.setForeground(EstilosApp.COLOR_TEXTO);
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        return etiqueta;
    }
    
    @Override
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    @Override
    public void alMostrar() {
        // Se actualiza solo el contenido
        construirPanelPerfil();
    }

    @Override
    public void alOcultar() {
        // No se necesita hacer nada al ocultar
    }

    @Override
    public TipoVentana getTipo() {
        return TipoVentana.PERFIL;
    }
    
   
}
