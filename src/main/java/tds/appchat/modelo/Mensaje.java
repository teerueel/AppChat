package tds.appchat.modelo;

import java.time.LocalDate;

import tds.appchat.modelo.util.TipoMensaje;

public class Mensaje {
    private String texto;
    private LocalDate fecha;
    private TipoMensaje tipo;
    //emoticono aún no se como hacerlo

    public Mensaje(String texto, LocalDate fecha, TipoMensaje tipo) {
        this.texto = texto;
        this.fecha = fecha;
        this.tipo = tipo;
    }
    
    public Mensaje(String texto, TipoMensaje tipo) {
        this.texto = texto;
        this.fecha = LocalDate.now();
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public TipoMensaje getTipo() {
        return tipo;
    }
    public void setTipo(TipoMensaje tipo) {
        this.tipo = tipo;
    }
    



}
