package models;

public class Biblioteca {
    private int idUsuario;
    private String isbnLibro;
    private boolean enBiblioteca;
    private boolean comprado;
    private boolean leido;
    private boolean prestado;

    public Biblioteca() {

    }

    public Biblioteca(int idUsuario, String isbnLibro, boolean enBiblioteca, boolean comprado, boolean leido, boolean prestado) {
        this.idUsuario = idUsuario;
        this.isbnLibro = isbnLibro;
        this.enBiblioteca = enBiblioteca;
        this.comprado = comprado;
        this.leido = leido;
        this.prestado = prestado;
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

    public boolean isEnBiblioteca() {
        return enBiblioteca;
    }

    public void setEnBiblioteca(boolean enBiblioteca) {
        this.enBiblioteca = enBiblioteca;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }
}
