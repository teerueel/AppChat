package tds.appchat.vista.componentes;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.ContactoIndividual;
import tds.appchat.modelo.contactos.Grupo;
import tds.appchat.vista.util.ImagenUtil;
import tds.appchat.vista.util.EstilosApp;

public class TarjetaAddContacto extends JPanel {

    private Contacto contacto;
    private JLabel lblImagen;
    private JLabel lblNombre;
    private JLabel lblTelefono;
    private JLabel lblSaludo;

    public TarjetaAddContacto(Contacto contacto) {
        this.contacto = contacto;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new CompoundBorder(
                new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                new EmptyBorder(10, 10, 10, 10)));
        setBackground(EstilosApp.COLOR_TARJETA);

        // Crear etiqueta para la imagen
        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(80, 80));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        Image image = null;
        if(contacto instanceof ContactoIndividual) {
            image = ImagenUtil.cargarImagen(((ContactoIndividual) contacto).getImagen());
        } else if(contacto instanceof Grupo) {
            image = ImagenUtil.cargarImagen("/images/grupo_default.jpg");
        }
        if(image == null) {
            image = ImagenUtil.cargarImagen("/images/avatar_default.png");
        }
        Image scaled = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(scaled));

        // Panel para la información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(EstilosApp.COLOR_TARJETA);

        lblNombre = new JLabel(contacto.getNombre());
        lblNombre.setFont(EstilosApp.FUENTE_TARJETA_TITULO);
        lblNombre.setForeground(EstilosApp.COLOR_TEXTO);
        panelInfo.add(lblNombre);

        // Solo agregar teléfono y saludo si es ContactoIndividual
        if(contacto instanceof ContactoIndividual) {
            lblTelefono = new JLabel(((ContactoIndividual) contacto).getTelefono());
            lblTelefono.setFont(EstilosApp.FUENTE_TARJETA_TEXTO);
            lblTelefono.setForeground(EstilosApp.COLOR_TEXTO);
            panelInfo.add(Box.createVerticalStrut(5));
            panelInfo.add(lblTelefono);

            lblSaludo = new JLabel(((ContactoIndividual) contacto).getSaludo());
            lblSaludo.setFont(EstilosApp.FUENTE_NORMAL);
            lblSaludo.setForeground(EstilosApp.COLOR_TEXTO_SECUNDARIO);
            panelInfo.add(Box.createVerticalStrut(5));
            panelInfo.add(lblSaludo);
        }
        
        add(lblImagen, BorderLayout.WEST);
        add(panelInfo, BorderLayout.CENTER);
    }
}
