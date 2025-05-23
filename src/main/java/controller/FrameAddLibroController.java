package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import models.Metodos;
import models.UsuarioIniciado;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import connection.Conexion;

public class FrameAddLibroController {

    @FXML private Label tituloFecha;
    @FXML private Button btnAnadirImagen, btnGuardar;
    @FXML private ImageView btnHome, btnLogOff, imageLibro;
    @FXML private Label btnRegresar, tituloComentario, tituloNotas;
    @FXML private CheckBox checkAnadirColeccion, checkComprado, checkLeido;
    @FXML private ScrollPane scrollPane;
    @FXML private TextField txtAutor, txtFecha, txtGeneros, txtISBN, txtNota, txtTitulo;
    @FXML private TextArea txtComentario, txtDescripcion;
    @FXML private DatePicker txtDate;
    @FXML private Label txtFechaDePublicación;

    // Ruta de la imagen seleccionada o por defecto
    private String imagenRuta = "src/main/java/images/libro.png";

    @FXML
    public void initialize() {
        checkAnadirColeccion.setSelected(true);
        checkAnadirColeccion.setDisable(true);

        // Cargar imagen por defecto
        imageLibro.setImage(new Image(new File(imagenRuta).toURI().toString()));

        manejarVisibilidadCampos();
        checkLeido.setOnAction(e -> manejarVisibilidadCampos());
    }

    private void manejarVisibilidadCampos() {
        boolean leido = checkLeido.isSelected();
        scrollPane.setVisible(leido);
        tituloComentario.setVisible(leido);
        tituloNotas.setVisible(leido);
        tituloFecha.setVisible(leido);
        txtComentario.setVisible(leido);
        txtNota.setVisible(leido);
        txtDate.setVisible(leido);

        if (!leido) {
            txtComentario.clear();
            txtNota.clear();
            txtDate.setValue(null);
        }
    }
/**
 * Metodo que guarda la imagen, crea la carpeta necesaria si no la tiene y le da un nombre unico.
 * @param event
 */
    @FXML
    void AnadirImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(btnAnadirImagen.getScene().getWindow());
        if (selectedFile != null) {
            try {

                Path destinoDir = Path.of("src/main/java/images");
                if (!Files.exists(destinoDir)) {
                    Files.createDirectories(destinoDir);
                }


                String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
                String nombreDestino = "imagen_" + System.currentTimeMillis() + extension;
                Path destino = destinoDir.resolve(nombreDestino);
                Files.copy(selectedFile.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);


                imagenRuta = "/images/" + nombreDestino;


                imageLibro.setImage(new Image(destino.toUri().toString()));

            } catch (Exception e) {
                e.printStackTrace();
                Metodos.mostrarMensajeError("No se pudo cargar la imagen.");
            }
        }
    }

    @FXML
    void guardar(ActionEvent event) {
        String isbn = txtISBN.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String fechaPublicacion = txtFecha.getText().trim();
        int idUsuario = UsuarioIniciado.getUsuario().getId();

        boolean enBiblioteca = checkAnadirColeccion.isSelected();
        boolean comprado = checkComprado.isSelected();
        boolean leido = checkLeido.isSelected();

        // Si está marcado "Leído", validar que se haya seleccionado fecha
        if (leido && txtDate.getValue() == null) {
            Metodos.mostrarMensajeError("Debes indicar la fecha de lectura antes de guardar.");
            return;
        }

        try (Connection conn = Conexion.conectar()) {

            // Validar si ya existe un libro con ese ISBN
            PreparedStatement checkIsbn = conn.prepareStatement("SELECT COUNT(*) FROM Libro WHERE iSBN = ?");
            checkIsbn.setString(1, isbn);
            var rs = checkIsbn.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                Metodos.mostrarMensajeError("No se pudo crear el libro, ISBN ya existente en la db.");
                return;
            }

            // 1) Insertar en Libro
            PreparedStatement psLibro = conn.prepareStatement(
                "INSERT INTO Libro (iSBN, nombre, autor, fechaPublicacion, imagenPeque, imagenGrande) VALUES (?, ?, ?, ?, ?, ?)");
            psLibro.setString(1, isbn);
            psLibro.setString(2, titulo);
            psLibro.setString(3, autor);
            psLibro.setString(4, fechaPublicacion);
            psLibro.setString(5, imagenRuta);
            psLibro.setString(6, imagenRuta);
            psLibro.executeUpdate();

            // 2) Insertar en Biblioteca
            PreparedStatement psBiblio = conn.prepareStatement(
                "INSERT INTO Biblioteca (idUsuario, isbnLibro, enBiblioteca, comprado, leido) VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE enBiblioteca=VALUES(enBiblioteca), comprado=VALUES(comprado), leido=VALUES(leido)");
            psBiblio.setInt(1, idUsuario);
            psBiblio.setString(2, isbn);
            psBiblio.setBoolean(3, enBiblioteca);
            psBiblio.setBoolean(4, comprado);
            psBiblio.setBoolean(5, leido);
            psBiblio.executeUpdate();

            // 3) Si está marcado "Leído", insertar en Resenia con fechaLeido
            if (leido) {
                String comentario = txtComentario.getText().trim();
                double nota = Double.parseDouble(txtNota.getText().trim());
                Date fechaLeido = Date.valueOf(txtDate.getValue());

                PreparedStatement psResenia = conn.prepareStatement(
                    "INSERT INTO Resenia (comentario, nota, idUsuario, isbnLibro, fechaLeido) VALUES (?, ?, ?, ?, ?)");
                psResenia.setString(1, comentario);
                psResenia.setDouble(2, nota);
                psResenia.setInt(3, idUsuario);
                psResenia.setString(4, isbn);
                psResenia.setDate(5, fechaLeido);
                psResenia.executeUpdate();
            }

            Metodos.mostrarMensajeConfirmacion("Libro y datos guardados correctamente.");
            Metodos.cambiarEscena(event, "/view/FrameHome.fxml", "Home");

        } catch (Exception e) {
            e.printStackTrace();
            Metodos.mostrarMensajeError("No se pudo guardar el libro: " + e.getMessage());
        }
    }


    @FXML void regresar(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameHome.fxml", "Home");
    }
    @FXML void cerrarSesion(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameLoggin.fxml", "CerrarSesion");
    }
    @FXML void volverHome(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameHome.fxml", "Home");
    }
    @FXML void anadirColeccion(ActionEvent event) {}
    @FXML void anadirComprado(ActionEvent event) {}
    @FXML void anadirLeido(ActionEvent event) {}
    @FXML void ventanaUsuario(MouseEvent event) {}

}

