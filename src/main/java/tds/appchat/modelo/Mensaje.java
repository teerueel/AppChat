package tds.appchat.modelo;

import java.time.LocalDateTime;
import tds.appchat.modelo.util.TipoMensaje;

public class Mensaje {
    private String texto;
    private LocalDateTime fecha;
    private TipoMensaje tipo;
    //emoticono aún no se como hacerlo

    public Mensaje(String texto, LocalDateTime fecha, TipoMensaje tipo) {
        this.texto = texto;
        this.fecha = fecha;
        this.tipo = tipo;
    }
    
    public Mensaje(String texto, TipoMensaje tipo) {
        this.texto = texto;
        this.fecha = LocalDateTime.now();
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public TipoMensaje getTipo() {
        return tipo;
    }
    public void setTipo(TipoMensaje tipo) {
        this.tipo = tipo;
    }
}
