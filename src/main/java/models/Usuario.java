package models;

import java.util.ArrayList;

public class Usuario {
	private String nombre;
	private String apellidos;
	private String generoFavorito;
	private String contrasena;
	private String correo;
	private String nickname;
	private int id;
	
	 private static ArrayList<Libro> librosRegistrados = new ArrayList<>();
	 
	 

	public Usuario(String nombre, String apellidos, String generoFavorito, String contrasena, String correo,
			String nickname) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.generoFavorito = generoFavorito;
		this.contrasena = contrasena;
		this.correo = correo;
		this.nickname = nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getGeneroFavorito() {
		return generoFavorito;
	}

	public void setGeneroFavorito(String generoFavorito) {
		this.generoFavorito = generoFavorito;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static ArrayList<Libro> getLibrosRegistrados() {
		return librosRegistrados;
	}

	public static void setLibrosRegistrados(ArrayList<Libro> librosRegistrados) {
		Usuario.librosRegistrados = librosRegistrados;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellidos=" + apellidos + ", generoFavorito=" + generoFavorito
				+ ", contrasena=" + contrasena + ", correo=" + correo + ", nickname=" + nickname + ", id=" + id + "]";
	}
	

}
