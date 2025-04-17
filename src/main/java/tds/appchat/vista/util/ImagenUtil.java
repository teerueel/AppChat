package tds.appchat.vista.util;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utilidad para cargar y manipular imágenes en la aplicación.
 */
public class ImagenUtil {
    /**
     * Carga una imagen desde los recursos de la aplicación.
     * @param ruta Ruta relativa de la imagen dentro del directorio de recursos
     * @return La imagen cargada o null si no se encuentra
     */
    public static Image cargarImagen(String ruta) {
        Image img = null;
        try {
            InputStream is = ImagenUtil.class.getResourceAsStream(ruta);
            if (is != null) {
                img = ImageIO.read(is);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen desde recursos: " + e.getMessage());
        }
        if (img == null) {
            img = cargarImagenDesdeArchivo(ruta);
            if(img == null){
                System.err.println("Error: no se pudo cargar la imagen ni desde recursos ni desde archivo: " + ruta);
            }
        }
        return img;
    }

    public static Image cargarImagenDesdeArchivo(String ruta) {
        try {
            return ImageIO.read(new java.io.File(ruta));
        } catch (IOException e) {
            System.err.println("Error al cargar imagen desde archivo: " + e.getMessage());
            return null;
        }
    }
}
