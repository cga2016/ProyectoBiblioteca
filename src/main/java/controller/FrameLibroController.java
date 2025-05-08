package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Metodos;

public class FrameLibroController {

    @FXML
    private Button btnComentariosAnterior;

    @FXML
    private Button btnGuardar;

    @FXML
    private ImageView btnHome;

    @FXML
    private ImageView btnLogOff;

    @FXML
    private Button btnSiguienteComentarios;

    @FXML
    private CheckBox checkAnadirColeccion;

    @FXML
    private CheckBox checkComprado;

    @FXML
    private CheckBox checkLeido;

    @FXML
    private ImageView imageLibro;

    @FXML
    private Label labelUsuario;

    @FXML
    private Label txtAutor;

    @FXML
    private Label txtComentarios;

    @FXML
    private Label txtDescriptcion;

    @FXML
    private Label txtFechaDePublicación;

    @FXML
    private Label txtGeneros;

    @FXML
    private Label txtISBN;

    @FXML
    private Label txtNotas;

    @FXML
    private Label txtTitulo;
    
    @FXML
    public void initialize() {
    	
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
    	Metodos.cambiarEscena(event, "/view/FrameLibroData.fxml", "Home");
    }

}
