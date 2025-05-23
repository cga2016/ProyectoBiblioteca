package main;

import java.util.ArrayList;

import Api.ApiMetodos;
import controller.FrameLogginController;
import dao.DaoUsuarios;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Libro;
import models.ListaUsuarios;
import models.Usuario;

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
            stage.setTitle("Inicio de Sesión");
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Cargar usuarios desde la base de datos
            ListaUsuarios.setUsuariosRegistrados(DaoUsuarios.loadUsers());




            ArrayList<Libro> lista = ApiMetodos.searchLibros("Terry", STYLESHEET_MODENA, "intitles");

        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(args);
    }
}

