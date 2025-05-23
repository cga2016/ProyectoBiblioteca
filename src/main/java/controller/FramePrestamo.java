package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connection.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import models.Libro;
import models.Metodos;
import models.PrestamoVista;
import models.UsuarioIniciado;

public class FramePrestamo {

    @FXML
    private Button btnDevolver;

    @FXML
    private Button btnPrestar;

    @FXML
    private ChoiceBox<String> checkLibros;

    @FXML
    private RadioButton checkMisPrestamos;

    @FXML
    private RadioButton checkPrestamos;

    @FXML
    private TextField txtUsuarios;

    private ToggleGroup toggleGroup;
    private ArrayList<Libro> librosUsuario = new ArrayList<>();
    private ObservableList<PrestamoVista> prestamosVista = FXCollections.observableArrayList();

    @FXML
    private TableView<PrestamoVista> tablaDevoluciones;

    @FXML
    private TableColumn<PrestamoVista, String> columnaTitulo = new TableColumn<>();

    @FXML
    private TableColumn<PrestamoVista, String> columnaCorreo = new TableColumn<>();

    @FXML
    private TableColumn<PrestamoVista, String> columnaFecha = new TableColumn<>();

    @FXML
    public void initialize() {
        toggleGroup = new ToggleGroup();
        checkPrestamos.setToggleGroup(toggleGroup);
        checkMisPrestamos.setToggleGroup(toggleGroup);
        checkMisPrestamos.setSelected(true); // Seleccionado por defecto

        // Configurar columnas
        columnaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correoPrestador"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));

        tablaDevoluciones.setItems(prestamosVista);

        btnDevolver.setVisible(false);
        btnPrestar.setVisible(false);
        checkLibros.setVisible(false);
        txtUsuarios.setVisible(false);
        tablaDevoluciones.setVisible(true); // visible por defecto

        // Mostrar préstamos activos (yo presto a otros)
        checkPrestamos.setOnAction(e -> {
            if (checkPrestamos.isSelected()) {
                mostrarPrestamos();
                cargarLibrosDelUsuario();
                tablaDevoluciones.setVisible(false); // oculta tabla
            }
        });

        // Mostrar libros que me han prestado
        checkMisPrestamos.setOnAction(e -> {
            if (checkMisPrestamos.isSelected()) {
                mostrarMisPrestamos();
                cargarLibrosPrestadosAMi();
                tablaDevoluciones.setVisible(true); // muestra tabla
            }
        });

        // Listener para seleccionar libro en ChoiceBox al seleccionar fila en la tabla
        tablaDevoluciones.setRowFactory(tv -> {
            TableRow<PrestamoVista> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    PrestamoVista prestamo = row.getItem();
                    checkLibros.setValue(prestamo.getTitulo());
                }
            });
            return row;
        });

        // Carga inicial
        mostrarMisPrestamos();
        cargarLibrosPrestadosAMi();
    }

    private void mostrarPrestamos() {
        btnPrestar.setVisible(true);
        txtUsuarios.setVisible(true);
        checkLibros.setVisible(true);
        btnDevolver.setVisible(false);
    }

    private void mostrarMisPrestamos() {
        btnPrestar.setVisible(false);
        txtUsuarios.setVisible(false);
        checkLibros.setVisible(true);
        btnDevolver.setVisible(true);
    }

    private void cargarLibrosDelUsuario() {
        librosUsuario.clear();
        checkLibros.getItems().clear();

        int idUsuario = UsuarioIniciado.getUsuario().getId();

        String consulta = """
            SELECT l.*
            FROM Biblioteca b
            JOIN Libro l ON b.isbnLibro = l.iSBN
            WHERE b.idUsuario = ? AND b.prestado = false AND b.meHanPrestado = false
        """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(consulta)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Libro libro = new Libro(rs);
                librosUsuario.add(libro);
                checkLibros.getItems().add(libro.getNombre());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarLibrosPrestadosAMi() {
        librosUsuario.clear();
        checkLibros.getItems().clear();
        prestamosVista.clear();

        int idUsuario = UsuarioIniciado.getUsuario().getId();

        String consulta = """
            SELECT l.nombre, u.correo, p.fechaPrestamo
            FROM Biblioteca b
            JOIN Libro l ON b.isbnLibro = l.iSBN
            JOIN Prestamo p ON p.isbnLibro = b.isbnLibro AND p.idRecibidor = b.idUsuario
            JOIN Usuario u ON u.id = p.idPrestador
            WHERE b.idUsuario = ? AND b.meHanPrestado = true
        """;

        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(consulta)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nombreLibro = rs.getString("nombre");
                String correoPrestador = rs.getString("correo");
                String fechaPrestamo = rs.getString("fechaPrestamo");

                checkLibros.getItems().add(nombreLibro);
                prestamosVista.add(new PrestamoVista(nombreLibro, correoPrestador, fechaPrestamo));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void devolver(ActionEvent event) {
        String libroSeleccionado = checkLibros.getValue();

        if (libroSeleccionado == null) {
            Metodos.mostrarMensajeConfirmacion("No has seleccionado ningún libro para devolver.");
            return;
        }

        PrestamoVista seleccionado = tablaDevoluciones.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Metodos.mostrarMensajeConfirmacion("Selecciona un libro de la tabla para devolver.");
            return;
        }

        String titulo = seleccionado.getTitulo();
        String correoPrestador = seleccionado.getCorreoPrestador();

        try (Connection conn = Conexion.conectar()) {
            String queryIdPrestador = "SELECT id FROM Usuario WHERE correo = ?";
            int idPrestador;

            try (PreparedStatement stmt = conn.prepareStatement(queryIdPrestador)) {
                stmt.setString(1, correoPrestador);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    idPrestador = rs.getInt("id");
                } else {
                    Metodos.mostrarMensajeConfirmacion("No se encontró al prestador.");
                    return;
                }
            }

            String isbnLibro = null;
            String queryIsbn = "SELECT iSBN FROM Libro WHERE nombre = ?";
            try (PreparedStatement stmt = conn.prepareStatement(queryIsbn)) {
                stmt.setString(1, titulo);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    isbnLibro = rs.getString("iSBN");
                } else {
                    Metodos.mostrarMensajeConfirmacion("No se encontró el libro.");
                    return;
                }
            }

            int idActual = UsuarioIniciado.getUsuario().getId();

            String deletePrestamo = """
                DELETE FROM Prestamo 
                WHERE idPrestador = ? AND idRecibidor = ? AND isbnLibro = ?
            """;

            try (PreparedStatement stmt = conn.prepareStatement(deletePrestamo)) {
                stmt.setInt(1, idPrestador);
                stmt.setInt(2, idActual);
                stmt.setString(3, isbnLibro);
                stmt.executeUpdate();
            }

            String updatePrestador = """
                UPDATE Biblioteca 
                SET prestado = false 
                WHERE idUsuario = ? AND isbnLibro = ?
            """;

            try (PreparedStatement stmt = conn.prepareStatement(updatePrestador)) {
                stmt.setInt(1, idPrestador);
                stmt.setString(2, isbnLibro);
                stmt.executeUpdate();
            }

            String deleteBiblioteca = """
                DELETE FROM Biblioteca 
                WHERE idUsuario = ? AND isbnLibro = ?
            """;

            try (PreparedStatement stmt = conn.prepareStatement(deleteBiblioteca)) {
                stmt.setInt(1, idActual);
                stmt.setString(2, isbnLibro);
                stmt.executeUpdate();
            }

            Metodos.mostrarMensajeConfirmacion("Libro devuelto con éxito.");
            Window window = btnDevolver.getScene().getWindow();
            window.hide();

        } catch (Exception e) {
            e.printStackTrace();
            Metodos.mostrarMensajeConfirmacion("Error al devolver el libro.");
        }
    }

    @FXML
    void prestar(ActionEvent event) {
        String correoReceptor = txtUsuarios.getText().trim();
        if (correoReceptor.isEmpty()) {
            System.out.println("Introduce un correo.");
            return;
        }

        String libroSeleccionado = checkLibros.getValue();
        if (libroSeleccionado == null) {
            System.out.println("Selecciona un libro.");
            return;
        }

        String isbn = librosUsuario.stream()
                .filter(libro -> libro.getNombre().equals(libroSeleccionado))
                .map(Libro::getiSBN)
                .findFirst()
                .orElse(null);

        if (isbn == null) {
            System.out.println("Error al obtener el ISBN del libro.");
            return;
        }

        try (Connection conn = Conexion.conectar()) {
            String buscarUsuario = "SELECT id FROM Usuario WHERE correo = ?";
            int idReceptor;

            try (PreparedStatement stmtBuscar = conn.prepareStatement(buscarUsuario)) {
                stmtBuscar.setString(1, correoReceptor);
                ResultSet rs = stmtBuscar.executeQuery();
                if (rs.next()) {
                    idReceptor = rs.getInt("id");
                } else {
                    System.out.println("Usuario no encontrado.");
                    return;
                }
            }

            int idActual = UsuarioIniciado.getUsuario().getId();

            String updatePrestamo = """
                UPDATE Biblioteca
                SET prestado = true
                WHERE idUsuario = ? AND isbnLibro = ?
            """;

            try (PreparedStatement stmtUpdate = conn.prepareStatement(updatePrestamo)) {
                stmtUpdate.setInt(1, idActual);
                stmtUpdate.setString(2, isbn);
                stmtUpdate.executeUpdate();
            }

            String existeEntrada = """
                SELECT COUNT(*) AS total FROM Biblioteca
                WHERE idUsuario = ? AND isbnLibro = ?
            """;

            try (PreparedStatement stmtVerificar = conn.prepareStatement(existeEntrada)) {
                stmtVerificar.setInt(1, idReceptor);
                stmtVerificar.setString(2, isbn);
                ResultSet rs = stmtVerificar.executeQuery();
                if (rs.next() && rs.getInt("total") > 0) {
                    System.out.println("Este usuario ya tiene una copia del libro.");
                    return;
                }
            }

            String insertarPrestamo = """
                INSERT INTO Biblioteca (idUsuario, isbnLibro, enBiblioteca, prestado, meHanPrestado)
                VALUES (?, ?, true, false, true)
            """;

            try (PreparedStatement stmtInsert = conn.prepareStatement(insertarPrestamo)) {
                stmtInsert.setInt(1, idReceptor);
                stmtInsert.setString(2, isbn);
                stmtInsert.executeUpdate();
            }

            String insertarRegistro = """
                INSERT INTO Prestamo (idPrestador, isbnLibro, idRecibidor, fechaPrestamo)
                VALUES (?, ?, ?, NOW())
            """;

            try (PreparedStatement stmtPrestamo = conn.prepareStatement(insertarRegistro)) {
                stmtPrestamo.setInt(1, idActual);
                stmtPrestamo.setString(2, isbn);
                stmtPrestamo.setInt(3, idReceptor);
                stmtPrestamo.executeUpdate();
            }

            Metodos.mostrarMensajeConfirmacion("Libro prestado con éxito");
            Window window = btnPrestar.getScene().getWindow();
            window.hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    void misLibrosPrestados(ActionEvent event) {

    }

    @FXML
    void misPrestamos(ActionEvent event) {

    }






}
