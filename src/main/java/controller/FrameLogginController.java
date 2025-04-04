package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
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
    void btnIniciarEntrar(MouseEvent event) {
    	Metodos.cambiarColorBotonEntrada(btnLoggin);
    }

    @FXML
    void btnIniciarSalir(MouseEvent event) {
    	Metodos.cambiarColorBotonSalir(btnLoggin);
    }
    

    @FXML
    void IrARegistro(MouseEvent event) {
    	Metodos.cambiarEscena(event, "/view/FrameRegistro.fxml", "Registro");
    }

    @FXML
    void iniciarSesion(ActionEvent event) {
    	if (ListaUsuarios.checkUsuarioEmail(txtGmail.getText())) {
    		Usuario user1 = ListaUsuarios.searchUsser(txtGmail.getText());
    		if(user1.getContrasena().equals(txtContrasena.getText())) {
    			UsuarioIniciado.setUsuario(ListaUsuarios.searchUsser(txtGmail.getText()));
	    		Metodos.mostrarMensajeConfirmacion("Usuario encontrado");
	    		Metodos.cambiarEscena(event, "/view/FrameHome.fxml", "Home");
    		} else {
    			Metodos.mostrarMensajeConfirmacion("Contrseña incorrecta");
    		}
    		
    	} else {
    		Metodos.mostrarMensajeError("Usuario no encontrado");
    	}
    }

}