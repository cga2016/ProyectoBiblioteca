package controller;

import javafx.scene.input.MouseEvent;

import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import dao.DaoUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Metodos;
import models.Usuario;



public class FrameRegistroController {


    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnVolver;
    @FXML
    private ChoiceBox<String> chboxFavoritos; 
    
    @FXML
    private TextField txtApellidos;

    @FXML
    private PasswordField txtConfirmarContrasena;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNickname;

    @FXML
    private TextField txtNombre;
    @FXML
    public void initialize() {
        // Llenar el ChoiceBox con las opciones
        chboxFavoritos.getItems().addAll("ficcion", "ciencia+ficcion", "terror", "romance", "cocina");
        chboxFavoritos.setValue("ficcion"); // Establecer un valor por defecto
    }
    
    private Usuario user;
    


    @FXML
    void Registrar(ActionEvent event) {
    	if (txtNombre.getText().isEmpty() || txtApellidos.getText().isEmpty() || 
                txtNickname.getText().isEmpty() || txtContrasena.getText().isEmpty() || txtConfirmarContrasena.getText().isEmpty() || 
                txtCorreo.getText().isEmpty()) {
                Metodos.mostrarMensajeError("Por favor, complete todos los campos.");
                return;
            }

            if (!txtContrasena.getText().equals(txtConfirmarContrasena.getText())) {
                Metodos.mostrarMensajeError("Las contraseñas no coinciden.");
                return;
            }

            if (!txtCorreo.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                Metodos.mostrarMensajeError("El correo electrónico no es válido.");
                return;
            }
    	user = new Usuario(txtNombre.getText(), txtApellidos.getText(),(String) chboxFavoritos.getValue(),
    			txtContrasena.getText(), txtContrasena.getText(), txtNickname.getText());
    	DaoUsuarios.addUser(user);
    	Metodos.mostrarMensajeConfirmacion("Usuario creado con exito");
    }

    @FXML
    void Volver(ActionEvent event) {
    	Metodos.cambiarEscena(event, "/view/FrameLoggin.fxml", "Registro");
    }

}


