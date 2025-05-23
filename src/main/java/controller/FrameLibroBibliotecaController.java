package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.Conexion;
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

public class FrameLibroBibliotecaController {

    @FXML private Button btnAnterior, btnGuardar, btnSiguiente;
    @FXML private ImageView btnHome, btnLogOff, imageLibro;
    @FXML private Label btnRegresar;
    @FXML private CheckBox checkAnadirColeccion, checkComentarios, checkComprado, checkLeido;
    @FXML private ScrollPane scrollPane;
    @FXML private Label tituloComentario, tituloFecha, tituloNotas;
    @FXML private Label txtAutor, txtDescriptcion, txtFechaDePublicación,
                  txtGeneros, txtISBN, txtTitulo;
    @FXML private TextArea txtComentario;
    @FXML private TextField txtNota;
    @FXML private DatePicker txtDate;

    private final Image imagenDefault =
        new Image(getClass().getResourceAsStream("/images/libro.png"));

    private List<Resenia> resenias = new ArrayList<>();
    private int indiceActual = 0;
    private Resenia reseniaUsuario; // la reseña del usuario actual

    @FXML
    public void initialize() {
        txtDescriptcion.setWrapText(true);

        Libro libro = Metodos.getLibroSeleccionado();
        if (libro != null) {
            // Datos del libro
            txtTitulo.setText(libro.getNombre());
            txtAutor.setText("Autor: " + libro.getAutor());
            txtDescriptcion.setText("Descripción: " + libro.getDescripcion());
            txtISBN.setText("ISBN: " + libro.getiSBN());
            txtGeneros.setText("Géneros: " + String.join(", ", libro.getGeneros()));
            txtFechaDePublicación.setText("Fecha: " + libro.getFechaPublicacion());
            loadImageSafe(libro.getImagenPeque());
            cargarDatosBiblioteca(libro.getiSBN(), UsuarioIniciado.getUsuario().getId());
        }

        // Estado inicial
        btnAnterior.setVisible(false);
        btnSiguiente.setVisible(false);
        manejarVisibilidadCampos();

        // Listeners de cambio de modo
        checkComentarios.setOnAction(e -> manejarVisibilidadCampos());
        checkLeido.setOnAction(e -> manejarVisibilidadCampos());
    }

