package tds.appchat.vista.pantallas;

import javax.swing.*;
import java.awt.*;

public class DialogAceptarSuscripcion extends JDialog {

    private boolean aceptado = false;

    public DialogAceptarSuscripcion(Frame owner) {
        super(owner, "Suscripción Premium", true);
        setLayout(new BorderLayout(15, 15));

        JLabel titulo = new JLabel("¡Hazte premium!", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));

        JTextArea descripcion = new JTextArea(
            "Por solo 2,99€/mes tendrás acceso a funciones exclusivas:\n\n" +
            "• Exporta tus chats a PDF en un solo clic.\n\n" +
            "¿Quieres activar tu suscripción ahora?"
        );
        descripcion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descripcion.setEditable(false);
        descripcion.setOpaque(false);
        descripcion.setFocusable(false);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnAceptar = new JButton("Aceptar oferta");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(e -> {
            aceptado = true;
            JOptionPane.showMessageDialog(this,
                "Se ha activado la suscripción premium.",
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        });

        btnCancelar.addActionListener(e -> {
            aceptado = false;
            dispose();
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(titulo, BorderLayout.NORTH);
        add(descripcion, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(500, 300));
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    public static boolean mostrar(Frame owner) {
        DialogAceptarSuscripcion dialogo = new DialogAceptarSuscripcion(owner);
        dialogo.setVisible(true);
        return dialogo.aceptado;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            boolean resultado = DialogAceptarSuscripcion.mostrar(null);
            System.out.println("¿Se ha aceptado la oferta? " + resultado);
        });
    }
}

