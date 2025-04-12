package tds.appchat.vista.core;

/**
 * Interfaz para componentes que necesitan recargarse cuando cambia el estado
 * de la aplicación, como cambios en la sesión del usuario.
 */
public interface Recargable {
    
    /**
     * Recarga el componente para reflejar el estado actual de la aplicación.
     * Útil para actualizar elementos de la UI cuando cambia la sesión del usuario
     * u otros estados relevantes.
     */
    void recargar();
}
