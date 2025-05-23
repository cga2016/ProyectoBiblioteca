package models;

import java.time.LocalDate;

public class Prestamos {

    private int idPrestador;
    private String isbnLibro;
    private int idRecibidor;
    private LocalDate fechaPrestamo;

    public Prestamos() {
    }

    public Prestamos(int idPrestador, String isbnLibro, int idRecibidor, LocalDate fechaPrestamo) {
        this.idPrestador = idPrestador;
        this.isbnLibro = isbnLibro;
        this.idRecibidor = idRecibidor;
        this.fechaPrestamo = fechaPrestamo;
    }

    public int getIdPrestador() {
        return idPrestador;
    }

    public void setIdPrestador(int idPrestador) {
        this.idPrestador = idPrestador;
    }

    public String getIsbnLibro() {
        return isbnLibro;
    }

    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    public int getIdRecibidor() {
        return idRecibidor;
    }

    public void setIdRecibidor(int idRecibidor) {
        this.idRecibidor = idRecibidor;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "idPrestador=" + idPrestador +
                ", isbnLibro='" + isbnLibro + '\'' +
                ", idRecibidor=" + idRecibidor +
                ", fechaPrestamo=" + fechaPrestamo +
                '}';
    }
}
