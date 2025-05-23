package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connection.Conexion;
import dao.DaoBibliotecaResenia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import models.Biblioteca;
import models.Libro;
import models.Metodos;
import models.Resenia;
import models.UsuarioIniciado;

public class FrameLibroController {

    @FXML
    private Button btnGuardar;

    @FXML
    private ImageView btnHome;

    @FXML
    private ImageView btnLogOff;

    @FXML
    private Label btnRegresar;

    @FXML
    private CheckBox checkAnadirColeccion;

    @FXML
    private CheckBox checkComprado;

    @FXML
    private CheckBox checkLeido;

    @FXML
    private ImageView imageLibro;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label tituloComentario;

    @FXML
    private Label tituloFecha;

    @FXML
    private Label tituloNotas;

    @FXML
    private Label txtAutor;

    @FXML
    private TextArea txtComentario;

    @FXML
    private DatePicker txtDate;

    @FXML
    private Label txtDescriptcion;

    @FXML
    private Label txtFechaDePublicación;

    @FXML
    private Label txtGeneros;

    @FXML
    private Label txtISBN;

    @FXML
    private TextField txtNota;

    @FXML
    private Label txtTitulo;


    @FXML
    void anadirColeccion(ActionEvent event) {
        if (!checkAnadirColeccion.isSelected()) {
            checkLeido.setSelected(false);
            checkComprado.setSelected(false);
            setComentarioYNotaVisible(false);
        }
    }

    @FXML
    void anadirComprado(ActionEvent event) {
        if (checkComprado.isSelected()) {
            checkAnadirColeccion.setSelected(true);
        }
    }

    @FXML
    void anadirLeido(ActionEvent event) {
        if (checkLeido.isSelected()) {
            checkAnadirColeccion.setSelected(true);
            setComentarioYNotaVisible(true);
        } else {
            setComentarioYNotaVisible(false);
        }
    }

    @FXML
    void guardar(ActionEvent event) {
        boolean anadir = checkAnadirColeccion.isSelected();
        boolean leido = checkLeido.isSelected();
        boolean comprado = checkComprado.isSelected();

        if (!anadir && !leido && !comprado) {
            Metodos.mostrarMensajeConfirmacion("Debes seleccionar al menos una opción.");
            return;
        }

        int idUsuario = UsuarioIniciado.getUsuario().getId();
        Libro libro = Metodos.getLibroSeleccionado();
        if (libro == null || libro.getiSBN() == null) {
            Metodos.mostrarMensajeConfirmacion("No se ha seleccionado un libro válido.");
            return;
        }

        String isbn = libro.getiSBN();

        if (!esLibroExistente(isbn)) {
            DaoBibliotecaResenia.guardarLibro(libro);
        }

        // Validar campos si está marcado como leído
        String comentario = null;
        String notaStr = null;
        double nota = 0.0;
        var fecha = txtDate.getValue();

        if (leido) {
            comentario = txtComentario.getText();
            notaStr = txtNota.getText();

            if (comentario == null || comentario.isEmpty() ||
                notaStr == null || notaStr.isEmpty() ||
                fecha == null) {
                Metodos.mostrarMensajeError("Debe completar comentario, nota y fecha para guardar como leído.");
                return;
            }

            try {
                nota = Double.parseDouble(notaStr);
            } catch (NumberFormatException e) {
                Metodos.mostrarMensajeError("La nota debe ser un número válido.");
                return;
            }
        }

        // Verificar si ya existe entrada en Biblioteca
        if (existeEntradaBiblioteca(idUsuario, isbn)) {
            Metodos.mostrarMensajeError("Este libro ya está en tu biblioteca.");
            return;
        }

        // Guardar reseña si aplica
        if (leido) {
            Resenia resenia = new Resenia();
            resenia.setComentario(comentario);
            resenia.setNota(nota);
            resenia.setIsbnLibro(isbn);
            resenia.setIdUsuario(idUsuario);
            resenia.setFechaLeido(java.sql.Date.valueOf(fecha));
            DaoBibliotecaResenia.guardarResenia(resenia);
        }

        // Guardar entrada en la tabla Biblioteca
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setIdUsuario(idUsuario);
        biblioteca.setIsbnLibro(isbn);
        biblioteca.setEnBiblioteca(true);
        biblioteca.setLeido(leido);
        biblioteca.setComprado(comprado);
        biblioteca.setPrestado(false);
        biblioteca.setMeHanPrestado(false);
        biblioteca.setFechaLeido(leido ? fecha : null); // Guardar fecha solo si está marcado como leído

        DaoBibliotecaResenia.guardarBiblioteca(biblioteca);
        Metodos.mostrarMensajeConfirmacion("Los datos se han guardado correctamente.");
    }

    private boolean existeEntradaBiblioteca(int idUsuario, String isbn) {
        String sql = "SELECT COUNT(*) FROM Biblioteca WHERE idUsuario = ? AND isbnLibro = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setString(2, isbn);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Metodos.mostrarMensajeError("Error al verificar si el libro ya está en tu biblioteca.");
        }
        return false;
    }


    @FXML
    void regresar(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameHome.fxml", "Home");
    }

    @FXML
    public void initialize() {
        setComentarioYNotaVisible(false);

        txtDescriptcion.setWrapText(true);
        Libro libro = Metodos.getLibroSeleccionado();
        if (libro != null) {
            txtTitulo.setText(libro.getNombre());
            txtAutor.setText("Autor: " + libro.getAutor());
            txtDescriptcion.setText("Descriptción: " + libro.getDescripcion());
            txtISBN.setText("ISBN: " + libro.getiSBN());

            String generos = String.join(", ", libro.getGeneros());
            txtGeneros.setText("Generos: " + generos);
            txtFechaDePublicación.setText("Fecha: " + libro.getFechaPublicacion());

            if (libro.getImagenPeque() != null && !libro.getImagenPeque().isEmpty()) {
                imageLibro.setImage(new Image(libro.getImagenPeque()));
            }
        }
    }

    @FXML
    void anteriorComentario(ActionEvent event) {
    }

    @FXML
    void cerrarSesion(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameLoggin.fxml", "Registro");
    }

    @FXML
    void siguienteComentario(ActionEvent event) {

    }

    @FXML
    void ventanaUsuario(MouseEvent event) {
 
    }

    @FXML
    void volverHome(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameHome.fxml", "Home");
    }

    private void setComentarioYNotaVisible(boolean visible) {
        txtComentario.setVisible(visible);
        txtNota.setVisible(visible);
        tituloComentario.setVisible(visible);
        tituloNotas.setVisible(visible);
        scrollPane.setVisible(visible);
        tituloFecha.setVisible(visible);
        txtDate.setVisible(visible);
    }
    
    private boolean esLibroExistente(String isbn) {
        String sql = "SELECT COUNT(*) FROM Libro WHERE iSBN = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, isbn);
            var resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Metodos.mostrarMensajeError("Error al verificar si el libro existe.");
        }
        return false;
    }
    

    @FXML
    void comentarioAnterior(ActionEvent event) {

    }

    @FXML
    void comentarioSiguiente(ActionEvent event) {

    }
    
    @FXML
    void cambiarModo(ActionEvent event) {

    }
}