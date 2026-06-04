package modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int es_primera;
	
	public Usuario(int id, int es_primera, String user, String correo, String contraseña, String nombre, String telefono) {
		this.id = id;
		this.es_primera = es_primera;
		this.user = user;
		this.correo = correo;
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.telefono = telefono;
	}
	
	private String user, correo, contraseña, nombre, telefono;
	public Usuario(int es_primera, String user, String correo, String contraseña, String nombre,
			String telefono) {
		super();
		this.es_primera = es_primera;
		this.user = user;
		this.correo = correo;
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.telefono = telefono;
	}
	public int getEs_primera() {
		return es_primera;
	}
	public void setEs_primera(int es_primera) {
		this.es_primera = es_primera;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
