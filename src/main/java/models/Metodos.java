package models;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Metodos {

	
	public static void cambiarEscena(Object event, String fxmlFile, String nombreVentana) {
	    try {
	        FXMLLoader loader = new FXMLLoader(Metodos.class.getResource(fxmlFile));
	        Pane root = loader.load();

	        Stage stage;
	        if (event instanceof ActionEvent) {
	            stage = (Stage) ((Node) ((ActionEvent) event).getSource()).getScene().getWindow();
	        } else if (event instanceof MouseEvent) {
	            stage = (Stage) ((Node) ((MouseEvent) event).getSource()).getScene().getWindow();
	        } else {
	            System.err.println("Error: Tipo de evento no soportado");
	            return;
	        }

	        stage.setTitle(nombreVentana);
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    } catch (IOException e) {
	        System.err.println("Error al cargar el archivo FXML: " + e.getMessage());
	        e.printStackTrace();
	    } catch (NullPointerException e) {
	        System.err.println("Error de NullPointer: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	 public static void mostrarMensajeError(String mensaje) {
		    Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle("Error");
		    alert.setHeaderText(null);
		    alert.setContentText(mensaje);
		    alert.showAndWait();
		}
	 public static void mostrarMensajeConfirmacion(String mensaje) {
		    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		    alert.setTitle("Confirmado");
		    alert.setHeaderText(null);
		    alert.setContentText(mensaje);
		    alert.showAndWait();
		}

	 
	 public static boolean compararContrasenas(String contrasena1, String contrasena2) {
		 if(contrasena1.equals(contrasena2)) {
			 return true;
		 } else {
			 return false;
		 }
	 }
		
		
}
