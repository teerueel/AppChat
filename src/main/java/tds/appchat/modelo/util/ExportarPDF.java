package tds.appchat.modelo.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import tds.appchat.modelo.Mensaje;
import tds.appchat.modelo.contactos.Contacto;

public enum ExportarPDF {
    INSTANCIA;

    public void crearPDF(String ruta, String nombreUsuario, Contacto contacto)
            throws FileNotFoundException, DocumentException {
    	List<Mensaje> mensajes = contacto.getMensajes();
    	String nombreContacto = contacto.getNombre();
    	
        Document documento = new Document();
        PdfWriter.getInstance(documento, new FileOutputStream(ruta));
        documento.open();

        documento.add(new Paragraph("Conversación entre " + nombreUsuario + " y " + nombreContacto));

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Mensaje mensaje : mensajes) {
            String autor;
            if (mensaje.getTipo().equals(TipoMensaje.ENVIADO)) {
            	autor = nombreUsuario;
            }
            else {
            	autor = nombreContacto;
            }
            Paragraph encabezado = new Paragraph("Mensaje enviado por " + autor);
            encabezado.setSpacingBefore(12f);
            documento.add(encabezado);

            Paragraph fecha = new Paragraph(mensaje.getFecha().format(formateador));
            fecha.setIndentationLeft(12f);
            documento.add(fecha);

            Paragraph cuerpo;
            if (!mensaje.isEmoji()) {
                cuerpo = new Paragraph(mensaje.getTexto());
            } else {
                cuerpo = new Paragraph("Emoticono");
            }
            cuerpo.setIndentationLeft(22f);
            cuerpo.setSpacingAfter(7f);
            documento.add(cuerpo);
        }

        documento.close();
    }
}

