package tds.appchat.vista.pantallas;

import javax.swing.*;
import java.awt.*;
import tds.appchat.vista.core.Ventana;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.modelo.contactos.Contacto;
import tds.appchat.sesion.Sesion;
import tds.appchat.vista.componentes.TarjetaAddContacto;
import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.core.TipoVentana;

public class VentanaContactos extends JFrame implements Ventana, Recargable {

    private JPanel panelPrincipal;
    private JPanel panelIzquierdo;
    private JPanel panelDerecho;

    public VentanaContactos() {
        inicializarComponentes();
    }

    public void inicializarComponentes() {
        setTitle("Contactos - Ventana de Contactos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Panel principal de la ventana
        panelPrincipal = new JPanel(new BorderLayout());

        // Panel izquierdo: Lista de Contactos y Grupos con botón inferior "Añadir Contacto"
        panelIzquierdo = new JPanel(new BorderLayout());
        // Crear el contenedor de la lista, con BoxLayout vertical
        JPanel listaContactos = new JPanel();
        listaContactos.setLayout(new BoxLayout(listaContactos, BoxLayout.Y_AXIS));
        listaContactos.setBackground(Color.WHITE);
        listaContactos.setBorder(BorderFactory.createTitledBorder("Contactos"));    
        // Obtener la lista de contactos del usuario actual y añadirlos a la lista
        if(Sesion.INSTANCIA.haySesion()){
            java.util.List<Contacto> contactos = Sesion.INSTANCIA.getUsuarioActual().getContactos();
            listaContactos.removeAll();
            for(Contacto contacto : contactos) {
                // Utilizar TarjetaAddContacto en lugar de JLabel
                TarjetaAddContacto tarjeta = new TarjetaAddContacto(contacto);
                listaContactos.add(tarjeta);
            }
        }
        // Envolver la lista en un JScrollPane
        JScrollPane scrollLista = new JScrollPane(listaContactos);
        scrollLista.setPreferredSize(new Dimension(350, 500));
        panelIzquierdo.add(scrollLista, BorderLayout.CENTER);
        
        // Botón "Añadir Contacto" debajo de la lista
        JButton btnAddContacto = new JButton("Añadir Contacto");
        btnAddContacto.setFont(EstilosApp.FUENTE_BOTON);
        btnAddContacto.setForeground(Color.WHITE);
        btnAddContacto.setBackground(EstilosApp.COLOR_PRIMARIO);
        // Al hacer clic, se mostrará la VentanaNuevoContacto
        btnAddContacto.addActionListener(e -> {
            // Se asume que TipoVentana.NUEVO_CONTACTO está definido y GestorVentanas gestiona la navegación
            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.NUEVO_CONTACTO);
        });
        panelIzquierdo.add(btnAddContacto, BorderLayout.SOUTH);

        // Panel derecho: Área para mostrar contactos del grupo seleccionado y botón "Añadir Grupo"
        panelDerecho = new JPanel(new BorderLayout());
        // Crear un panel areaGrupo con un mensaje por defecto y un tamaño preferido para asegurar visibilidad
        JPanel areaGrupo = new JPanel(new BorderLayout());
        areaGrupo.setBackground(Color.WHITE);
        areaGrupo.setPreferredSize(new Dimension(350, 500));
        JLabel lblDefault = new JLabel("Seleccione un contacto/grupo", SwingConstants.CENTER);
        lblDefault.setForeground(new Color(0, 100, 0));
        areaGrupo.add(lblDefault, BorderLayout.CENTER);
        panelDerecho.add(areaGrupo, BorderLayout.CENTER);
        // Botón "Añadir Grupo" en la parte inferior con estética verde
        JButton btnAddGrupo = new JButton("Añadir Grupo");
        btnAddGrupo.setFont(EstilosApp.FUENTE_BOTON);
        btnAddGrupo.setForeground(Color.WHITE);
        btnAddGrupo.setBackground(EstilosApp.COLOR_PRIMARIO);
        //FUncionalidd del botón
        btnAddGrupo.addActionListener(e -> {
            // Se asume que TipoVentana.NUEVO_GRUPO está definido y GestorVentanas gestiona la navegación
            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.NUEVO_GRUPO);
        });
        panelDerecho.add(btnAddGrupo, BorderLayout.SOUTH);
        
        // Utilizar un JSplitPane para dividir los dos paneles de manera equitativa
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(350);
        
        panelPrincipal.add(splitPane, BorderLayout.CENTER);
        
        // Agregar panelPrincipal al content pane
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
        return TipoVentana.CONTACTOS; // suponiendo que CONTACTOS es un tipo definido en TipoVentana
    }
}
