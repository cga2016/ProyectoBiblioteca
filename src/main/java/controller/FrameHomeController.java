package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.Metodos;
import models.UsuarioIniciado;

public class FrameHomeController {
	
    @FXML
    private Button btnAdelante;

    @FXML
    private Button btnAtras;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView10;

    @FXML
    private ImageView imageView11;

    @FXML
    private ImageView imageView12;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;

    @FXML
    private ImageView imageView5;

    @FXML
    private ImageView imageView6;

    @FXML
    private ImageView imageView7;

    @FXML
    private ImageView imageView8;

    @FXML
    private ImageView imageView9;

    @FXML
    private Label labelUsuario;

    @FXML
    private TextField searchField;

    @FXML
    private Label tituloJuego1;

    @FXML
    private Label tituloJuego10;

    @FXML
    private Label tituloJuego11;

    @FXML
    private Label tituloJuego12;

    @FXML
    private Label tituloJuego2;

    @FXML
    private Label tituloJuego3;

    @FXML
    private Label tituloJuego4;

    @FXML
    private Label tituloJuego5;

    @FXML
    private Label tituloJuego6;

    @FXML
    private Label tituloJuego7;

    @FXML
    private Label tituloJuego8;

    @FXML
    private Label tituloJuego9;
    

    @FXML
    void btnAdelanteEntrar(MouseEvent event) {
    	Metodos.cambiarColorBotonEntrada(btnAdelante);
    }

    @FXML
    void btnAdelanteSalir(MouseEvent event) {
    	Metodos.cambiarColorBotonSalir(btnAdelante);
    }

    @FXML
    void btnAtrasEntrar(MouseEvent event) {
    	Metodos.cambiarColorBotonEntrada(btnAtras);
    }

    @FXML
    void btnAtrasSalir(MouseEvent event) {
    	Metodos.cambiarColorBotonSalir(btnAtras);
    }
    
    @FXML
    public void initialize() {
    	labelUsuario.setText(UsuarioIniciado.getUsuario().getNickname());
    }
    

    @FXML
    void ClickFPS(MouseEvent event) {

    }

    @FXML
    void ClickTerror(MouseEvent event) {

    }

    @FXML
    void buscar(KeyEvent event) {

    }
    
    @FXML
    void buscarClick(MouseEvent event) {

    }


    @FXML
    void clickDetails(MouseEvent event) {

    }

    @FXML
    void clickPuzles(MouseEvent event) {

    }

    @FXML
    void ventanaUsuario(MouseEvent event) {

    }

}

