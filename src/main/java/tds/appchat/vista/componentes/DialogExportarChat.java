package tds.appchat.vista.componentes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class DialogExportarChat extends JDialog {
    private boolean aceptado = false;

    public DialogExportarChat(Frame owner) {
        super(owner, "Exportar conversación a PDF", true);
        setLayout(new BorderLayout(10, 10)); // márgenes internos más pequeños

        JLabel titulo = new JLabel("Exportar Chat", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16)); // tamaño más pequeño

        JTextArea descripcion = new JTextArea("¿Seguro que desea exportar este chat a PDF?");
        descripcion.setFont(new Font("SansSerif", Font.PLAIN, 12)); // tamaño más pequeño
        descripcion.setEditable(false);
        descripcion.setOpaque(false);
        descripcion.setFocusable(false);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // menos relleno

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        // Botones más compactos
        btnAceptar.setMargin(new Insets(5, 10, 5, 10));
        btnCancelar.setMargin(new Insets(5, 10, 5, 10));

        btnAceptar.addActionListener(e -> {
            aceptado = true;
            JOptionPane.showMessageDialog(this,
                "Se procederá con la descarga en breves instantes",
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        });

        btnCancelar.addActionListener(e -> {
            aceptado = false;
            dispose();
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(titulo, BorderLayout.NORTH);
        add(descripcion, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(350, 180)); // tamaño más pequeño
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    public static boolean mostrar(Frame owner) {
        DialogExportarChat dialogo = new DialogExportarChat(owner);
        dialogo.setVisible(true);
        return dialogo.aceptado;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            boolean resultado = DialogExportarChat.mostrar(null);
            System.out.println("¿Se ha aceptado la oferta? " + resultado);
        });
    }
}

