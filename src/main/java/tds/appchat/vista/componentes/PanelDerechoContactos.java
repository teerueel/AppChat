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
import tds.appchat.vista.pantallas.DialogSeleccionarContactosGrupo;

public class PanelDerechoContactos extends JPanel {

    Contacto seleccionado;
    List<Contacto> selectedIntegrantes = new ArrayList<>(); // Lista para almacenar los contactos seleccionados

    public PanelDerechoContactos() {
        
        inicializarComponentes();
        
    }
    
    void inicializarComponentes(){
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
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

            

               // Botón "Eliminar"
            JButton btnEliminar = new JButton("Eliminar");
            btnEliminar.setFont(EstilosApp.FUENTE_BOTON);
            btnEliminar.setForeground(Color.WHITE);
            btnEliminar.setBackground(Color.RED);
               
            btnEliminar.addActionListener(e -> {
                if(selectedIntegrantes.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Seleccione al menos un contacto para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Controlador.INSTANCIA.eliminarContactos(selectedIntegrantes, (Grupo) seleccionado);
                GestorVentanas.INSTANCIA.getVentanaContactos().updatePanelDerecho();
               });
            panelBotones.add(btnEliminar,BorderLayout.SOUTH);
   
               // Botón "Añadir Contactos"
            JButton btnAddContactos = new JButton("Añadir Contactos");
            btnAddContactos.setFont(EstilosApp.FUENTE_BOTON);
            btnAddContactos.setForeground(Color.WHITE);
            btnAddContactos.setBackground(EstilosApp.COLOR_PRIMARIO);
            btnAddContactos.addActionListener(e -> {
                    // Mostrar ventana de selección de contactos
                List<Contacto> contactosSeleccionados = new ArrayList<>();
                DialogSeleccionarContactosGrupo dialog = 
                new DialogSeleccionarContactosGrupo(GestorVentanas.INSTANCIA.getVentanaContactos(),
                (Grupo) seleccionado);
                dialog.setVisible(true);
                contactosSeleccionados = dialog.getSelectedContactos();
                // Agregar contactos seleccionados al grupo
                Controlador.INSTANCIA.agregarContactosGrupo(contactosSeleccionados, (Grupo) seleccionado);
                // Actualizar la vista de contactos
                GestorVentanas.INSTANCIA.getVentanaContactos().updatePanelDerecho();
            });
            panelBotones.add(btnAddContactos,BorderLayout.SOUTH);
        }
            
            
            // Botón "Añadir Grupo"
        JButton btnAddGrupo = new JButton("Añadir Grupo");
        btnAddGrupo.setFont(EstilosApp.FUENTE_BOTON);
        btnAddGrupo.setForeground(Color.WHITE);
        btnAddGrupo.setBackground(EstilosApp.COLOR_PRIMARIO);
            
        btnAddGrupo.addActionListener(e -> {
            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.NUEVO_GRUPO);
            });
        panelBotones.add(btnAddGrupo,BorderLayout.SOUTH);

            
         

        //Añadimos el panel de botones al panel principal
        this.add(panelBotones, BorderLayout.SOUTH);
        
    }
}
