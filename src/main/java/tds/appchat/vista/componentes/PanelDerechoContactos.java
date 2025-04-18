package tds.appchat.vista.componentes;

import javax.swing.*;
import java.awt.*;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.Grupo;
import tds.appchat.modelo.contactos.ContactoIndividual;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.pantallas.VentanaContactos;

public class PanelDerechoContactos extends JPanel {

    public PanelDerechoContactos() {
        inicializarComponentes();
    }
    
    void inicializarComponentes(){
        setLayout(new BorderLayout());
        
        Contacto seleccionado = null;
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
            // Panel para listar cada integrante con TarjetaAddContacto
            JPanel listaIntegrantes = new JPanel();
            listaIntegrantes.setLayout(new BoxLayout(listaIntegrantes, BoxLayout.Y_AXIS));
            listaIntegrantes.setBackground(Color.WHITE);
            
            for (Contacto integrante : grupo.getContactos()) {
                // Se usa TarjetaAddContacto para representar cada integrante
                TarjetaAddContacto tarjeta = new TarjetaAddContacto(integrante);
                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                wrapper.add(tarjeta, BorderLayout.CENTER);
                listaIntegrantes.add(wrapper);
            }
            JScrollPane scroll = new JScrollPane(listaIntegrantes);
            add(scroll, BorderLayout.CENTER);
        }
        
        // Agregar botón "Añadir Grupo" en la parte inferior
        // Botón "Añadir Contacto" debajo de la lista
        JButton btnAddGrupo = new JButton("Añadir Grupo");
        btnAddGrupo.setFont(EstilosApp.FUENTE_BOTON);
        btnAddGrupo.setForeground(Color.WHITE);
        btnAddGrupo.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnAddGrupo.addActionListener(e -> {
            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.NUEVO_GRUPO);
        });
        this.add(btnAddGrupo, BorderLayout.SOUTH);
    }
}
