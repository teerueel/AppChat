package tds.appchat.vista.util;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public enum SelectorImagen {
    INSTANCIA;

    public String seleccionarImagenPerfil(){
        final String[] selectedPath = {""};
        // Crear un diálogo modal personalizado
        JDialog dialog = new JDialog((Frame)null, "Selecciona una imagen", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());
        
        // Instrucciones en el centro
        JLabel instructions = new JLabel("Arrastra tu imagen aquí o selecciona de tu ordenador", SwingConstants.CENTER);
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dialog.add(instructions, BorderLayout.CENTER);
        
        // Panel inferior con botón para seleccionar manualmente
        JPanel bottomPanel = new JPanel();
        JButton selectButton = new JButton("Seleccionar de mi ordenador");
        bottomPanel.add(selectButton);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        
        // Agregar soporte de Drag & Drop al diálogo
        new DropTarget(dialog, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    if (dtde.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.javaFileListFlavor)) {
                            dtde.acceptDrop(DnDConstants.ACTION_COPY);
                            Object transferData = dtde.getTransferable().getTransferData(java.awt.datatransfer.DataFlavor.javaFileListFlavor);
                            if (transferData instanceof java.util.List<?>) {
                                java.util.List<?> files = (java.util.List<?>) transferData;
                                java.util.List<File> droppedFiles = new java.util.ArrayList<>();
                                for (Object file : files) {
                                    if (file instanceof File) {
                                        droppedFiles.add((File) file);
                                    }
                                }
                                if(!droppedFiles.isEmpty()){
                                    selectedPath[0] = droppedFiles.get(0).getAbsolutePath();
                                }
                                dtde.dropComplete(true);
                                dialog.dispose();
                            } else {
                                dtde.dropComplete(false);
                            }
                        } else {
                            dtde.dropComplete(false);
                        }
                } catch (Exception ex) {
                    System.err.println("Error al procesar el archivo arrastrado: " + ex.getMessage());
                    dtde.dropComplete(false);
                }
            }
        });
        
        // Acción del botón: abrir JFileChooser para seleccionar imagen
        selectButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecciona una imagen");
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes (jpg, jpeg, png, gif)", "jpg", "jpeg", "png", "gif");
            chooser.addChoosableFileFilter(filter);
            chooser.setDragEnabled(true);
            int result = chooser.showOpenDialog(dialog);
            if(result == JFileChooser.APPROVE_OPTION){
                File file = chooser.getSelectedFile();
                selectedPath[0] = file.getAbsolutePath();
            }
            dialog.dispose();
        });
        
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        return selectedPath[0];
    }

}
