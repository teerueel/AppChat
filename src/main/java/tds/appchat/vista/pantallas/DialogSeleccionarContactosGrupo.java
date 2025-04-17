package tds.appchat.vista.pantallas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.sesion.Sesion;
import tds.appchat.vista.componentes.TarjetaAddContacto;
import tds.appchat.vista.util.EstilosApp;

public class DialogSeleccionarContactosGrupo extends JDialog {
    private java.util.List<Contacto> selectedContactos = new ArrayList<>();
    private JPanel panelListado;

    public DialogSeleccionarContactosGrupo(Frame owner) {
        super(owner, "Selecciona Contactos", true);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setSize(500, 400);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));
        
        panelListado = new JPanel();
        panelListado.setLayout(new BoxLayout(panelListado, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(panelListado);
        scroll.getViewport().setBackground(EstilosApp.COLOR_FONDO);
        add(scroll, BorderLayout.CENTER);
        
        // Cargar contactos del usuario actual
        java.util.List<Contacto> contactos = Sesion.INSTANCIA.haySesion() ?
            Sesion.INSTANCIA.getUsuarioActual().getContactos() : new ArrayList<>();
        
        // Por cada contacto se crea una tarjeta con capacidad de selección
        for(Contacto c : contactos) {
            TarjetaAddContacto tarjeta = new TarjetaAddContacto(c);
            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setOpaque(false);
            wrapper.add(tarjeta, BorderLayout.CENTER);
            wrapper.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            // Estado de selección
            final boolean[] seleccionado = {false};
            // Al hacer clic se alterna la selección visual y se agrega/quita el contacto
            tarjeta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    seleccionado[0] = !seleccionado[0];
                    if(seleccionado[0]) {
                        wrapper.setBorder(BorderFactory.createLineBorder(EstilosApp.COLOR_PRIMARIO, 2));
                        selectedContactos.add(c);
                    } else {
                        wrapper.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                        selectedContactos.remove(c);
                    }
                }
            });
            panelListado.add(wrapper);
        }
        
        // Panel inferior con botones Aceptar y Cancelar
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(EstilosApp.FUENTE_BOTON);
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnAceptar.addActionListener(e -> dispose());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(EstilosApp.FUENTE_BOTON);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBackground(EstilosApp.COLOR_BOTON);
        btnCancelar.addActionListener(e -> {
            selectedContactos.clear();
            dispose();
        });
        panelBotones.add(btnCancelar);
        panelBotones.add(btnAceptar);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    public java.util.List<Contacto> getSelectedContactos() {
        return selectedContactos;
    }
}
