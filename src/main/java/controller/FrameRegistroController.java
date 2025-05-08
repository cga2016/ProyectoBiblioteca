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
import models.ListaUsuarios;
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
    private String genero = "";

    
    

    @FXML
    void btnRegistrarEntrar(MouseEvent event) {
    	Metodos.cambiarColorBotonEntrada(btnRegistrar);
    }

    @FXML
    void btnRegistrarSalir(MouseEvent event) {
    	Metodos.cambiarColorBotonSalir(btnRegistrar);
    }

    @FXML
    void btnVolverEntrar(MouseEvent event) {
    	Metodos.cambiarColorBotonEntrada(btnVolver);
    }

    @FXML
    void btnVolverSalir(MouseEvent event) {
    	Metodos.cambiarColorBotonSalir(btnVolver);
    }
    @FXML
    /**
     * metodo inicial que rellena los combo box
     */
    //corregir las tildes de ficcion y sustituir ciencia+ficcion por Ciencia ficcion
    public void initialize() {
        chboxFavoritos.getItems().addAll("ficción", "Ciencia ficcion", "terror", "romance", "cocina");
        chboxFavoritos.setValue("ficcion"); 
        
    }
    
    private Usuario user;
    


    /**
     * 
     * @param event
     */
    //corregir que no se puedan crear usuarios repetidos
    @FXML
    void Registrar(ActionEvent event) {
    	if(chboxFavoritos.getValue().equals("Ciencia ficcion")){
    		genero = "ciencia+ficcion";
    	} else  if(chboxFavoritos.getValue().equals("ficción")) {
    		genero = "ficción";
    	} else {
    		genero = (String) chboxFavoritos.getValue();
    	}
    	
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
            
            if (txtContrasena.getText().length() <= 5) {
                Metodos.mostrarMensajeError("Las contraseñas no pueden ser mas cortas que 5 dijitos.");
                return;
            }
            

            if (!txtCorreo.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                Metodos.mostrarMensajeError("El correo electrónico no es válido.");
                return;
            }
            	user = new Usuario(txtNombre.getText(), txtApellidos.getText(), genero,
    			txtContrasena.getText(), txtCorreo.getText(), txtNickname.getText());
    	
		    if (ListaUsuarios.getUsuariosRegistrados().contains(user)) {
		        Metodos.mostrarMensajeError("Usuario ya existente en el sistema");
		        return;
		    }
    	DaoUsuarios.addUser(user);
    	ListaUsuarios.addUsuario(user);
    	Metodos.mostrarMensajeConfirmacion("Usuario creado con exito");
    }

    @FXML
    void Volver(ActionEvent event) {
    	Metodos.cambiarEscena(event, "/view/FrameLoggin.fxml", "Registro");
    }

}


