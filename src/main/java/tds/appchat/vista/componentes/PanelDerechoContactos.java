package tds.appchat.vista.componentes;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import tds.appchat.controlador.Controlador;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.Grupo;
import tds.appchat.modelo.contactos.ContactoIndividual;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.core.TipoVentana;

public class PanelDerechoContactos extends JPanel {

    Contacto seleccionado;

    public PanelDerechoContactos() {
        inicializarComponentes();
    }
    
    void inicializarComponentes(){
        setLayout(new BorderLayout());
        
        // Obtener la selección actual desde VentanaContactos (sincronizador)
        if(GestorVentanas.INSTANCIA != null) {
             seleccionado = GestorVentanas.INSTANCIA.getVentanaContactos().getSelectedContacto();
        }
        
        if(seleccionado == null) {
            JLabel lbl = new JLabel("Seleccione un contacto", SwingConstants.CENTER);
            lbl.setFont(EstilosApp.FUENTE_NORMAL);
            lbl.setForeground(EstilosApp.COLOR_TEXTO);
            add(lbl, BorderLayout.CENTER);
        }
        else if(seleccionado instanceof ContactoIndividual) {
            JLabel lbl = new JLabel("Por favor, seleccione un grupo", SwingConstants.CENTER);
            lbl.setFont(EstilosApp.FUENTE_NORMAL);
            lbl.setForeground(EstilosApp.COLOR_TEXTO);
            add(lbl, BorderLayout.CENTER);
        }
        else if(seleccionado instanceof Grupo) {
            

            Grupo grupo = (Grupo) seleccionado;
            // Lista para almacenar los contactos seleccionados
            final List<Contacto> selectedIntegrantes = new ArrayList<>();
            
            // Panel para listar cada integrante con TarjetaAddContacto y toggle de selección
            JPanel listaIntegrantes = new JPanel();
            listaIntegrantes.setLayout(new BoxLayout(listaIntegrantes, BoxLayout.Y_AXIS));
            listaIntegrantes.setBackground(Color.WHITE);
            
            for (Contacto integrante : grupo.getContactos()) {
                
                // Se usa TarjetaAddContacto para representar cada integrante
                TarjetaAddContacto tarjeta = new TarjetaAddContacto(integrante);
                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                wrapper.add(tarjeta, BorderLayout.CENTER);
                wrapper.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                // Listener para alternar selección
                wrapper.addMouseListener(new MouseAdapter(){
                    private boolean isSelected = false;
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        isSelected = !isSelected;
                        if(isSelected) {
                            wrapper.setBorder(BorderFactory.createLineBorder(EstilosApp.COLOR_PRIMARIO, 2));
                            selectedIntegrantes.add(integrante);
                        } else {
                            wrapper.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                            selectedIntegrantes.remove(integrante);
                        }
                    }
                });
                listaIntegrantes.add(wrapper);
            }
            JScrollPane scroll = new JScrollPane(listaIntegrantes);
            add(scroll, BorderLayout.CENTER);
        }
            
            // Panel de botones para acciones
         
            // Botón "Añadir Grupo"
            JButton btnAddGrupo = new JButton("Añadir Grupo");
            btnAddGrupo.setFont(EstilosApp.FUENTE_BOTON);
            btnAddGrupo.setForeground(Color.WHITE);
            btnAddGrupo.setBackground(EstilosApp.COLOR_PRIMARIO);
            
            btnAddGrupo.addActionListener(e -> {
                GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.NUEVO_GRUPO);
            });
            this.add(btnAddGrupo,BorderLayout.SOUTH);

            
            // Botón "Eliminar"
            
        
    }
}
