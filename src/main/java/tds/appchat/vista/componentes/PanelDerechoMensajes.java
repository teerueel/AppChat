package tds.appchat.vista.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.hibernate.mapping.Component;

import tds.BubbleText;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.ContactoIndividual;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.util.EstilosApp;

public class PanelDerechoMensajes extends JPanel {
     Contacto seleccionado;
   

    public PanelDerechoMensajes() {
        inicializarComponentes();

    }

    public void inicializarComponentes(){
        setLayout(new BorderLayout());

        JPanel panelTexto = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelTexto.setBackground(Color.WHITE);
        panelTexto.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        if(GestorVentanas.INSTANCIA != null) {
            seleccionado = GestorVentanas.INSTANCIA.getVentanaApp().getSelectedContacto();
         }

         if(seleccionado == null) {
            JLabel lbl = new JLabel("Seleccione un contacto", SwingConstants.CENTER);
            lbl.setFont(EstilosApp.FUENTE_NORMAL);
            lbl.setForeground(EstilosApp.COLOR_TEXTO);
            add(lbl, BorderLayout.CENTER);
        }

        else{
            //Aquí escribo los mensajes.
            new BubbleText(panelTexto, ((ContactoIndividual) seleccionado).getUltimoMensaje().get().getTexto(), 
            getBackground(), TOOL_TIP_TEXT_KEY, ABORT);
        }

        // Campo de texto para escribir el mensaje
        JTextField  campoTexto = new JTextField(20);
        
        campoTexto.setFont(EstilosApp.FUENTE_NORMAL);
        campoTexto.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));
        campoTexto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        // Efecto focus en campo usuario
        campoTexto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campoTexto.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(EstilosApp.COLOR_PRIMARIO, 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                campoTexto.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));
            }
        });

        panelTexto.add(campoTexto);

        // Botón para enviar el mensaje
        JButton btnEnviar = new JButton("Enviar");
        btnEnviar.setFont(EstilosApp.FUENTE_BOTON);
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnEnviar.setPreferredSize(new Dimension(100, 45));
        btnEnviar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnEnviar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad disponible próximamente", 
            "Enviar Mensaje", JOptionPane.INFORMATION_MESSAGE);
            
            campoTexto.setText(""); // Limpiar el campo de texto después de enviar
        });

        panelTexto.add(btnEnviar);

        this.add(panelTexto, BorderLayout.SOUTH);

    }
}
