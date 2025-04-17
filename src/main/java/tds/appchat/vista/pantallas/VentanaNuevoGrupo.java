package tds.appchat.vista.pantallas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.controlador.Controlador;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.util.ImagenUtil;
import tds.appchat.modelo.contactos.Contacto;

public class VentanaNuevoGrupo extends JFrame implements Ventana, Recargable {

    private JPanel panelPrincipal;
    private JTextField campoNombreGrupo;
    private JLabel lblImagen;
    private String path = "/images/grupo_default.jpg"; // Imagen por defecto
    private List<Contacto> contactosSeleccionados = new ArrayList<>();
    private JLabel lblSelectedCount; // Muestra la cantidad de contactos seleccionados

    public VentanaNuevoGrupo() {
        inicializarComponentes();
    }

    public void inicializarComponentes() {
        setTitle("Nuevo Grupo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 350);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(EstilosApp.COLOR_FONDO);
        
        // Panel superior: Título
        JLabel lblTitulo = new JLabel("Registrar Nuevo Grupo", SwingConstants.CENTER);
        lblTitulo.setFont(EstilosApp.FUENTE_TITULO);
        lblTitulo.setBorder(new EmptyBorder(10,10,10,10));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel central: Imagen, ingreso del nombre y botón de "Añadir Contactos"
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(EstilosApp.COLOR_FONDO);
        panelCentro.setBorder(new EmptyBorder(10,20,10,20));
        
        // Panel para imagen
        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new BoxLayout(panelImagen, BoxLayout.Y_AXIS));
        panelImagen.setOpaque(false);
        panelImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblImagen = new JLabel();
        ImageIcon imagenIcon = new ImageIcon(ImagenUtil.cargarImagen(path)
                            .getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        lblImagen.setIcon(imagenIcon);
        lblImagen.setPreferredSize(new Dimension(120, 120));
        lblImagen.setMaximumSize(new Dimension(120, 120));
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelImagen.add(lblImagen);
        panelImagen.add(Box.createVerticalStrut(10));
        
        JButton btnImportarImagen = new JButton("Importar imagen");
        btnImportarImagen.setFont(EstilosApp.FUENTE_BOTON);
        btnImportarImagen.setForeground(Color.WHITE);
        btnImportarImagen.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnImportarImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnImportarImagen.addActionListener(e -> {
            path = Controlador.INSTANCIA.seleccionarImagenPerfil();
            if(path != null && !path.isEmpty()){
                try {
                    Image image = ImagenUtil.cargarImagen(path);
                    if(image != null){
                        Image scaled = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                        lblImagen.setIcon(new ImageIcon(scaled));
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        panelImagen.add(btnImportarImagen);
        panelCentro.add(panelImagen);
        panelCentro.add(Box.createVerticalStrut(15));
        
        // Campo para ingresar el nombre del grupo
        JLabel labelNombre = new JLabel("Nombre del Grupo");
        labelNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelNombre.setForeground(EstilosApp.COLOR_TEXTO);
        labelNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCentro.add(labelNombre);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 8)));
        
        campoNombreGrupo = new JTextField(20);
        campoNombreGrupo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoNombreGrupo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));
        campoNombreGrupo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campoNombreGrupo.setMaximumSize(new Dimension(300, 45));
        // Efecto focus para campo
        campoNombreGrupo.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e) {
                campoNombreGrupo.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(EstilosApp.COLOR_PRIMARIO, 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                campoNombreGrupo.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(EstilosApp.COLOR_BORDE, 1, true),
                        new EmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        panelCentro.add(campoNombreGrupo);
        panelCentro.add(Box.createVerticalStrut(15));
        
        // Eliminar la inserción anterior del botón "Añadir Contactos" y el contador, y crear un panel centrado para ambos
        JPanel panelContactos = new JPanel();
        panelContactos.setLayout(new BoxLayout(panelContactos, BoxLayout.Y_AXIS));
        panelContactos.setOpaque(false);
        panelContactos.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnAddContactos = new JButton("Añadir Contactos");
        btnAddContactos.setFont(EstilosApp.FUENTE_BOTON);
        btnAddContactos.setForeground(Color.WHITE);
        btnAddContactos.setBackground(EstilosApp.COLOR_SECUNDARIO);
        btnAddContactos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddContactos.addActionListener(e -> {
            DialogSeleccionarContactosGrupo dialog = new DialogSeleccionarContactosGrupo(this);
            dialog.setVisible(true);
            contactosSeleccionados = dialog.getSelectedContactos();
            lblSelectedCount.setText("Contactos seleccionados: " + contactosSeleccionados.size());
        });
        panelContactos.add(btnAddContactos);
        panelContactos.add(Box.createVerticalStrut(10));

        lblSelectedCount = new JLabel("Contactos seleccionados: 0");
        lblSelectedCount.setFont(EstilosApp.FUENTE_NORMAL);
        lblSelectedCount.setForeground(EstilosApp.COLOR_TEXTO);
        lblSelectedCount.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContactos.add(lblSelectedCount);

        panelCentro.add(panelContactos);
        panelCentro.add(Box.createVerticalStrut(15));
        
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        
        // Panel inferior: Botones "Crear Grupo" y "Cancelar"
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(EstilosApp.COLOR_FONDO);
        
        JButton btnCrearGrupo = new JButton("Crear Grupo");
        btnCrearGrupo.setFont(EstilosApp.FUENTE_BOTON);
        btnCrearGrupo.setForeground(Color.WHITE);
        btnCrearGrupo.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnCrearGrupo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCrearGrupo.addActionListener(e -> {
            if(campoNombreGrupo.getText().isEmpty()){
                JOptionPane.showMessageDialog(panelPrincipal, "Debe ingresar el nombre del grupo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean exito = Controlador.INSTANCIA.nuevoGrupo(campoNombreGrupo.getText(), path, contactosSeleccionados);
            if(exito){
                JOptionPane.showMessageDialog(panelPrincipal, "Grupo creado exitosamente.");
                GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.CONTACTOS);
            } else {
                JOptionPane.showMessageDialog(panelPrincipal, "Error al crear el grupo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
           
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(EstilosApp.FUENTE_BOTON);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBackground(EstilosApp.COLOR_PRIMARIO);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> {
            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.CONTACTOS);
        });
        
        panelBotones.add(btnCrearGrupo);
        panelBotones.add(btnCancelar);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
    }

    @Override
    public void recargar() {
        panelPrincipal.removeAll();
        path = "/images/grupo_default.jpg"; // Restablecer la imagen por defecto
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
        campoNombreGrupo.setText("");
        campoNombreGrupo.requestFocus();
        // Restablecer imagen por defecto
        lblImagen.setIcon(new ImageIcon(ImagenUtil.cargarImagen(path)
                .getScaledInstance(120,120,Image.SCALE_SMOOTH)));
        path = "/images/grupo_default.jpg";
        contactosSeleccionados.clear();
        lblSelectedCount.setText("Contactos seleccionados: 0");
    }

    @Override
    public void alOcultar() {
        // ...acciones al ocultar...
    }

    @Override
    public TipoVentana getTipo() {
        return TipoVentana.NUEVO_GRUPO; // Asegúrese de definir NUEVO_GRUPO en TipoVentana
    }
}
