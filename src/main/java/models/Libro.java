package models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	private String fechaPrestamo;
	private boolean estadoPrestamo;
	private String descripcion;
	private ArrayList<String> generos = new ArrayList<>();
	private ArrayList<String> Comentarios = new ArrayList<>();
	private ArrayList<String> notas = new ArrayList<>();
	
	private Biblioteca ticket = new Biblioteca();
	
	public Libro(ResultSet rs) throws SQLException {
	    this.iSBN = rs.getString("iSBN");
	    this.nombre = rs.getString("nombre");
	    this.linkPropio = rs.getString("linkPropio");
	    this.autor = rs.getString("autor");
	    this.fechaPublicacion = rs.getString("fechaPublicacion");
	    this.imagenPeque = rs.getString("imagenPeque");
	    this.imageGrande = rs.getString("imagenGrande");
	    this.prestado = rs.getBoolean("prestado");
	    this.idUsuarioPrestamo = rs.getInt("idUsuarioPrestamo");
	    this.fechaPrestamo = rs.getString("fechaPrestamo");
	    this.estadoPrestamo = rs.getBoolean("estadoPrestamo");
	}
	
	
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
	
	public String getDescripcion() {
	    return descripcion;
	}

	public void setDescripcion(String descripcion) {
	    this.descripcion = descripcion;
	}

	public ArrayList<String> getGeneros() {
	    return generos;
	}

	public void setGeneros(ArrayList<String> generos) {
	    this.generos = generos;
	}

	@Override
	public String toString() {
	    return "Libro [id=" + id + ", nombre=" + nombre + ", autor=" + autor +
	           ", fechaPublicacion=" + fechaPublicacion + ", descripcion=" + descripcion +
	           ", generos=" + generos + ", iSBN=" + iSBN + "]";
	}
	public Biblioteca getTicket() {
		return ticket;
	}
	public void setTicket(Biblioteca ticket) {
		this.ticket = ticket;
	}
	public boolean isPrestado() {
		return prestado;
	}
	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}
	public int getIdUsuarioPrestamo() {
		return idUsuarioPrestamo;
	}
	public void setIdUsuarioPrestamo(int idUsuarioPrestamo) {
		this.idUsuarioPrestamo = idUsuarioPrestamo;
	}
	public String getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(String fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public boolean isEstadoPrestamo() {
		return estadoPrestamo;
	}
	public void setEstadoPrestamo(boolean estadoPrestamo) {
		this.estadoPrestamo = estadoPrestamo;
	}
	public ArrayList<String> getComentarios() {
		return Comentarios;
	}
	public void setComentarios(ArrayList<String> comentarios) {
		Comentarios = comentarios;
	}
	public ArrayList<String> getNotas() {
		return notas;
	}
	public void setNotas(ArrayList<String> notas) {
		this.notas = notas;
	}

	
}
