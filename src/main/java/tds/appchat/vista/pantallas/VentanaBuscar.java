package tds.appchat.vista.pantallas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.controlador.Controlador;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;


public class VentanaBuscar extends JFrame implements Ventana, Recargable {

    private JPanel panelPrincipal;
    private JPanel panelResultados;
    
    List<JLabel> etiquetasMensajes = new ArrayList<>();

    public VentanaBuscar() {
        inicializarComponentes();
    }

    public void inicializarComponentes() {
        setTitle("Búsqueda de mensajes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(EstilosApp.COLOR_FONDO);
        
        panelResultados = new JPanel(); //Dejamos ya inicializado el panel donde se mostrarán los resultados de la búsqueda
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        panelResultados.setBackground(EstilosApp.COLOR_FONDO);
        
        // Reemplazar el título directamente agregado al panelPrincipal por un panel superior con borde negro
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(EstilosApp.COLOR_FONDO);
        panelSuperior.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel lblTitulo = new JLabel("Búsqueda de mensajes", SwingConstants.CENTER);
        lblTitulo.setFont(EstilosApp.FUENTE_TITULO);
        lblTitulo.setForeground(EstilosApp.COLOR_PRIMARIO);
        lblTitulo.setBorder(new EmptyBorder(15, 0, 15, 0));
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central que agrupa el bloque de búsqueda y el área de resultados
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(EstilosApp.COLOR_FONDO);
        
        // Panel de búsqueda (con título "Buscar")
        JPanel panelBuscar = new JPanel();
        panelBuscar.setLayout(new BoxLayout(panelBuscar, BoxLayout.Y_AXIS));
        panelBuscar.setBackground(EstilosApp.COLOR_TARJETA);
        panelBuscar.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(EstilosApp.COLOR_TARJETA),
                "Buscar",
                0, 0, null, EstilosApp.COLOR_TEXTO_SECUNDARIO));
        
        // Campo superior para introducir "texto"
        JTextField txtTexto = new JTextField("texto", 20);
        txtTexto.setFont(EstilosApp.FUENTE_NORMAL);
        // Aumentar el alto para evitar que el texto se corte
        txtTexto.setMaximumSize(new Dimension(300, 45));
        txtTexto.setPreferredSize(new Dimension(300, 45));
        txtTexto.setForeground(Color.GRAY);
        txtTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtTexto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(txtTexto.getText().equals("texto")){
                    txtTexto.setText("");
                    txtTexto.setForeground(EstilosApp.COLOR_TEXTO);
                }
                txtTexto.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(EstilosApp.COLOR_PRIMARIO, 1, true),
                    new EmptyBorder(10, 15, 10, 15)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(txtTexto.getText().isEmpty()){
                    txtTexto.setText("texto");
                    txtTexto.setForeground(Color.GRAY);
                    txtTexto.setCaretPosition(0); // Asegurar que el texto se vea completo
                }
                txtTexto.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                    new EmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        // Panel inferior en el bloque de búsqueda para "telefono", "contacto" y botón "Buscar"
        JPanel panelInferiorBuscar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelInferiorBuscar.setBackground(EstilosApp.COLOR_TARJETA);
        
