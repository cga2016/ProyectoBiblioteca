package models;

public class PrestamoVista {
    private final String titulo;
    private final String correoPrestador;
    private final String fechaPrestamo;

    public PrestamoVista(String titulo, String correoPrestador, String fechaPrestamo) {
        this.titulo = titulo;
        this.correoPrestador = correoPrestador;
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCorreoPrestador() {
        return correoPrestador;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }
}