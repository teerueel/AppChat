package tds.appchat.sesion;

import tds.appchat.modelo.Usuario;


public enum Sesion {
	INSTANCIA;
	private Usuario usuarioActual;
	
	private long tiempoInicioSesion;
	
	public Usuario getUsuarioActual() {
		return this.usuarioActual;
	}


	
	public void setUsuarioActual(Usuario user) {
		this.usuarioActual = user;
		//Guardamos el momento en el que se inicia sesión
		this.tiempoInicioSesion = System.currentTimeMillis();
	}

	public void setTiempoInicioSesion(long tiempo) {
		this.tiempoInicioSesion = tiempo;
	}

	public long getTiempoInicioSesion() {
		return this.tiempoInicioSesion;
	}
	
	public void cerrarSesion() {
		if(this.usuarioActual != null) {
            this.usuarioActual.getStats().aumentarTiempoUso(System.currentTimeMillis() - tiempoInicioSesion);
        }
		this.usuarioActual = null;
		
	}
	
	public boolean haySesion() {
		return this.usuarioActual != null;
	}
	
	


}
