package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Libro;
import models.Metodos;
import models.Resenia;
import models.UsuarioIniciado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connection.Conexion;

public class FrameLibroBibliotecaController {

    @FXML
    private Button btnAnterior;

    @FXML
    private Button btnGuardar;

    @FXML
    private ImageView btnHome;

    @FXML
    private ImageView btnLogOff;

    @FXML
    private Label btnRegresar;

    @FXML
    private Button btnSiguiente;

    @FXML
    private CheckBox checkAnadirColeccion;

    @FXML
    private CheckBox checkComentarios;

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

    private List<Resenia> resenias = new ArrayList<>();
    private int indiceActual = 0;

    @FXML
    public void initialize() {
        txtDescriptcion.setWrapText(true);
        Libro libro = Metodos.getLibroSeleccionado();

        if (libro != null) {
            txtTitulo.setText(libro.getNombre());
            txtAutor.setText("Autor: " + libro.getAutor());
            txtDescriptcion.setText("Descripción: " + libro.getDescripcion());
            txtISBN.setText("ISBN: " + libro.getiSBN());
            txtGeneros.setText("Géneros: " + String.join(", ", libro.getGeneros()));
            txtFechaDePublicación.setText("Fecha: " + libro.getFechaPublicacion());

            if (libro.getImagenPeque() != null && !libro.getImagenPeque().isEmpty()) {
                imageLibro.setImage(new Image(libro.getImagenPeque()));
            }

            cargarDatosBiblioteca(libro.getiSBN(), UsuarioIniciado.getUsuario().getId());
        }

        btnAnterior.setVisible(false);
        btnSiguiente.setVisible(false);
        manejarVisibilidadCampos();

        checkComentarios.setOnAction(e -> manejarVisibilidadCampos());
        checkLeido.setOnAction(e -> manejarVisibilidadCampos());
    }

    private void manejarVisibilidadCampos() {
        boolean modoComentario = checkComentarios.isSelected();
        boolean modoLeido = checkLeido.isSelected() && !modoComentario;

        if (modoComentario) {
            txtComentario.clear();
            txtNota.clear();
            txtComentario.setEditable(false);
            txtNota.setEditable(false);
            btnAnterior.setVisible(true);
            btnSiguiente.setVisible(true);
            scrollPane.setVisible(true);
            tituloComentario.setVisible(true);
            tituloNotas.setVisible(true);
            txtComentario.setVisible(true);
            txtNota.setVisible(true);
            btnGuardar.setVisible(false);
            checkAnadirColeccion.setVisible(false);
            checkComprado.setVisible(false);
            checkLeido.setVisible(false);
            txtDate.setVisible(false);
            tituloFecha.setVisible(false);
            cargarResenias();
            mostrarResenia(0);
        } else if (modoLeido) {
            txtComentario.clear();
            txtNota.clear();
            txtComentario.setEditable(true);
            txtNota.setEditable(true);
            scrollPane.setVisible(true);
            tituloComentario.setVisible(true);
            tituloNotas.setVisible(true);
            txtComentario.setVisible(true);
            txtNota.setVisible(true);
            btnAnterior.setVisible(false);
            btnSiguiente.setVisible(false);
            btnGuardar.setVisible(true);
            checkAnadirColeccion.setVisible(true);
            checkComprado.setVisible(true);
            checkLeido.setVisible(true);
            txtDate.setVisible(true);
            tituloFecha.setVisible(true);
            
        } else {
            scrollPane.setVisible(false);
            tituloComentario.setVisible(false);
            tituloNotas.setVisible(false);
            txtComentario.setVisible(false);
            txtNota.setVisible(false);
            btnAnterior.setVisible(false);
            btnSiguiente.setVisible(false);
            btnGuardar.setVisible(true);
            checkAnadirColeccion.setVisible(true);
            checkComprado.setVisible(true);
            checkLeido.setVisible(true);
            txtDate.setVisible(false);
            tituloFecha.setVisible(false);
        }
    }

