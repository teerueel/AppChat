package tds.appchat.vista.componentes;

import tds.appchat.vista.util.EstilosApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Botón personalizado con efectos visuales mejorados.
 */
public class CustomButton extends JButton {
    private Color colorNormal;
    private Color colorHover;
    private Color colorPressed;
    private Color colorTexto;
    private boolean esPrimario = true;
    
    /**
     * Crea un botón primario con el texto especificado y acción predeterminada.
     * 
     * @param texto Texto del botón
     */
    public CustomButton(String texto) {
        this(texto, null, true);
    }
    
    /**
     * Crea un botón primario con el texto especificado y la acción indicada.
     * 
     * @param texto Texto del botón
     * @param accion Acción a ejecutar al hacer clic
     */
    public CustomButton(String texto, ActionListener accion) {
        this(texto, accion, true);
    }
    
    /**
     * Crea un botón con el texto especificado, la acción indicada y el tipo indicado.
     * 
     * @param texto Texto del botón
     * @param accion Acción a ejecutar al hacer clic
     * @param esPrimario Si es true, el botón será de tipo primario, si es false, será secundario
     */
    public CustomButton(String texto, ActionListener accion, boolean esPrimario) {
        super(texto);
        this.esPrimario = esPrimario;
        configurarEstilos();
        
        if (accion != null) {
            addActionListener(accion);
        }
        
        // Eliminar efectos visuales predeterminados de Java
        setFocusPainted(false);
        setBorderPainted(true);
        setContentAreaFilled(true);
        
        // Agregar efectos de hover y clic
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(colorHover);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(colorNormal);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(colorPressed);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(colorHover);
            }
        });
    }
    
    /**
     * Configura el botón como primario o secundario.
     * 
     * @param esPrimario Si es true, el botón será de tipo primario, si es false, será secundario
     */
    public void setPrimario(boolean esPrimario) {
        this.esPrimario = esPrimario;
        configurarEstilos();
        repaint();
    }
    
    private void configurarEstilos() {
        if (esPrimario) {
            colorNormal = EstilosApp.COLOR_PRIMARIO;
            colorHover = EstilosApp.COLOR_PRIMARIO.darker();
            colorPressed = new Color(
                Math.max(0, EstilosApp.COLOR_PRIMARIO.getRed() - 30),
                Math.max(0, EstilosApp.COLOR_PRIMARIO.getGreen() - 30),
                Math.max(0, EstilosApp.COLOR_PRIMARIO.getBlue() - 30)
            );
            colorTexto = Color.WHITE;
            setBorder(new EmptyBorder(10, 20, 10, 20));
        } else {
            colorNormal = Color.WHITE;
            colorHover = new Color(245, 245, 245);
            colorPressed = new Color(230, 230, 230);
            colorTexto = EstilosApp.COLOR_PRIMARIO;
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstilosApp.COLOR_PRIMARIO, 1, true),
                new EmptyBorder(9, 19, 9, 19)
            ));
        }
        
        setBackground(colorNormal);
        setForeground(colorTexto);
        setFont(EstilosApp.FUENTE_BOTON);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dibujar fondo del botón
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
        
        // Si es secundario, dibujar borde
        if (!esPrimario) {
            g2.setColor(EstilosApp.COLOR_PRIMARIO);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
        }
        
        g2.dispose();
        
        super.paintComponent(g);
    }
}