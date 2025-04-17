package tds.appchat.modelo;


import jakarta.persistence.Embeddable;

@Embeddable
public class EstadisticasUsuario {
    
    private int numCursosCompletados;
   
    private int numCursosEnProgreso;
  
    private long tiempoUso;
   
    private int rachaActual;
   
    private int mejorRacha;


    public EstadisticasUsuario(int numCursosCompletados, int numCursosEnProgreso, long tiempoUso, int mejorRacha) {
        this.numCursosCompletados = numCursosCompletados;
        this.numCursosEnProgreso = numCursosEnProgreso;
        this.tiempoUso = tiempoUso;
        this.mejorRacha = mejorRacha;
    }

    public EstadisticasUsuario() {
        this(0, 0, 0, 0);
    }

    public int getNumCursosCompletados() {
        return this.numCursosCompletados;
    }

    public void setNumCursosCompletados(int numCursosCompletados) {
        this.numCursosCompletados = numCursosCompletados;
    }

    public int getNumCursosEnProgreso() {
        return this.numCursosEnProgreso;
    }

    public void setNumCursosEnProgreso(int numCursosEnProgreso) {
        this.numCursosEnProgreso = numCursosEnProgreso;
    }

    public long getTiempoUso() {
        return this.tiempoUso;
    }

    public void setTiempoUso(long tiempoUso) {
        this.tiempoUso = tiempoUso;
    }

    public int getMejorRacha() {
        return this.mejorRacha;
    }

    public void setMejorRacha(int mejorRacha) {
        this.mejorRacha = mejorRacha;
    }

    public void aumentarCursosCompletados() {
        this.numCursosCompletados++;
        this.numCursosEnProgreso--;
    }

    public void aumentarCursosEnProgreso() {
        this.numCursosEnProgreso++;
    }

    public void aumentarTiempoUso(long tiempo) {
        this.tiempoUso += tiempo;
    }

    public void actualizarRacha(boolean acierto) {
        if(acierto) {
            this.rachaActual++;
        } else {
            this.rachaActual = 0;
        }
        if (this.rachaActual > this.mejorRacha) {
            this.mejorRacha = this.rachaActual;
        }
    }
}
