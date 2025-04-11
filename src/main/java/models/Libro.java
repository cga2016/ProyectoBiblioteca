package models;

import java.sql.Date;

public class Libro {
	private int id;
	private String nombre;
	private String linkPropio;
	private String autor;
	private String fechaPublicacion;
	private String iSBN;
	private String imagenPeque;
	private String imageGrande;
	private boolean prestado;
	private int idUsuarioPrestamo;
	private Date fechaPrestamo;
	private boolean estadoPrestamo;
	
	
	
	public Libro(int id, String nombre, String linkPropio, String autor, String fechaPublicacion, String iSBN) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.linkPropio = linkPropio;
		this.autor = autor;
		this.fechaPublicacion = fechaPublicacion;
		this.iSBN = iSBN;
	}
	public int  getId() {
		return id;
	}
	public void setId(int  id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLinkPropio() {
		return linkPropio;
	}
	public void setLinkPropio(String linkPropio) {
		this.linkPropio = linkPropio;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getiSBN() {
		return iSBN;
	}
	public void setiSBN(String iSBN) {
		this.iSBN = iSBN;
	}
	public String getImagenPeque() {
		return imagenPeque;
	}
	public void setImagenPeque(String imagenPeque) {
		this.imagenPeque = imagenPeque;
	}
	public String getImageGrande() {
		return imageGrande;
	}
	public void setImageGrande(String imageGrande) {
		this.imageGrande = imageGrande;
	}
	@Override
	public String toString() {
		return "Libro [id=" + id + ", nombre=" + nombre + ", linkPropio=" + linkPropio + ", autor=" + autor
				+ ", fechaPublicacion=" + fechaPublicacion + ", iSBN=" + iSBN + ", imagenPeque=" + imagenPeque
				+ ", imageGrande=" + imageGrande + ", prestado=" + prestado + ", idUsuarioPrestamo=" + idUsuarioPrestamo
				+ ", fechaPrestamo=" + fechaPrestamo + ", estadoPrestamo=" + estadoPrestamo + "]";
	}
	
	
}
