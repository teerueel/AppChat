package tds.appchat.vista.componentes;

import javax.swing.*;

import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.sesion.Sesion;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.pantallas.VentanaContactos;
import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.util.EstilosApp;

import java.awt.*;
import java.awt.event.*;

public class PanelIzquierdoContactos extends JPanel {
    // Nuevas variables para almacenar la selección
    private Contacto selectedContacto;
    private JPanel selectedPanel;

    public PanelIzquierdoContactos(){
        inicializarComponentes();
    }

    void inicializarComponentes(){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JPanel listaContactos = new JPanel();
        listaContactos.setLayout(new BoxLayout(listaContactos, BoxLayout.Y_AXIS));
        listaContactos.setBackground(Color.WHITE);
        listaContactos.setBorder(BorderFactory.createTitledBorder("Contactos"));    
        // Obtener la lista de contactos del usuario actual y añadirlos a la lista
        if(Sesion.INSTANCIA.haySesion()){
            java.util.List<Contacto> contactos = Sesion.INSTANCIA.getUsuarioActual().getContactos();
            listaContactos.removeAll();
            for(Contacto contacto : contactos) {
                // Utilizar TarjetaAddContacto y envolverla para poder seleccionar
                TarjetaAddContacto tarjeta = new TarjetaAddContacto(contacto);
                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.setOpaque(false);
                wrapper.add(tarjeta, BorderLayout.CENTER);
                wrapper.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                wrapper.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Deseleccionar el panel previamente seleccionado 
                        if(selectedPanel != null) {
                            selectedPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                        }
                        selectedPanel = wrapper;
                        selectedPanel.setBorder(BorderFactory.createLineBorder(EstilosApp.COLOR_PRIMARIO, 2));
                        selectedContacto = contacto;
                        // Llamada al método sincronizador del panel derecho
                        
                        GestorVentanas.INSTANCIA.getVentanaContactos().updatePanelDerecho();
                        
                    }
                });
                listaContactos.add(wrapper);
            }
        }
        // Envolver la lista en un JScrollPane
        JScrollPane scrollLista = new JScrollPane(listaContactos);
        scrollLista.setPreferredSize(new Dimension(350, 500));
        this.add(scrollLista, BorderLayout.CENTER);
        
        // Botón "Añadir Contacto" debajo de la lista
        JButton btnAddContacto = new JButton("Añadir Contacto");
        btnAddContacto.setFont(EstilosApp.FUENTE_BOTON);
        btnAddContacto.setForeground(Color.WHITE);
        btnAddContacto.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnAddContacto.addActionListener(e -> {
            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.NUEVO_CONTACTO);
        });
        this.add(btnAddContacto, BorderLayout.SOUTH);
    }
    
    // Método que devuelve el contacto seleccionado
   
    
    public Contacto getSelectedContacto() {
        return selectedContacto;
    }
   
}
