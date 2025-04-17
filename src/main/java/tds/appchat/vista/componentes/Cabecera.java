package tds.appchat.vista.componentes;

import tds.appchat.vista.core.GestorVentanas;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.util.EstilosApp;
import tds.appchat.vista.util.ImagenUtil;
import tds.appchat.controlador.Controlador;
import tds.appchat.sesion.Sesion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Componente de cabecera reutilizable para todas las pantallas de la aplicación.
 */
public class Cabecera extends JPanel implements Recargable {
    
    /**
     * Constructor de la cabecera.
     */
    public Cabecera() {
        initialize();
    }

    
    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(EstilosApp.COLOR_TARJETA);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, EstilosApp.COLOR_BORDE),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        // Logo pequeño para la barra
        JLabel logo = new JLabel("APPCHAT");
        logo.setFont(EstilosApp.FUENTE_NAVBAR);
        logo.setForeground(EstilosApp.COLOR_PRIMARIO);
        
        Image imgLogo = ImagenUtil.cargarImagen("/images/appchat_logo.png");
        if (imgLogo != null) {
            logo.setIcon(new ImageIcon(imgLogo.getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
            logo.setIconTextGap(10);
        }
        
        // Hacer el logo clickeable para volver a la página principal
        logo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.PRINCIPAL);
            }
        });
        
        // Panel de navegación
        JPanel navLinks = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navLinks.setOpaque(false);
        
        String[] enlaces = {"Inicio", "Principal"};
        for (String enlace : enlaces) {
            JLabel linkLabel = new JLabel(enlace);
            linkLabel.setFont(EstilosApp.FUENTE_NAVBAR);
            linkLabel.setForeground(EstilosApp.COLOR_TEXTO_SECUNDARIO);
            linkLabel.setBorder(new EmptyBorder(5, 15, 5, 15));
            linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Efecto hover
            linkLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Navegación según el enlace
                    switch (enlace) {
                        case "Inicio":
                            GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.PRINCIPAL);
                            break;
                        case "Principal":
                            if(Controlador.INSTANCIA.haySesion()){
                                GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.APP);
                            } else {
                                JOptionPane.showMessageDialog(Cabecera.this, "Inicia sesión para acceder a esta sección.");
                            }
                            
                            break;
                       
                        // Añadir más casos según sea necesario
                    }
                }
            });
            
            navLinks.add(linkLabel);
        }
        
        // Verificar si hay sesión activa
        if (Sesion.INSTANCIA.haySesion()) {
            // Panel para contener el avatar y el nombre
            JPanel perfilPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
            perfilPanel.setOpaque(false);
            perfilPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            perfilPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Crear un icono de perfil con bordes redondeados
            JLabel iconoPerfil = new JLabel();
            ImageIcon avatarIcon = null;
            String path = Sesion.INSTANCIA.getUsuarioActual().getImagen();
            if(path.equals("/images/avatar_default.png")){
                 avatarIcon = new ImageIcon(ImagenUtil.cargarImagen(path)
                        .getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            } else {
                 avatarIcon = new ImageIcon(ImagenUtil.cargarImagenDesdeArchivo(path)
                        .getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            }
            
            iconoPerfil.setIcon(avatarIcon);
            
            // Borde mejorado para el avatar
            iconoPerfil.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstilosApp.COLOR_PRIMARIO, 2),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)
            ));
            
            // Etiqueta con el nombre del usuario actual
            JLabel nombreUsuarioLabel = new JLabel(Sesion.INSTANCIA.getUsuarioActual().getNombre());
            nombreUsuarioLabel.setFont(new Font(EstilosApp.FUENTE_NAVBAR.getFamily(), Font.BOLD, 12));
            nombreUsuarioLabel.setForeground(EstilosApp.COLOR_TEXTO);
            
            // Icono desplegable
            JLabel iconoDesplegable = new JLabel("▼");
            iconoDesplegable.setFont(new Font(iconoDesplegable.getFont().getFamily(), Font.PLAIN, 8));
            iconoDesplegable.setForeground(EstilosApp.COLOR_TEXTO_SECUNDARIO);
            
            // Añadir componentes al panel de perfil
            perfilPanel.add(iconoPerfil);
            perfilPanel.add(nombreUsuarioLabel);
            perfilPanel.add(iconoDesplegable);
            
            // Menú desplegable para el usuario con estilo mejorado
            JPopupMenu userMenu = new JPopupMenu();
            userMenu.setBorder(BorderFactory.createLineBorder(EstilosApp.COLOR_BORDE, 1));
            
            // Añadir encabezado al menú con nombre del usuario
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(EstilosApp.COLOR_TARJETA);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            
            // Panel para foto de perfil en el menú
            JLabel menuAvatar = new JLabel();
            String path2 = Sesion.INSTANCIA.getUsuarioActual().getImagen();
            if(path.equals("/images/avatar_default.png")){
                menuAvatar.setIcon(new ImageIcon(ImagenUtil.cargarImagen(path2)
                        .getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            } else {
                menuAvatar.setIcon(new ImageIcon(ImagenUtil.cargarImagenDesdeArchivo(path2)
                        .getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            }
           
            menuAvatar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstilosApp.COLOR_PRIMARIO, 2),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)
            ));
            
            // Panel para información de usuario en el menú
            JPanel infoUsuario = new JPanel();
            infoUsuario.setLayout(new BoxLayout(infoUsuario, BoxLayout.Y_AXIS));
            infoUsuario.setOpaque(false);
            infoUsuario.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            
            JLabel nombreUsuario = new JLabel(Sesion.INSTANCIA.getUsuarioActual().getNombre());
            nombreUsuario.setFont(new Font(EstilosApp.FUENTE_NAVBAR.getFamily(), Font.BOLD, 14));
            nombreUsuario.setForeground(EstilosApp.COLOR_TEXTO);
            nombreUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
            
        
            
            infoUsuario.add(nombreUsuario);
            infoUsuario.add(Box.createRigidArea(new Dimension(0, 5)));
        
            
            headerPanel.add(menuAvatar, BorderLayout.WEST);
            headerPanel.add(infoUsuario, BorderLayout.CENTER);
            
            // Agregar panel personalizado al menú
            userMenu.add(headerPanel);
            userMenu.addSeparator();
            
            // Opciones del menú con mejor estilo
            JMenuItem miPerfil = new JMenuItem("Mi Perfil");
            miPerfil.setFont(new Font(EstilosApp.FUENTE_NAVBAR.getFamily(), Font.PLAIN, 12));
            miPerfil.setIcon(new ImageIcon(ImagenUtil.cargarImagen("/images/perfil_icon.png")
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
            miPerfil.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            miPerfil.addActionListener(e -> GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.PERFIL));
            
            JMenuItem cerrarSesion = new JMenuItem("Cerrar Sesión");
            cerrarSesion.setFont(new Font(EstilosApp.FUENTE_NAVBAR.getFamily(), Font.PLAIN, 12));
            cerrarSesion.setIcon(new ImageIcon(ImagenUtil.cargarImagen("/images/logout.png")
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
            cerrarSesion.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            cerrarSesion.addActionListener(e -> {
                // Cerrar sesión y recargar la cabecera
                Sesion.INSTANCIA.cerrarSesion();
                recargar();
                GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.LOGIN);
            });
            
            userMenu.add(miPerfil);
            userMenu.addSeparator();
            userMenu.add(cerrarSesion);
            
            // Mostrar menú al hacer clic en el panel de perfil
            MouseAdapter perfilListener = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    userMenu.show(perfilPanel, 0, perfilPanel.getHeight());
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    // Efecto hover en todo el panel
                    iconoPerfil.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(EstilosApp.COLOR_SECUNDARIO, 2),
                        BorderFactory.createEmptyBorder(2, 2, 2, 2)
                    ));
                    nombreUsuarioLabel.setForeground(EstilosApp.COLOR_SECUNDARIO);
                    iconoDesplegable.setForeground(EstilosApp.COLOR_SECUNDARIO);
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    // Volver al estilo normal
                    iconoPerfil.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(EstilosApp.COLOR_PRIMARIO, 2),
                        BorderFactory.createEmptyBorder(2, 2, 2, 2)
                    ));
                    nombreUsuarioLabel.setForeground(EstilosApp.COLOR_TEXTO);
                    iconoDesplegable.setForeground(EstilosApp.COLOR_TEXTO_SECUNDARIO);
                }
            };
            
            // Aplicar el listener a todos los componentes del panel
            perfilPanel.addMouseListener(perfilListener);
            iconoPerfil.addMouseListener(perfilListener);
            nombreUsuarioLabel.addMouseListener(perfilListener);
            iconoDesplegable.addMouseListener(perfilListener);
            
            navLinks.add(perfilPanel);
        } else {
            // Botón de inicio de sesión con icono
            JButton btnLogin = new JButton("Iniciar Sesión");
            btnLogin.setFont(EstilosApp.FUENTE_NAVBAR);
            btnLogin.setForeground(Color.WHITE);
            btnLogin.setBackground(EstilosApp.COLOR_PRIMARIO);
            btnLogin.setBorder(new EmptyBorder(8, 20, 8, 20));
            btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnLogin.setFocusPainted(false);
            
            // Añadir icono al botón
            ImageIcon loginIcon = new ImageIcon(ImagenUtil.cargarImagen("/images/login_icon.png")
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH));
            btnLogin.setIcon(loginIcon);
            btnLogin.setIconTextGap(10);
            
            // Efectos hover para el botón
            btnLogin.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btnLogin.setBackground(EstilosApp.COLOR_PRIMARIO.darker());
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    btnLogin.setBackground(EstilosApp.COLOR_PRIMARIO);
                }
            });
            
            btnLogin.addActionListener(e -> GestorVentanas.INSTANCIA.mostrarVentana(TipoVentana.LOGIN));
            
            navLinks.add(btnLogin);
        }
        
        add(logo, BorderLayout.WEST);
        add(navLinks, BorderLayout.EAST);
    }

    @Override
    public void recargar() {
        // Reconstruir la cabecera según el estado de la sesión:
        removeAll();
        initialize(); 
        revalidate();
        repaint();
    }
}
