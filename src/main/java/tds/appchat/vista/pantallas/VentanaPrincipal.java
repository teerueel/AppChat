package tds.appchat.vista.pantallas;


import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.vista.util.ImagenUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * Ventana principal de la aplicación.
 */
public class VentanaPrincipal implements Ventana {
    private JPanel panelPrincipal;
    
    public VentanaPrincipal() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout(0, 0));
        panelPrincipal.setBackground(EstilosApp.COLOR_FONDO);
            
        // Panel con scroll para todo el contenido
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(EstilosApp.COLOR_FONDO);
        panelContenido.setBorder(new EmptyBorder(20, 40, 40, 40)); // Añadir espacio alrededor del contenido
        
        // Secciones a la página
        panelContenido.add(crearSeccionHero());
       
        // Crear scroll pane
        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(EstilosApp.COLOR_FONDO);
        
        // Barra de desplazamiento  
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = EstilosApp.COLOR_PRIMARIO.brighter();
                this.trackColor = EstilosApp.COLOR_FONDO;
            }
            
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
            
            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
        
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Crea la sección principal (hero) de la página de inicio
     */
    private JPanel crearSeccionHero() {
        JPanel panelHero = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Crear un gradiente suave de fondo
                int w = getWidth();
                int h = getHeight();
                Color colorInicio = new Color(245, 247, 250);
                Color colorFin = EstilosApp.COLOR_TARJETA;
                GradientPaint gp = new GradientPaint(
                    0, 0, colorInicio,
                    0, h, colorFin);
                    
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
                
                // Borde redondeado
                g2d.setColor(new Color(230, 230, 230));
                g2d.setStroke(new BasicStroke(1f));
                g2d.drawRoundRect(0, 0, w-1, h-1, 15, 15);
                
                g2d.dispose();
            }
        };
        
        panelHero.setLayout(new BoxLayout(panelHero, BoxLayout.Y_AXIS));
        panelHero.setBorder(new EmptyBorder(60, 80, 60, 80));
        panelHero.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelHero.setMaximumSize(new Dimension(Integer.MAX_VALUE, 600)); 
        
        // Panel para el contenido del hero (imagen + texto)
        JPanel contenidoHero = new JPanel(new GridLayout(1, 2, 50, 0));
        contenidoHero.setOpaque(false);
        contenidoHero.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Panel para el texto del hero
        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setOpaque(false);
        
        // Título principal actualizado
        JLabel labelTitulo = new JLabel("<html><div style='text-shadow: 1px 1px 2px rgba(0,0,0,0.2);'>Conecta al instante en AppChat</div></html>");
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 44));
        labelTitulo.setForeground(EstilosApp.COLOR_PRIMARIO);
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Subtítulo actualizado
        JTextArea areaDescripcion = new JTextArea(
            "Conversa en tiempo real, comparte momentos y mantente conectado con tus amigos en AppChat."
        );
        areaDescripcion.setEditable(false);
        areaDescripcion.setWrapStyleWord(true);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setFont(new Font(EstilosApp.FUENTE_SUBTITULO.getName(), Font.PLAIN, EstilosApp.FUENTE_SUBTITULO.getSize() + 2));
        areaDescripcion.setForeground(new Color(60, 60, 70));
        areaDescripcion.setBackground(new Color(0, 0, 0, 0));
        areaDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Panel para los botones con mejor espaciado
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelBotones.setOpaque(false);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        
      
        
        // Añadir elementos al panel de texto con mejor espaciado
        panelTexto.add(labelTitulo);
        panelTexto.add(Box.createRigidArea(new Dimension(0, 30))); 
        panelTexto.add(areaDescripcion);
        panelTexto.add(Box.createRigidArea(new Dimension(0, 40))); 
        panelTexto.add(panelBotones);
        
        // Panel para la imagen
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.setOpaque(false);
        
        // Mejorar la presentación de la imagen
        JLabel labelImagen = new JLabel();
        Image imgHero = ImagenUtil.cargarImagen("/images/hero_image.jpg");
        if (imgHero != null) {
            ImageIcon icon = new ImageIcon(imgHero);
            int originalWidth = icon.getIconWidth();
            int originalHeight = icon.getIconHeight();
            float ratio = (float) originalWidth / originalHeight;
            int newHeight = 380;  
            int newWidth = Math.round(newHeight * ratio);
            
            Image scaledImage = imgHero.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);
            // Crear borde redondeado para la imagen
            BufferedImage roundedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = roundedImage.createGraphics();
            
            // Activar antialiasing para bordes suaves
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Dibujar imagen con bordes redondeados
            RoundRectangle2D roundRect = new RoundRectangle2D.Float(0, 0, newWidth, newHeight, 15, 15);
            g2.setClip(roundRect);
            g2.drawImage(scaledImage, 0, 0, null);
            
            // Añadir un sutil borde
            g2.setClip(null);
            g2.setColor(new Color(210, 210, 210));
            g2.setStroke(new BasicStroke(1f));
            g2.draw(roundRect);
            
            g2.dispose();
            
            labelImagen.setIcon(new ImageIcon(roundedImage));
        } else {
            // Si no encuentra la imagen, mostrar un placeholder mejorado
            labelImagen.setText("<html><div style='text-align: center;'>APPCHAT</div></html>");
            labelImagen.setFont(new Font("Segoe UI", Font.BOLD, 28));
            labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
            labelImagen.setForeground(EstilosApp.COLOR_PRIMARIO);
            labelImagen.setPreferredSize(new Dimension(520, 380)); // Mayor tamaño
            
            // Borde 
            labelImagen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstilosApp.COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(40, 20, 40, 20)
            ));
        }
        
        panelImagen.add(labelImagen, BorderLayout.CENTER);
        
        // Añadir los paneles al contenido del hero
        contenidoHero.add(panelTexto);
        contenidoHero.add(panelImagen);
        
        panelHero.add(contenidoHero);
        
        // Añadir efecto de sombra al panel
        panelHero.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10, 10, 20, 10),
            BorderFactory.createCompoundBorder()
        ));
        
        return panelHero;
    }
    
    @Override
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    
    @Override
    public void alMostrar() {
        // Reiniciar posición del scroll al inicio
        SwingUtilities.invokeLater(() -> {
            Container container = panelPrincipal.getParent();
            while (container != null && !(container instanceof JScrollPane)) {
                container = container.getParent();
            }
            
            if (container != null) {
                JScrollPane scrollPane = (JScrollPane) container;
                scrollPane.getVerticalScrollBar().setValue(0);
            }
        });
    }
    
    @Override
    public void alOcultar() {
        // Acciones al ocultar la ventana principal
    }
    
    @Override
    public TipoVentana getTipo() {
        return TipoVentana.PRINCIPAL;
    }
}