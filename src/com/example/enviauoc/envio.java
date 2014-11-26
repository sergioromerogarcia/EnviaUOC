/**
 * Clase Envio para poder convertir cada fila de la tabla en un objeto y poder acceder fácilmente a los valores del registro
 */
package com.example.enviauoc;

public class envio {
	private int envio_id;
	private String envio_track;
	private String envio_direccion;
	private String envio_idUsuario;
	
	public envio(int envio_id, String envio_track, String envio_direccion, String envio_idUsuario) {
		this.envio_id = envio_id;
		this.envio_track = envio_track;
		this.envio_direccion = envio_direccion;
		this.envio_idUsuario = envio_idUsuario;
	}

	public int getEnvio_id() {
		return envio_id;
	}

	public void setEnvio_id(int envio_id) {
		this.envio_id = envio_id;
	}

	public String getEnvio_track() {
		return envio_track;
	}

	public void setEnvio_track(String envio_track) {
		this.envio_track = envio_track;
	}

	public String getEnvio_direccion() {
		return envio_direccion;
	}

	public void setEnvio_direccion(String envio_direccion) {
		this.envio_direccion = envio_direccion;
	}

	public String getEnvio_idUsuario() {
		return envio_idUsuario;
	}

	public void setEnvio_idUsuario(String envio_idUsuario) {
		this.envio_idUsuario = envio_idUsuario;
	}
	
	
}
