package tds.appchat.vista.pantallas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import tds.appchat.controlador.Controlador;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.Grupo;
import tds.appchat.sesion.Sesion;
import tds.appchat.vista.componentes.TarjetaAddContacto;
import tds.appchat.vista.util.EstilosApp;

public class DialogSeleccionarUnContacto extends JDialog {
    private Contacto selectedContacto = null;
    private JPanel panelListado;
    private JPanel selectedWrapper = null; // para tener referencia de la tarjeta seleccionada (wrapper)

    public DialogSeleccionarUnContacto(Frame owner) {
        super(owner, "Selecciona un Contacto", true);
        inicializarComponentes();
    }
    
    public DialogSeleccionarUnContacto(Frame owner, Grupo grupo) {
        super(owner, "Selecciona un Contacto", true);
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
        List<Contacto> contactos = Sesion.INSTANCIA.haySesion() ?
            Sesion.INSTANCIA.getUsuarioActual().getContactos() : new ArrayList<>();
        
        // Por cada contacto se crea una tarjeta única para selección
        for(Contacto c : contactos) {
            TarjetaAddContacto tarjeta = new TarjetaAddContacto(c);
            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setOpaque(false);
            wrapper.add(tarjeta, BorderLayout.CENTER);
            wrapper.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

            wrapper.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Deseleccionar la tarjeta previamente seleccionada si existe
                    if(selectedWrapper != null && selectedWrapper != wrapper) {
                        selectedWrapper.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                    }
                    // Si se hace clic en el mismo wrapper, deseleccionar
                    if(selectedWrapper == wrapper) {
                        wrapper.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                        selectedWrapper = null;
                        selectedContacto = null;
                    } else {
                        wrapper.setBorder(BorderFactory.createLineBorder(EstilosApp.COLOR_PRIMARIO, 2));
                        selectedWrapper = wrapper;
                        selectedContacto = c;
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
            selectedContacto = null;
            dispose();
        });
        panelBotones.add(btnCancelar);
        panelBotones.add(btnAceptar);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    public Contacto getSelectedContacto() {
        return selectedContacto;
    }
}
