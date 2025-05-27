package tds.appchat.modelo;

import java.time.LocalDateTime;
import tds.appchat.modelo.util.TipoMensaje;

public class Mensaje {
    private String texto;
    private int id;
    private LocalDateTime fecha;
    private TipoMensaje tipo;
    private int emoji;
    //emoticono aún no se como hacerlo

    public Mensaje(String texto, LocalDateTime fecha, TipoMensaje tipo) {
        this.texto = texto;
        this.fecha = fecha;
        this.tipo = tipo;
        this.emoji = -1;

    }
    
    public Mensaje(String texto, TipoMensaje tipo) {
        this.texto = texto;
        this.fecha = LocalDateTime.now();
        this.tipo = tipo;
        this.emoji = -1;
    }

    public Mensaje(LocalDateTime fecha, TipoMensaje tipo, int emoji) {
        this.texto = "Emoticono";
        this.fecha = fecha;
        this.tipo = tipo;
        this.emoji = emoji;
    }

    public Mensaje(int emoji, TipoMensaje tipo) {
        this.texto = "Emoticono";
        this.fecha = LocalDateTime.now();
        this.tipo = tipo;
        this.emoji = emoji;
    }
    
    public Mensaje(String texto, LocalDateTime fecha, TipoMensaje tipo, int emoji) {
        this.texto = texto;
        this.fecha = fecha;
        this.tipo = tipo;
        this.emoji = emoji;
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

    public boolean isEmoji() {
        return this.emoji != -1;
    }

    public int getEmoji() {
        return emoji;
    }

    public void setEmoji(int emoji) {
        this.emoji = emoji;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
