package tds.appchat.vista.pantallas;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tds.appchat.vista.core.Recargable;
import tds.appchat.vista.core.TipoVentana;
import tds.appchat.vista.core.Ventana;

public class VentanaPremium extends JFrame implements Ventana, Recargable{
	private JPanel panelPrincipal;

    public VentanaPremium() {
        // Inicializa el panel principal (puedes personalizarlo luego)
        panelPrincipal = new JPanel();
    }

    @Override
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    @Override
    public void alMostrar() {
        // Aquí puedes poner lógica para cuando se muestre la ventana
        System.out.println("Ventana Premium mostrada.");
    }

    @Override
    public void alOcultar() {
        // Aquí puedes poner lógica para cuando se oculte la ventana
        System.out.println("Ventana Premium oculta.");
    }

    @Override
    public TipoVentana getTipo() {
        return TipoVentana.PREMIUM;
    }
    
    @Override
    public void recargar() {
        panelPrincipal.removeAll();
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
}