    /** Carga imagen desde URL, recurso o fallback */
    private void loadImageSafe(String ruta) {
        Image img = imagenDefault;
        try {
            if (ruta != null && !ruta.isBlank()) {
                if (ruta.startsWith("http://") || ruta.startsWith("https://")) {
                    img = new Image(ruta, true);
                    if (img.isError()) throw new Exception("HTTP error");
                } else if (ruta.startsWith("/images/")) {
                    img = new Image(getClass().getResourceAsStream(ruta));
                } else {
                    img = new Image("file:" + ruta);
                    if (img.isError()) throw new Exception("File error");
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar imagen '" + ruta + "': " + e.getMessage());
            img = imagenDefault;
        }
        imageLibro.setImage(img);
    }

    /** Ajusta visibilidad y carga datos según el modo seleccionado */
    private void manejarVisibilidadCampos() {
        boolean modoComentario = checkComentarios.isSelected();
        boolean modoLeido = checkLeido.isSelected() && !modoComentario;

        // Modo visualizar todos los comentarios
        if (modoComentario) {
            prepararModoLectura(false);
            cargarResenias();              // todas las reseñas del libro
            indiceActual = 0;
            mostrarResenia(indiceActual);

        // Modo añadir/editar reseña del usuario
        } else if (modoLeido) {
            prepararModoLectura(true);
            cargarReseniaUsuario();        // solo reseña del usuario actual
            mostrarReseniaUsuario();

        // Modo normal (sin reseñas visibles)
        } else {
            prepararModoNormal();
        }
    }

    private void prepararModoLectura(boolean editable) {
        scrollPane.setVisible(true);
        tituloComentario.setVisible(true);
        tituloNotas.setVisible(true);
        txtComentario.setEditable(editable);
        txtNota.setEditable(editable);
        btnGuardar.setVisible(editable);
        checkAnadirColeccion.setVisible(editable);
        checkComprado.setVisible(editable);
        checkLeido.setVisible(true);
        txtDate.setVisible(editable);
        tituloFecha.setVisible(editable);
        boolean navegacion = !editable;
        btnAnterior.setVisible(navegacion);
        btnSiguiente.setVisible(navegacion);
    }

    private void prepararModoNormal() {
        scrollPane.setVisible(false);
        tituloComentario.setVisible(false);
        tituloNotas.setVisible(false);
        txtComentario.setVisible(false);
        txtNota.setVisible(false);
        txtDate.setVisible(false);
        tituloFecha.setVisible(false);
        btnGuardar.setVisible(true);
        btnAnterior.setVisible(false);
        btnSiguiente.setVisible(false);
        checkAnadirColeccion.setVisible(true);
        checkComprado.setVisible(true);
        checkLeido.setVisible(true);
    }


    private void cargarDatosBiblioteca(String isbn, int idUsuario) {
        String sql = "SELECT enBiblioteca, comprado, leido FROM Biblioteca WHERE idUsuario=? AND isbnLibro=?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, isbn);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                checkAnadirColeccion.setSelected(rs.getBoolean("enBiblioteca"));
                checkComprado.setSelected(rs.getBoolean("comprado"));
                checkLeido.setSelected(rs.getBoolean("leido"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Carga todas las reseñas de este libro */
    private void cargarResenias() {
        resenias.clear();
        Libro libro = Metodos.getLibroSeleccionado();
        if (libro == null) return;
        String sql = "SELECT comentario,nota,idUsuario,fechaLeido FROM Resenia WHERE isbnLibro=?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, libro.getiSBN());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Resenia r = new Resenia();
                r.setComentario(rs.getString("comentario"));
                r.setNota(rs.getDouble("nota"));
                r.setIdUsuario(rs.getInt("idUsuario"));
                r.setFechaLeido(rs.getDate("fechaLeido"));
                resenias.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Carga la reseña (si existe) del usuario actual */
    private void cargarReseniaUsuario() {
        reseniaUsuario = null;
        Libro libro = Metodos.getLibroSeleccionado();
        int userId = UsuarioIniciado.getUsuario().getId();
        if (libro == null) return;
        String sql = "SELECT comentario,nota,fechaLeido FROM Resenia WHERE isbnLibro=? AND idUsuario=?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, libro.getiSBN());
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Resenia r = new Resenia();
                r.setComentario(rs.getString("comentario"));
                r.setNota(rs.getDouble("nota"));
                r.setFechaLeido(rs.getDate("fechaLeido"));
                reseniaUsuario = r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Muestra en los campos la reseña del usuario (o limpia si no existe) */
    private void mostrarReseniaUsuario() {
        if (reseniaUsuario != null) {
            txtComentario.setText(reseniaUsuario.getComentario());
            txtNota.setText(String.valueOf(reseniaUsuario.getNota()));
            txtDate.setValue(reseniaUsuario.getFechaLeido().toLocalDate());
        } else {
            txtComentario.clear();
            txtNota.clear();
            txtDate.setValue(null);
        }
    }


    private void mostrarResenia(int idx) {
        if (resenias.isEmpty()) {
            txtComentario.setText("No hay reseñas disponibles.");
            txtNota.clear();
            txtDate.setValue(null);
            btnAnterior.setDisable(true);
            btnSiguiente.setDisable(true);
            checkLeido.setVisible(false);
            return;
        }
        Resenia r = resenias.get(idx);
        txtComentario.setText(r.getComentario());
        txtNota.setText(String.valueOf(r.getNota()));
        txtDate.setValue(r.getFechaLeido().toLocalDate());
        btnAnterior.setDisable(idx == 0);
        btnSiguiente.setDisable(idx >= resenias.size() - 1);
        indiceActual = idx;
    }

    @FXML void comentarioAnterior(ActionEvent e) {
        if (indiceActual > 0) mostrarResenia(--indiceActual);
    }
    @FXML void comentarioSiguiente(ActionEvent e) {
        if (indiceActual < resenias.size() - 1) mostrarResenia(++indiceActual);
    }

    @FXML
    void guardar(ActionEvent event) {
        Libro libro = Metodos.getLibroSeleccionado();
        int idUsuario = UsuarioIniciado.getUsuario().getId();
        if (libro == null) return;

        try (Connection conn = Conexion.conectar()) {
            // Inserta/actualiza Biblioteca
            String checkSql = "SELECT COUNT(*) FROM Biblioteca WHERE idUsuario=? AND isbnLibro=?";
            PreparedStatement psCheck = conn.prepareStatement(checkSql);
            psCheck.setInt(1, idUsuario);
            psCheck.setString(2, libro.getiSBN());
            ResultSet rs = psCheck.executeQuery(); rs.next();
            boolean existe = rs.getInt(1) > 0;

            if (existe) {
                String upd = "UPDATE Biblioteca SET enBiblioteca=?,comprado=?,leido=? WHERE idUsuario=? AND isbnLibro=?";
                PreparedStatement psUpd = conn.prepareStatement(upd);
                psUpd.setBoolean(1, checkAnadirColeccion.isSelected());
                psUpd.setBoolean(2, checkComprado.isSelected());
                psUpd.setBoolean(3, checkLeido.isSelected());
                psUpd.setInt(4, idUsuario);
                psUpd.setString(5, libro.getiSBN());
                psUpd.executeUpdate();
            } else {
                String ins = "INSERT INTO Biblioteca(idUsuario,isbnLibro,enBiblioteca,comprado,leido) VALUES (?,?,?,?,?)";
                PreparedStatement psIns = conn.prepareStatement(ins);
                psIns.setInt(1, idUsuario);
                psIns.setString(2, libro.getiSBN());
                psIns.setBoolean(3, checkAnadirColeccion.isSelected());
                psIns.setBoolean(4, checkComprado.isSelected());
                psIns.setBoolean(5, checkLeido.isSelected());
                psIns.executeUpdate();
            }

            // Si es modo “leído” sin ver reseñas, guarda una nueva reseña
            if (checkLeido.isSelected() && !checkComentarios.isSelected()) {
                String comentario = txtComentario.getText().trim();
                String notaStr = txtNota.getText().trim();
                if (comentario.isEmpty() || notaStr.isEmpty() || txtDate.getValue()==null) {
                    mostrarAlerta("Por favor, complete comentario, nota y fecha.");
                    return;
                }
                double nota;
                try { nota = Double.parseDouble(notaStr); }
                catch (NumberFormatException ex) {
                    mostrarAlerta("La nota debe ser un número válido.");
                    return;
                }

             // Comprueba si ya existe reseña del usuario para este libro
                String chkR = "SELECT id, comentario, nota, fechaLeido FROM Resenia WHERE idUsuario=? AND isbnLibro=?";
                PreparedStatement psChkR = conn.prepareStatement(chkR);
                psChkR.setInt(1, idUsuario);
                psChkR.setString(2, libro.getiSBN());
                rs = psChkR.executeQuery();
                if (rs.next()) {
                    int idResenia = rs.getInt("id");
                    String comentarioActual = rs.getString("comentario");
                    double notaActual = rs.getDouble("nota");
                    Date fechaActual = rs.getDate("fechaLeido");

                    boolean cambiado = !comentario.equals(comentarioActual) ||
                                       nota != notaActual ||
                                       !Date.valueOf(txtDate.getValue()).equals(fechaActual);

                    if (cambiado) {
                        String updR = "UPDATE Resenia SET comentario=?, nota=?, fechaLeido=? WHERE id=?";
                        PreparedStatement psUpdR = conn.prepareStatement(updR);
                        psUpdR.setString(1, comentario);
                        psUpdR.setDouble(2, nota);
                        psUpdR.setDate(3, Date.valueOf(txtDate.getValue()));
                        psUpdR.setInt(4, idResenia);
                        psUpdR.executeUpdate();
                        mostrarAlerta("Reseña actualizada correctamente.");
                    } else {
                        mostrarAlerta("No hay cambios en la reseña.");
                    }
                } else {
                    String insR = "INSERT INTO Resenia(comentario,nota,idUsuario,isbnLibro,fechaLeido) VALUES(?,?,?,?,?)";
                    PreparedStatement psInsR = conn.prepareStatement(insR);
                    psInsR.setString(1, comentario);
                    psInsR.setDouble(2, nota);
                    psInsR.setInt(3, idUsuario);
                    psInsR.setString(4, libro.getiSBN());
                    psInsR.setDate(5, Date.valueOf(txtDate.getValue()));
                    psInsR.executeUpdate();
                    mostrarAlerta("Reseña guardada correctamente.");
                }

            }

            mostrarAlerta("Datos guardados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al guardar los datos.");
        }
    }
    
    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    @FXML void cerrarSesion(MouseEvent e) {
        Metodos.cambiarEscena(e, "/view/FrameLoggin.fxml", "Registro");
    }
    @FXML void regresar(MouseEvent e) {
        Metodos.cambiarEscena(e, "/view/FrameBiblioteca.fxml", "Biblioteca");
    }
    @FXML void volverHome(MouseEvent e) {
        Metodos.cambiarEscena(e, "/view/FrameHome.fxml", "Inicio");
    }
    @FXML void ventanaUsuario(MouseEvent event) { }

    @FXML void anadirColeccion(ActionEvent event) { }
    @FXML void anadirComprado(ActionEvent event) { }
    @FXML void anadirLeido(ActionEvent event) { manejarVisibilidadCampos(); }
    @FXML void cambiarModo(ActionEvent event) { manejarVisibilidadCampos(); }

}
