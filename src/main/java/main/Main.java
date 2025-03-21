package main;

import Api.ApiPrueba;
import controller.FrameLogginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {
	private static FrameLogginController controlador;
	@Override
	public void start(Stage primaryStage) {	
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FrameLoggin.fxml"));
			Parent root = loader.load();
			
			controlador = loader.getController();
						
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		try {
			System.out.print(ApiPrueba.searchBooksByName("Terry"));
		} catch (Exception e) {
		
			e.printStackTrace();
		};
		launch(args);

	}

}
