package tds.appchat.vista.pantallas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.util.EstilosApp;

public class VentanaPremium extends JFrame implements Ventana, Recargable{
	private JPanel panelPrincipal;

    public VentanaPremium() {
    	inicializarComponentes();
    }
    public void inicializarComponentes() {
        setTitle("Suscripción Premium");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(EstilosApp.COLOR_FONDO);

        // Panel superior con título
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(EstilosApp.COLOR_FONDO);
        panelSuperior.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel lblTitulo = new JLabel("Suscripción Premium", SwingConstants.CENTER);
        lblTitulo.setFont(EstilosApp.FUENTE_TITULO);
        lblTitulo.setForeground(EstilosApp.COLOR_PRIMARIO);
        lblTitulo.setBorder(new EmptyBorder(15, 0, 15, 0));
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Panel central con contenido
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(EstilosApp.COLOR_FONDO);
        panelCentral.setBorder(new EmptyBorder(30, 50, 30, 50));

        // Botón de "Pasarse a Premium"
        JButton btnPremium = new JButton("Pasarse a Premium");
        btnPremium.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPremium.setFont(EstilosApp.FUENTE_NORMAL);
        btnPremium.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnPremium.setForeground(Color.WHITE);
        btnPremium.setFocusPainted(false);
        btnPremium.setMaximumSize(new Dimension(200, 40));
        panelCentral.add(btnPremium);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 30)));

        // Etiquetas de estado
        JLabel lblEstado = new JLabel("Estado de la suscripción: Activa");
        lblEstado.setFont(EstilosApp.FUENTE_NORMAL);
        lblEstado.setForeground(EstilosApp.COLOR_TEXTO);
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(lblEstado);

        // Botón "Cancelar suscripción"
        JButton btnCancelar = new JButton("Cancelar suscripción");
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelar.setFont(EstilosApp.FUENTE_NORMAL);
        btnCancelar.setBackground(Color.RED.darker());
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setMaximumSize(new Dimension(200, 40));
        panelCentral.add(btnCancelar);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        setContentPane(panelPrincipal);
    }

    @Override
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    @Override
    public void alMostrar() {
        // Aquí puedes poner lógica para cuando se muestre la ventana
        System.out.println("Ventana Premium mostrada.");
    }

    @Override
    public void alOcultar() {
        // Aquí puedes poner lógica para cuando se oculte la ventana
        System.out.println("Ventana Premium oculta.");
    }

    @Override
    public TipoVentana getTipo() {
        return TipoVentana.PREMIUM;
    }
    
    @Override
    public void recargar() {
    	panelPrincipal.removeAll();
		inicializarComponentes();
		panelPrincipal.revalidate();
		panelPrincipal.repaint();
    }
}
