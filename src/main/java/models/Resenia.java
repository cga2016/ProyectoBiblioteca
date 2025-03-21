package models;

import java.sql.Date;

public class Resenia {
	
	private String comentario;
	private double nota;
	private int idUsuario;
	private int idLibro;
	private Date fechaLeido;
	
	public Resenia(String comentario, double nota, int idUsuario, int idLibro, Date fechaLeido) {
		super();
		this.comentario = comentario;
		this.nota = nota;
		this.idUsuario = idUsuario;
		this.idLibro = idLibro;
		this.fechaLeido = fechaLeido;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdLibro() {
		return idLibro;
	}
	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}
	public Date getFechaLeido() {
		return fechaLeido;
	}
	public void setFechaLeido(Date fechaLeido) {
		this.fechaLeido = fechaLeido;
	}
	
	

}
