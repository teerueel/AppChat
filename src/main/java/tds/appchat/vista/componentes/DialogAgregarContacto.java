package tds.appchat.vista.componentes;

import javax.swing.*;
import tds.appchat.controlador.Controlador;
import tds.appchat.modelo.contactos.Contacto;
import java.awt.*;
import java.awt.event.*;

public class DialogAgregarContacto extends JDialog {

    private JTextField txtNombre;
    private String contactoNombre;
    private boolean confirmed = false;
    private Contacto contacto; // contacto correspondiente

    public DialogAgregarContacto(Frame parent, Contacto contacto) {
        super(parent, "Agregar Contacto", true);
        this.contacto = contacto;
        initializeComponents();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        setLayout(new BorderLayout(10, 10));
        JPanel panelInput = new JPanel(new FlowLayout());
        panelInput.add(new JLabel("Nombre del contacto:"));
        txtNombre = new JTextField(20);
        panelInput.add(txtNombre);
        add(panelInput, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnOk = new JButton("Agregar");
        JButton btnCancel = new JButton("Cancelar");
        
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactoNombre = txtNombre.getText().trim();
                if(!contactoNombre.isEmpty()){
                    confirmed = true;
                    Controlador.INSTANCIA.agregarContacto(contactoNombre, contacto);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(DialogAgregarContacto.this, "El nombre no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        panelButtons.add(btnCancel);
        panelButtons.add(btnOk);
        add(panelButtons, BorderLayout.SOUTH);
    }

    public String getContactoNombre() {
        return contactoNombre;
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
}