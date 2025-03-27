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

	
	public static void cambiarEscena(ActionEvent event, String fxmlFile, String nombreVentana) {
	    try {
	        FXMLLoader loader = new FXMLLoader(Metodos.class.getResource(fxmlFile));
	        
	        Pane root = loader.load();
	        
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

		
		
}
