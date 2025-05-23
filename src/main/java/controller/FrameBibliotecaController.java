package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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


    @FXML
    private Button btnAdelante;

    @FXML
    private Button btnAtras;

    @FXML
    private ImageView btnBuscador;

    @FXML
    private Label btnComprados;

    @FXML
    private Label btnHanPrestado;

    @FXML
    private Label btnHePrestado;

    @FXML
    private ImageView btnHome;

    @FXML
    private Label btnLeidos;

    @FXML
    private ImageView btnLogOff;

    @FXML
    private Label btnTodo;

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
    private Label txtPaginacion;


    private ArrayList<Libro> librosTotales = new ArrayList<>();
    private int paginaActual = 1;
    private final int librosPorPagina = 12;

    // Imagen por defecto
    private final Image imagenDefault = new Image(
        getClass().getResourceAsStream("/images/libro.png")
    );

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

        // Limpiar
        for (int i = 0; i < 12; i++) {
            labels[i].setText("");
            imageViews[i].setImage(null);
            imageViews[i].setOnMouseClicked(null);
        }

        for (int i = 0; i < libros.size(); i++) {
            Libro libro = libros.get(i);
            labels[i].setText(libro.getNombre());

        
            String ruta = libro.getImagenPeque();
            Image img = imagenDefault;
            try {
                if (ruta != null && !ruta.isBlank()) {
                    if (ruta.startsWith("http://") || ruta.startsWith("https://")) {
                        img = new Image(ruta, true);
                        if (img.isError()) throw new Exception("HTTP error");
                    } else if (ruta.startsWith("/images/")) {
                        img = new Image(getClass().getResourceAsStream(ruta));
                    } else {
                        img = new Image("file:" + ruta);
                        if (img.isError()) throw new Exception("File error");
                    }
                }
            } catch (Exception e) {
                System.err.println("Excepción al cargar imagen '" + ruta + "': " + e.getMessage());
                img = imagenDefault;
            }
            imageViews[i].setImage(img);

            final Libro seleccionado = libro;
            imageViews[i].setOnMouseClicked(event -> {
                Metodos.setLibroSeleccionado(seleccionado);
                Metodos.cambiarEscena(event, "/view/FrameBibliotecaLibroData.fxml", "Detalles del libro");
            });
        }
    }

    @FXML void adelante(ActionEvent event) {
        int total = (librosTotales.size() + librosPorPagina - 1) / librosPorPagina;
        if (paginaActual < total) {
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
    
    @FXML
    void filtrarHePrestado(MouseEvent event) {
        librosTotales = LibroDAO.obtenerLibrosPrestadosPorMi(UsuarioIniciado.getUsuario().getId());
        paginaActual = 1;
        actualizarPaginacion();
        
    }

    @FXML
    void filtrarPrestado(MouseEvent event) {
        librosTotales = LibroDAO.obtenerLibrosPrestadosPorMi(UsuarioIniciado.getUsuario().getId());
        paginaActual = 1;
        actualizarPaginacion();
    }
    @FXML
    void filtrarMeHanPrestado(MouseEvent event) {

    }
    

    @FXML
    void filtrarMehanPrestado(MouseEvent event) {
        librosTotales = LibroDAO.obtenerLibrosQueMeHanPrestado(UsuarioIniciado.getUsuario().getId());
        paginaActual = 1;
        actualizarPaginacion();
    }

    @FXML void cerrarSesion(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameLoggin.fxml", "Registro");
    }

    @FXML void volverHome(MouseEvent event) {
        Metodos.cambiarEscena(event, "/view/FrameHome.fxml", "Inicio");
    }

    @FXML void btnAdelanteEntrar(MouseEvent event) { Metodos.cambiarColorBotonEntrada(btnAdelante); }
    @FXML void btnAdelanteSalir(MouseEvent event)  { Metodos.cambiarColorBotonSalir(btnAdelante); }
    @FXML void btnAtrasEntrar(MouseEvent event)    { Metodos.cambiarColorBotonEntrada(btnAtras);   }
    @FXML void btnAtrasSalir(MouseEvent event)     { Metodos.cambiarColorBotonSalir(btnAtras);    }

    @FXML void buscar(KeyEvent event)   {  }
    @FXML void buscarClick(MouseEvent event) {  }
    @FXML void clickDetails(MouseEvent event) {  }
    @FXML void clickPuzles(MouseEvent event)  { }
    @FXML void ventanaUsuario(MouseEvent event)  {  }
}