    private void cargarDatosBiblioteca(String isbn, int idUsuario) {
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT enBiblioteca, comprado, leido FROM Biblioteca WHERE idUsuario = ? AND isbnLibro = ?")) {
            stmt.setInt(1, idUsuario);
            stmt.setString(2, isbn);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                checkAnadirColeccion.setSelected(rs.getBoolean("enBiblioteca"));
                checkComprado.setSelected(rs.getBoolean("comprado"));
                checkLeido.setSelected(rs.getBoolean("leido"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void guardar(ActionEvent event) {
        Libro libro = Metodos.getLibroSeleccionado();
        int idUsuario = UsuarioIniciado.getUsuario().getId();

        if (libro == null) return;

        try (Connection conn = Conexion.conectar()) {
            PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM Biblioteca WHERE idUsuario = ? AND isbnLibro = ?");
            checkStmt.setInt(1, idUsuario);
            checkStmt.setString(2, libro.getiSBN());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            boolean exists = rs.getInt(1) > 0;

            if (exists) {
                PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE Biblioteca SET enBiblioteca = ?, comprado = ?, leido = ? WHERE idUsuario = ? AND isbnLibro = ?");
                updateStmt.setBoolean(1, checkAnadirColeccion.isSelected());
                updateStmt.setBoolean(2, checkComprado.isSelected());
                updateStmt.setBoolean(3, checkLeido.isSelected());
                updateStmt.setInt(4, idUsuario);
                updateStmt.setString(5, libro.getiSBN());
                updateStmt.executeUpdate();
            } else {
                PreparedStatement insertStmt = conn.prepareStatement(
                        "INSERT INTO Biblioteca (idUsuario, isbnLibro, enBiblioteca, comprado, leido) VALUES (?, ?, ?, ?, ?)");
                insertStmt.setInt(1, idUsuario);
                insertStmt.setString(2, libro.getiSBN());
                insertStmt.setBoolean(3, checkAnadirColeccion.isSelected());
                insertStmt.setBoolean(4, checkComprado.isSelected());
                insertStmt.setBoolean(5, checkLeido.isSelected());
                insertStmt.executeUpdate();
            }

            if (checkLeido.isSelected() && !checkComentarios.isSelected()) {
                String comentario = txtComentario.getText().trim();
                String notaStr = txtNota.getText().trim();

                if (comentario.isEmpty() || notaStr.isEmpty()) {
                    mostrarAlerta("Por favor, rellene tanto el comentario como la nota.");
                    return;
                }

                double nota;
                try {
                    nota = Double.parseDouble(notaStr);
                } catch (NumberFormatException e) {
                    mostrarAlerta("La nota debe ser un número válido.");
                    return;
                }

                // comprueba si ya existe reseña
                PreparedStatement checkResenia = conn.prepareStatement(
                        "SELECT COUNT(*) FROM Resenia WHERE idUsuario = ? AND isbnLibro = ?");
                checkResenia.setInt(1, idUsuario);
                checkResenia.setString(2, libro.getiSBN());
                rs = checkResenia.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    mostrarAlerta("Ya existe una reseña para este libro creada por ti.");
                } else {
                    PreparedStatement insertResenia = conn.prepareStatement(
                            "INSERT INTO Resenia (comentario, nota, idUsuario, isbnLibro, fechaLeido) VALUES (?, ?, ?, ?, NOW())");
                    insertResenia.setString(1, comentario);
                    insertResenia.setDouble(2, nota);
                    insertResenia.setInt(3, idUsuario);
                    insertResenia.setString(4, libro.getiSBN());
                    insertResenia.executeUpdate();
                    mostrarAlerta("Datos y reseña guardados correctamente.");
                    return;
                }
            }

            mostrarAlerta("Datos guardados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al guardar los datos.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarResenias() {
        resenias.clear();
        Libro libro = Metodos.getLibroSeleccionado();
        if (libro == null) return;

        String isbn = libro.getiSBN();

        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT comentario, nota FROM Resenia WHERE isbnLibro = ?")) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Resenia r = new Resenia();
                r.setComentario(rs.getString("comentario"));
                r.setNota(rs.getDouble("nota"));
                resenias.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarResenia(int indice) {
        if (resenias.isEmpty()) {
            txtComentario.setText("No hay reseñas disponibles.");
            txtNota.setText("");
            return;
        }

        if (indice < 0 || indice >= resenias.size()) return;

        Resenia r = resenias.get(indice);
        txtComentario.setText(r.getComentario());
        txtNota.setText(String.valueOf(r.getNota()));
        indiceActual = indice;
    }

    @FXML
    void comentarioAnterior(ActionEvent event) {
        if (indiceActual > 0) {
            mostrarResenia(--indiceActual);
        }
    }

    @FXML
    void comentarioSiguiente(ActionEvent event) {
        if (indiceActual < resenias.size() - 1) {
            mostrarResenia(++indiceActual);
        }
    }

    @FXML void cambiarModo(ActionEvent event) {
    	
    	
    }
    @FXML void anadirColeccion(ActionEvent event) {
    	
    }
    @FXML void anadirComprado(ActionEvent event) {
    	
    }
    @FXML void anadirLeido(ActionEvent event) {
    	
    }
    @FXML void cerrarSesion(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameLoggin.fxml", "Registro");
    }
    @FXML void regresar(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameBiblioteca.fxml", "Biblioteca");
    }
    @FXML void ventanaUsuario(MouseEvent event) {
    	
    }
    @FXML void volverHome(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameHome.fxml", "Inicio");
    }
}

