package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.ListaUsuarios;
import models.Metodos;
import models.Usuario;
import models.UsuarioIniciado;
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
    	if (ListaUsuarios.checkUsuarioEmail(txtGmail.getText())) {
    		Usuario user1 = ListaUsuarios.searchUsser(txtGmail.getText());
    		if(user1.getContrasena().equals(txtContrasena.getText())) {
	    		Metodos.mostrarMensajeConfirmacion("Usuario encontrado");
	    		Metodos.cambiarEscena(event, "/view/FrameHome.fxml", "Home");
	    		UsuarioIniciado.setUsuario(ListaUsuarios.searchUsser(txtGmail.getText()));
    		} else {
    			Metodos.mostrarMensajeConfirmacion("Contrseña incorrecta");
    		}
    		
    	} else {
    		Metodos.mostrarMensajeError("Usuario no encontrado");
    	}
    }

}