        JTextField txtTelefono = new JTextField("telefono", 10);
        txtTelefono.setFont(EstilosApp.FUENTE_NORMAL);
        // Aumentar el alto para evitar corte (por ejemplo, de 30 a 35)
        txtTelefono.setMaximumSize(new Dimension(300, 35));
        txtTelefono.setPreferredSize(new Dimension(300, 35));
        txtTelefono.setForeground(Color.GRAY);
        txtTelefono.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtTelefono.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));
        txtTelefono.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(txtTelefono.getText().equals("telefono")){
                    txtTelefono.setText("");
                    txtTelefono.setForeground(EstilosApp.COLOR_TEXTO);
                }
                txtTelefono.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(EstilosApp.COLOR_PRIMARIO, 1, true),
                    new EmptyBorder(5, 10, 5, 10)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(txtTelefono.getText().isEmpty()){
                    txtTelefono.setText("telefono");
                    txtTelefono.setForeground(Color.GRAY);
                }
                txtTelefono.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                    new EmptyBorder(5, 10, 5, 10)
                ));
            }
        });
        
        JTextField txtContacto = new JTextField("contacto", 10);
        txtContacto.setFont(EstilosApp.FUENTE_NORMAL);
        // Aumentar el alto para que coincida
        txtContacto.setMaximumSize(new Dimension(300, 35));
        txtContacto.setPreferredSize(new Dimension(300, 35));
        txtContacto.setForeground(Color.GRAY);
        txtContacto.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtContacto.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));
        txtContacto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(txtContacto.getText().equals("contacto")){
                    txtContacto.setText("");
                    txtContacto.setForeground(EstilosApp.COLOR_TEXTO);
                }
                txtContacto.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(EstilosApp.COLOR_PRIMARIO, 1, true),
                    new EmptyBorder(5, 10, 5, 10)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(txtContacto.getText().isEmpty()){
                    txtContacto.setText("contacto");
                    txtContacto.setForeground(Color.GRAY);
                }
                txtContacto.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                    new EmptyBorder(5, 10, 5, 10)
                ));
            }
        });
        
        // Ajustar el panelInferiorBuscar para centrar los recuadros verticalmente
        JPanel panelCamposInferiores = new JPanel();
        panelCamposInferiores.setLayout(new BoxLayout(panelCamposInferiores, BoxLayout.Y_AXIS));
        panelCamposInferiores.setBackground(EstilosApp.COLOR_TARJETA);
        panelCamposInferiores.add(txtTelefono);
        panelCamposInferiores.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCamposInferiores.add(txtContacto);
        
        // Crear un botón "Buscar"
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(EstilosApp.FUENTE_BOTON);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
            new EmptyBorder(5, 10, 5, 10)
         ));
        btnBuscar.addActionListener(e -> {
        	List<String> mensajes = Controlador.INSTANCIA.buscarMensajes(txtTexto.getText(), txtTelefono.getText(), txtContacto.getText());
        	for(String s : mensajes) {
        		JLabel lblMensaje = new JLabel(s);
        		lblMensaje.setFont(EstilosApp.FUENTE_NORMAL);
                lblMensaje.setForeground(EstilosApp.COLOR_TEXTO);
                lblMensaje.setBorder(new EmptyBorder(5, 10, 5, 10));
                panelResultados.add(lblMensaje);
                panelResultados.revalidate();
                panelResultados.repaint();
        	}
        });
        
        // Crear un panel para agrupar el bloque inferior junto al botón
        JPanel panelBloqueInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBloqueInferior.setBackground(EstilosApp.COLOR_TARJETA);
        panelBloqueInferior.add(panelCamposInferiores);
        panelBloqueInferior.add(btnBuscar);
        
        // Agregar al panel de búsqueda
        panelBuscar.add(txtTexto);
        panelBuscar.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBuscar.add(panelBloqueInferior);
        
        // Creamos el panel deslizante de resultados para contener al panel de resultados normal
        
        JScrollPane scrollResultados = new JScrollPane(panelResultados);
        scrollResultados.setPreferredSize(new Dimension(0, 200));
        
        panelCentral.add(panelBuscar);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(scrollResultados);
        
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
    }

    @Override
    public void recargar() {
        panelPrincipal.removeAll();
        inicializarComponentes();
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    @Override
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    @Override
    public void alMostrar() {
        // ...acciones al mostrar la ventana...
    }

    @Override
    public void alOcultar() {
        // ...acciones al ocultar la ventana...
    }

    @Override
    public TipoVentana getTipo() {
        return TipoVentana.BUSCAR; // Asegúrese de definir BUSCAR en TipoVentana
    }
}
