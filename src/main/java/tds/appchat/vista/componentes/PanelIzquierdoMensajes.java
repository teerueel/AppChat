package tds.appchat.vista.componentes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import tds.appchat.controlador.Controlador;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.util.EstilosApp;

public class PanelIzquierdoMensajes extends JPanel {
    
    private Contacto selectedContacto;
    private Mensaje selectedMensaje;
    private JPanel selectedPanel;
    
    public PanelIzquierdoMensajes(){
        inicializarComponentes();
    }
    
    private void inicializarComponentes(){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Panel que contendrá las tarjetas de mensajes
        JPanel listaMensajes = new JPanel();
        listaMensajes.setLayout(new BoxLayout(listaMensajes, BoxLayout.Y_AXIS));
        listaMensajes.setBackground(Color.WHITE);
        listaMensajes.setBorder(BorderFactory.createTitledBorder("Mensajes"));
        
        // Obtener el mapa de últimos mensajes
        Map<Contacto, Mensaje> ultimosMensajes = Controlador.INSTANCIA.getUltimosMensajes();
        
        // Vaciar el panel (en caso de refrescar)
        
        listaMensajes.removeAll();

        // Si no hay mensajes, mostrar un mensaje informativo
        if(ultimosMensajes == null){
            JLabel lbl = new JLabel("No hay mensajes recientes", SwingConstants.CENTER);
            lbl.setFont(EstilosApp.FUENTE_NORMAL);
            lbl.setForeground(EstilosApp.COLOR_TEXTO);
            listaMensajes.add(lbl);
            add(listaMensajes, BorderLayout.CENTER);
            return;
        }
        else{
            
        
        // Iterar sobre cada par (Contacto, Mensaje) para crear la tarjeta y el wrapper
            for(Map.Entry<Contacto, Mensaje> entry : ultimosMensajes.entrySet()){
                System.out.println("Contacto: " + entry.getKey().getNombre() + ", Mensaje: " + entry.getValue().getTexto());
                Contacto contacto = entry.getKey();
                Mensaje mensaje = entry.getValue();
                
                TarjetaContactoMensaje tarjeta = new TarjetaContactoMensaje(contacto, mensaje);
                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.setOpaque(false);
                wrapper.add(tarjeta, BorderLayout.CENTER);
                wrapper.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                
                wrapper.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Deseleccionar el panel previamente seleccionado 
                        if(selectedPanel != null) {
                            selectedPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                        }
                        selectedPanel = wrapper;
                        Border selectedBorder = BorderFactory.createLineBorder(EstilosApp.COLOR_PRIMARIO, 2);
                        selectedPanel.setBorder(selectedBorder);
                        selectedContacto = contacto;
                        selectedMensaje = mensaje;
                        GestorVentanas.INSTANCIA.getVentanaApp().updatePanelDerecho(selectedContacto);
                    }
                });
                
                listaMensajes.add(wrapper);
            }
        }
        // Envolver la lista en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(listaMensajes);
        scrollPane.setPreferredSize(new Dimension(350, 500));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // Getters para el contacto y mensaje seleccionados
    public Contacto getSelectedContacto(){
        return selectedContacto;
    }
    
    public Mensaje getSelectedMensaje(){
        return selectedMensaje;
    }
    
    // Método para actualizar (refrescar) la lista
    public void actualizarPanel() {
        removeAll();
        inicializarComponentes();
        revalidate();
        repaint();
    }
}
