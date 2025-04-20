package tds.appchat.vista.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.hibernate.mapping.Component;

import tds.BubbleText;
import tds.appchat.controlador.Controlador;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.ContactoIndividual;
import tds.appchat.modelo.util.TipoMensaje;
import tds.appchat.sesion.Sesion;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.util.EstilosApp;

public class PanelDerechoMensajes extends JPanel {
     Contacto seleccionado;
   

    public PanelDerechoMensajes() {
        inicializarComponentes();

    }

    public void inicializarComponentes(){
        setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        this.setBackground(Color.WHITE);

        JPanel panelTexto = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelTexto.setBackground(Color.WHITE);
        panelTexto.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        if(GestorVentanas.INSTANCIA != null) {
            seleccionado = GestorVentanas.INSTANCIA.getVentanaApp().getSelectedContacto();
         }

        else if(seleccionado == null) {
            JLabel lbl = new JLabel("Seleccione un contacto", SwingConstants.CENTER);
            lbl.setFont(EstilosApp.FUENTE_NORMAL);
            lbl.setForeground(EstilosApp.COLOR_TEXTO);
            add(lbl, BorderLayout.CENTER);
        }


        else{
            //Aquí escribo los mensajes.
           /*  JPanel listaMensajes = new JPanel();
            listaMensajes.setLayout(new BoxLayout(listaMensajes, BoxLayout.Y_AXIS));
            listaMensajes.setBackground(Color.ORANGE);
            listaMensajes.setMaximumSize(new Dimension(300, 300)); */
            JPanel chat=new JPanel();
            chat.setLayout(new BoxLayout(chat,BoxLayout.Y_AXIS));
            chat.setSize(400,700);
            chat.setMinimumSize(new Dimension(400,700));
            chat.setMaximumSize(new Dimension(400,700));
            chat.setPreferredSize(new Dimension(400,700));
            
            
            for(Mensaje mensaje : ((ContactoIndividual) seleccionado).getMensajes()){

                
              
                
                if(mensaje.getTipo() == TipoMensaje.ENVIADO){
                    System.out.println("Enviado: " + mensaje.getTexto());
                
                     chat.add(new BubbleText(chat, mensaje.getTexto(), EstilosApp.COLOR_PRIMARIO,
                     Sesion.INSTANCIA.getUsuarioActual().getNombre(), BubbleText.SENT));
                     
                    
                }
                else if(mensaje.getTipo() == TipoMensaje.RECIBIDO){
                    System.out.println("Recibido: " + mensaje.getTexto());
                    chat.add(new BubbleText(chat, mensaje.getTexto(), EstilosApp.COLOR_SECUNDARIO,
                    seleccionado.getNombre(), BubbleText.RECEIVED));
                    
                    
                }
            }

            this.add(chat, BorderLayout.CENTER);
           /*  JScrollPane scroll = new JScrollPane(chat);
            scroll.setBackground(Color.GREEN);
            scroll.setMaximumSize(new Dimension(300, 300));
            add(scroll, BorderLayout.CENTER);*/
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
            String texto = campoTexto.getText().trim();
            if (texto.isEmpty()) {
                return;
            }
            Controlador.INSTANCIA.enviarMensaje(texto, seleccionado);
            GestorVentanas.INSTANCIA.getVentanaApp().updatePanelDerecho();
            campoTexto.setText(""); // Limpiar el campo de texto después de enviar
        });

        panelTexto.add(btnEnviar);

        this.add(panelTexto, BorderLayout.SOUTH);

    }
}
