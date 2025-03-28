package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.Metodos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class FrameLogginController {

    @FXML
    private Button btnLoggin;

    @FXML
    private Label btnRegistro;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private TextField txtGmail;
    

    @FXML
    void IrARegistro(MouseEvent event) {
    	Metodos.cambiarEscena(event, "/view/FrameRegistro.fxml", "Registro");
    }

    @FXML
    void iniciarSesion(ActionEvent event) {

    }

}