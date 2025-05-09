package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

import dao.LibroDAO;
import models.Libro;
import models.Metodos;
import models.UsuarioIniciado;

import java.util.ArrayList;

public class FrameBibliotecaController {

    @FXML private Button btnAdelante;
    @FXML private Button btnAtras;
    @FXML private ImageView btnBuscador;
    @FXML private Label btnComprados;
    @FXML private ImageView btnHome;
    @FXML private Label btnLeidos;
    @FXML private ImageView btnLogOff;
    @FXML private Label btnPrestados;
    @FXML private Label btnTodo;
    @FXML private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6,
                            imageView7, imageView8, imageView9, imageView10, imageView11, imageView12;
    @FXML private Label labelUsuario;
    @FXML private TextField searchField;
    @FXML private Label tituloJuego1, tituloJuego2, tituloJuego3, tituloJuego4, tituloJuego5, tituloJuego6,
                        tituloJuego7, tituloJuego8, tituloJuego9, tituloJuego10, tituloJuego11, tituloJuego12;
    @FXML private Label txtPaginacion;

    private ArrayList<Libro> librosTotales = new ArrayList<>();
    private int paginaActual = 1;
    private final int librosPorPagina = 12;

    @FXML
    public void initialize() {
        labelUsuario.setText(UsuarioIniciado.getUsuario().getNickname());
        cargarLibrosDesdeBD();
        actualizarPaginacion();
    }

    private void cargarLibrosDesdeBD() {
        librosTotales = LibroDAO.obtenerLibrosDeBibliotecaDelUsuario();
    }

    private void actualizarPaginacion() {
        int desde = (paginaActual - 1) * librosPorPagina;
        int hasta = Math.min(desde + librosPorPagina, librosTotales.size());
        ArrayList<Libro> librosPagina = new ArrayList<>(librosTotales.subList(desde, hasta));
        mostrarLibros(librosPagina);
        txtPaginacion.setText(paginaActual + "/" + ((librosTotales.size() + librosPorPagina - 1) / librosPorPagina));
    }

    private void mostrarLibros(ArrayList<Libro> libros) {
        ImageView[] imageViews = {
            imageView1, imageView2, imageView3, imageView4, imageView5, imageView6,
            imageView7, imageView8, imageView9, imageView10, imageView11, imageView12
        };

        Label[] labels = {
            tituloJuego1, tituloJuego2, tituloJuego3, tituloJuego4, tituloJuego5, tituloJuego6,
            tituloJuego7, tituloJuego8, tituloJuego9, tituloJuego10, tituloJuego11, tituloJuego12
        };

        for (int i = 0; i < 12; i++) {
            labels[i].setText("");
            imageViews[i].setImage(null);
            imageViews[i].setOnMouseClicked(null);
        }

        for (int i = 0; i < libros.size(); i++) {
            Libro libro = libros.get(i);
            labels[i].setText(libro.getNombre());
            try {
                Image imagen = new Image(libro.getImagenPeque(), true);
                imageViews[i].setImage(imagen.isError() ? new Image("/images/libro.png") : imagen);
            } catch (Exception e) {
                imageViews[i].setImage(new Image("/images/libro.png"));
            }

            final Libro libroSeleccionado = libro;
            imageViews[i].setOnMouseClicked(event -> {
                Metodos.setLibroSeleccionado(libroSeleccionado);
                Metodos.cambiarEscena(event, "/view/FrameBibliotecaLibroData.fxml", "Detalles del libro");
            });
        }
    }

    @FXML void adelante(ActionEvent event) {
        int totalPaginas = (librosTotales.size() + librosPorPagina - 1) / librosPorPagina;
        if (paginaActual < totalPaginas) {
            paginaActual++;
            actualizarPaginacion();
        }
    }

    @FXML void atras(ActionEvent event) {
        if (paginaActual > 1) {
            paginaActual--;
            actualizarPaginacion();
        }
    }

    @FXML void filtrarTodos(MouseEvent event) {
        librosTotales = LibroDAO.obtenerLibrosDeBibliotecaDelUsuario();
        paginaActual = 1;
        actualizarPaginacion();
    }

    @FXML void filtrarComprados(MouseEvent event) {
        librosTotales = LibroDAO.obtenerLibrosComprados(UsuarioIniciado.getUsuario().getId());
        paginaActual = 1;
        actualizarPaginacion();
    }

    @FXML void filtrarLeido(MouseEvent event) {
        librosTotales = LibroDAO.obtenerLibrosLeidos(UsuarioIniciado.getUsuario().getId());
        paginaActual = 1;
        actualizarPaginacion();
    }

    @FXML void cerrarSesion(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameLoggin.fxml", "Registro");
    }

    @FXML void volverHome(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameHome.fxml", "Inicio");
    }

    @FXML void btnAdelanteEntrar(MouseEvent event) {
    	Metodos.cambiarColorBotonEntrada(btnAdelante);
    	}
    @FXML void btnAdelanteSalir(MouseEvent event) { 
    	Metodos.cambiarColorBotonSalir(btnAdelante); 
    	}
    @FXML void btnAtrasEntrar(MouseEvent event) {
    	
    	Metodos.cambiarColorBotonEntrada(btnAtras); 
    	}
    @FXML void btnAtrasSalir(MouseEvent event) {
    	Metodos.cambiarColorBotonSalir(btnAtras); 
    	}

    @FXML void buscar(KeyEvent event) {
    	
    }
    @FXML void buscarClick(MouseEvent event) {
    	
    }
    @FXML void clickDetails(MouseEvent event) {
    	
    }
    @FXML void clickPuzles(MouseEvent event) {
    	
    }
    @FXML void filtrarPrestado(MouseEvent event) {
    	
    }
    @FXML void ventanaUsuario(MouseEvent event) {
    	
    }
}

