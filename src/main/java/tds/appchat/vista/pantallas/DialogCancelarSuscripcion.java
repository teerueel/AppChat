package tds.appchat.vista.pantallas;

import javax.swing.*;
import java.awt.*;

public class DialogCancelarSuscripcion extends JDialog {

    private boolean cancelado = false;

    public DialogCancelarSuscripcion(Frame owner) {
        super(owner, "Cancelar suscripción", true);
        setLayout(new BorderLayout(15, 15));

        JLabel mensaje = new JLabel("¿Seguro que deseas cancelar?", JLabel.CENTER);
        mensaje.setFont(new Font("SansSerif", Font.BOLD, 16));
        mensaje.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));

        JButton btnAceptar = new JButton("Sí, cancelar");
        JButton btnCancelar = new JButton("No, volver atrás");

        btnAceptar.addActionListener(e -> {
            cancelado = true;
            JOptionPane.showMessageDialog(this,
                "Suscripción cancelada con éxito.",
                "Cancelación confirmada",
                JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        });

        btnCancelar.addActionListener(e -> {
            cancelado = false;
            dispose();
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(mensaje, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setSize(350, 170);
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    public static boolean mostrar(Frame owner) {
        DialogCancelarSuscripcion dialogo = new DialogCancelarSuscripcion(owner);
        dialogo.setVisible(true);
        return dialogo.cancelado;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            boolean resultado = DialogCancelarSuscripcion.mostrar(null);
            System.out.println("¿Se ha cancelado realmente? " + !resultado);
        });
    }
}

