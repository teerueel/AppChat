package tds.appchat.vista.pantallas;

import tds.appchat.controlador.Controlador;
import tds.appchat.modelo.EstadisticasUsuario;
import tds.appchat.sesion.Sesion;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.util.EstilosApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaEstadisticas implements Ventana, Recargable {
    private JPanel panelPrincipal;

    public VentanaEstadisticas() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(EstilosApp.COLOR_FONDO);
        
        // Título
        JLabel lblTitulo = new JLabel("Estadísticas del Usuario", SwingConstants.CENTER);
        lblTitulo.setFont(EstilosApp.FUENTE_TITULO);
        lblTitulo.setForeground(EstilosApp.COLOR_PRIMARIO);
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Contenido
        JPanel contenidoPanel = new JPanel();
        contenidoPanel.setLayout(new BoxLayout(contenidoPanel, BoxLayout.Y_AXIS));
        contenidoPanel.setBackground(EstilosApp.COLOR_FONDO);
        contenidoPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        EstadisticasUsuario stats = null;
        if(Sesion.INSTANCIA.haySesion()) {
            stats = Sesion.INSTANCIA.getUsuarioActual().getStats();
        }
        
        if(stats == null) {
            JLabel lblNoStats = new JLabel("No hay estadísticas disponibles.");
            lblNoStats.setFont(EstilosApp.FUENTE_NORMAL);
            lblNoStats.setForeground(EstilosApp.COLOR_TEXTO);
            lblNoStats.setAlignmentX(Component.CENTER_ALIGNMENT);
            contenidoPanel.add(lblNoStats);
        } else {
            // Mostramos los distintos campos
            JLabel lblCursosCompletados = new JLabel("Cursos Completados: " + stats.getNumCursosCompletados());
            JLabel lblCursosEnProgreso = new JLabel("Cursos en Progreso: " + stats.getNumCursosEnProgreso());
            JLabel lblTiempoUso = new JLabel("Tiempo de Uso (ms): " + stats.getTiempoUso());
            JLabel lblMejorRacha = new JLabel("Mejor Racha: " + stats.getMejorRacha());
            
            lblCursosCompletados.setFont(EstilosApp.FUENTE_NORMAL);
            lblCursosCompletados.setForeground(EstilosApp.COLOR_TEXTO);
            lblCursosCompletados.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            lblCursosEnProgreso.setFont(EstilosApp.FUENTE_NORMAL);
            lblCursosEnProgreso.setForeground(EstilosApp.COLOR_TEXTO);
            lblCursosEnProgreso.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            lblTiempoUso.setFont(EstilosApp.FUENTE_NORMAL);
            lblTiempoUso.setForeground(EstilosApp.COLOR_TEXTO);
            lblTiempoUso.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            lblMejorRacha.setFont(EstilosApp.FUENTE_NORMAL);
            lblMejorRacha.setForeground(EstilosApp.COLOR_TEXTO);
            lblMejorRacha.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            contenidoPanel.add(lblCursosCompletados);
            contenidoPanel.add(Box.createRigidArea(new Dimension(0,10)));
            contenidoPanel.add(lblCursosEnProgreso);
            contenidoPanel.add(Box.createRigidArea(new Dimension(0,10)));
            contenidoPanel.add(lblTiempoUso);
            contenidoPanel.add(Box.createRigidArea(new Dimension(0,10)));
            contenidoPanel.add(lblMejorRacha);
        }
        
        // Botón para volver al perfil
        JButton btnVolver = new JButton("Volver al Perfil");
        btnVolver.setFont(EstilosApp.FUENTE_BOTON);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.PERFIL));
        
        contenidoPanel.add(Box.createRigidArea(new Dimension(0,20)));
        contenidoPanel.add(btnVolver);
        
        JScrollPane scrollPane = new JScrollPane(contenidoPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(EstilosApp.COLOR_FONDO);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    @Override
    public void alMostrar() {
        // Se puede recargar la información si fuera necesario
    }

    @Override
    public void alOcultar() {
        // ...
    }

    @Override
    public TipoVentana getTipo() {
        return TipoVentana.ESTADISTICAS;
    }

    @Override
    public void recargar() {
        Controlador.INSTANCIA.actualizarTiempoUso(System.currentTimeMillis()-Sesion.INSTANCIA.getTiempoInicioSesion());
        panelPrincipal.removeAll();
        inicializarComponentes();
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
}
