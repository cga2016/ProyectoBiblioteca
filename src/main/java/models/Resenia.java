package models;

import java.sql.Date;

public class Resenia {
    private int id;
    private String comentario;
    private double nota;
    private int idUsuario;
    private String isbnLibro;
    private Date fechaLeido;

    public Resenia() {
    }

    public Resenia(int id, String comentario, double nota, int idUsuario, String isbnLibro, Date fechaLeido) {
        this.id = id;
        this.comentario = comentario;
        this.nota = nota;
        this.idUsuario = idUsuario;
        this.isbnLibro = isbnLibro;
        this.fechaLeido = fechaLeido;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIsbnLibro() {
        return isbnLibro;
    }

    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    public Date getFechaLeido() {
        return fechaLeido;
    }

    public void setFechaLeido(Date fechaLeido) {
        this.fechaLeido = fechaLeido;
    }
}
