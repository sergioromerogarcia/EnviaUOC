/**
 * Clase usuario para poder convertir cada fila de la tabla en un objeto y poder acceder fácilmente a los valores del registro
 */
package com.example.enviauoc;

public class usuario {
	private int usuario_ID;
	private String usuario_nombre;
	private String usuario_password;
	private String usuario_email;
	
	public usuario(int usuario_ID, String usuario_nombre, String usuario_password, String usuario_email){
		this.usuario_ID = usuario_ID;
		this.usuario_nombre = usuario_nombre;
		this.usuario_password = usuario_password;
		this.usuario_email = usuario_email;
	}

	public int getUsuario_ID() {
		return usuario_ID;
	}

	public void setUsuario_ID(int usuario_ID) {
		this.usuario_ID = usuario_ID;
	}

	public String getUsuario_nombre() {
		return usuario_nombre;
	}

	public void setUsuario_nombre(String usuario_nombre) {
		this.usuario_nombre = usuario_nombre;
	}

	public String getUsuario_password() {
		return usuario_password;
	}

	public void setUsuario_password(String usuario_password) {
		this.usuario_password = usuario_password;
	}

	public String getUsuario_email() {
		return usuario_email;
	}

	public void setUsuario_email(String usuario_email) {
		this.usuario_email = usuario_email;
	}
	
}
