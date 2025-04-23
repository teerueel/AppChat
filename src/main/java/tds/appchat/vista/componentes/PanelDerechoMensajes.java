package tds.appchat.vista.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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

import jakarta.persistence.criteria.CriteriaBuilder.In;
import tds.BubbleText;
import tds.appchat.controlador.Controlador;
import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.modelo.contactos.ContactoIndividual;
import tds.appchat.modelo.util.TipoMensaje;
import tds.appchat.sesion.Sesion;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.vista.util.ImagenUtil;

public class PanelDerechoMensajes extends JPanel {
     Contacto seleccionado;
   

    public PanelDerechoMensajes() {
        if(GestorVentanas.INSTANCIA != null) {
            seleccionado = GestorVentanas.INSTANCIA.getVentanaApp().getSelectedContacto();
         }
        inicializarComponentes();

    }

    public PanelDerechoMensajes(Contacto contacto) {
        this.seleccionado = contacto;
        inicializarComponentes();
    }

    public void inicializarComponentes(){
        setLayout(new BorderLayout());
        // Añadir borde con título según el contacto seleccionado
        if(seleccionado != null) {
            setBorder(BorderFactory.createTitledBorder("Mensajes con " + seleccionado.getNombre()));
        } else {
            setBorder(BorderFactory.createTitledBorder("Mensajes"));
        }
        this.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        this.setBackground(Color.WHITE);

        JPanel panelTexto = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelTexto.setBackground(Color.WHITE);
        panelTexto.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
       

        if(seleccionado == null) {
            JLabel lbl = new JLabel("Seleccione un contacto", SwingConstants.CENTER);
            lbl.setFont(EstilosApp.FUENTE_NORMAL);
            lbl.setForeground(EstilosApp.COLOR_TEXTO);
            add(lbl, BorderLayout.CENTER);
        }


        else{
           
            JPanel chat=new JPanel();
            chat.setLayout(new BoxLayout(chat,BoxLayout.Y_AXIS));
            chat.setSize(400,10000);
            chat.setMinimumSize(new Dimension(400, 10000));
            chat.setMaximumSize(new Dimension(400, 10000));
            chat.setPreferredSize(new Dimension(400, 10000));
            
            if(!seleccionado.getMensajes().isEmpty()){
                
            
                for(Mensaje mensaje : ((ContactoIndividual) seleccionado).getMensajes()){

                    
                    if(mensaje.getTipo() == TipoMensaje.ENVIADO){
                        if(mensaje.isEmoji()){
                            chat.add(new BubbleText(chat, mensaje.getEmoji(), EstilosApp.COLOR_PRIMARIO,
                            Sesion.INSTANCIA.getUsuarioActual().getNombre(), BubbleText.SENT, 18));
                        }
                        
                        else{
                        chat.add(new BubbleText(chat, mensaje.getTexto(), EstilosApp.COLOR_PRIMARIO,
                        Sesion.INSTANCIA.getUsuarioActual().getNombre(), BubbleText.SENT));
                        }
                        
                    }
                    else if(mensaje.getTipo() == TipoMensaje.RECIBIDO){
                        if(mensaje.isEmoji()){
                            chat.add(new BubbleText(chat, mensaje.getEmoji(), EstilosApp.COLOR_SECUNDARIO,
                            seleccionado.getNombre(), BubbleText.RECEIVED, 18));
                        }
                        else{
                        chat.add(new BubbleText(chat, mensaje.getTexto(), EstilosApp.COLOR_SECUNDARIO,
                        seleccionado.getNombre(), BubbleText.RECEIVED));
                        }
                        
                    }
                }
            }

            JScrollPane scroll = new JScrollPane(chat);
            scroll.setBackground(Color.GREEN);
            
            scroll.setSize(400, 700);
            scroll.setMinimumSize(new Dimension(400 , 700));
            scroll.setMaximumSize(new Dimension(400,700));
            scroll.setPreferredSize(new Dimension(400,700));
            add(scroll, BorderLayout.CENTER);
            this.add(scroll, BorderLayout.CENTER);
           
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

        //Botón para emojis
        ImageIcon iconoEmoji = new ImageIcon("C:\\Users\\Antonio\\Desktop\\Nueva carpeta\\src\\main\\resources\\images\\BotonEmoji2.gif");
        JButton btnEmojis = new JButton(iconoEmoji); // Cambia el texto por un emoji o icono
        btnEmojis.setFont(EstilosApp.FUENTE_BOTON);
        btnEmojis.setForeground(Color.YELLOW);
        btnEmojis.setBackground(Color.YELLOW);
        btnEmojis.setPreferredSize(new Dimension(50, 45));
        btnEmojis.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnEmojis.addActionListener(e -> {
            Controlador.INSTANCIA.enviarEmoji((int)(Math.random() * BubbleText.MAXICONO), seleccionado);
            GestorVentanas.INSTANCIA.getVentanaApp().updatePanelDerecho(seleccionado);
        });

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
            GestorVentanas.INSTANCIA.getVentanaApp().updatePanelDerecho(seleccionado);
            campoTexto.setText(""); // Limpiar el campo de texto después de enviar
        });
        panelTexto.add(btnEmojis);
        panelTexto.add(btnEnviar);

        this.add(panelTexto, BorderLayout.SOUTH);

    }
}
