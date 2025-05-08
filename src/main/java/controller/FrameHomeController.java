package controller;

import java.net.URISyntaxException;
import java.util.ArrayList;

import Api.ApiMetodos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.Libro;
import models.Metodos;
import models.UsuarioIniciado;

public class FrameHomeController {
	
	
    @FXML
    private ComboBox<String> chBoxTipoDeBusqueda;

    @FXML
    private ComboBox<String>  chboxFiltro;
    
    
    @FXML
    private ImageView btnLogOff;

	
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
    private Label txtPaginacion;

    private ArrayList<Libro> librosTotales = new ArrayList<>();
    private int paginaActual = 1;
    private final int librosPorPagina = 12;
    private final int totalPaginas = 3;
    
    @FXML
    void adelante(ActionEvent event) {
        if (paginaActual < totalPaginas) {
            paginaActual++;
            actualizarPaginacion();
        }
    }

    @FXML
    void atras(ActionEvent event) {
        if (paginaActual > 1) {
            paginaActual--;
            actualizarPaginacion();
        }
    }
    
    @FXML
    void cambio1(InputMethodEvent event) {

    }
    
    private void cargarLibrosPorTipo(String tipo) throws URISyntaxException {
        switch (tipo.toLowerCase()) {
            case "favoritos":
                librosTotales = ApiMetodos.searchLibros(
                    UsuarioIniciado.getUsuario().getGeneroFavorito(), "", "subject");
                break;
            case "nuevo":
                librosTotales = searchNuevosLanzamientos(); 
                break;
            case "más populares":
                librosTotales = searchLibrosPopulares();
                break;
            default:
                return;
        }
        paginaActual = 1;
        actualizarPaginacion();
    }
    
    private void actualizarPaginacion() {
        int desde = (paginaActual - 1) * librosPorPagina;
        int hasta = Math.min(desde + librosPorPagina, librosTotales.size());

        ArrayList<Libro> librosPagina = new ArrayList<>(librosTotales.subList(desde, hasta));
        mostrarLibros(librosPagina);
        txtPaginacion.setText( paginaActual + "/" + totalPaginas);
    }
    

    @FXML
    void cerrarSesion(MouseEvent event) {
    	Metodos.cambiarEscena(event, "/view/FrameLoggin.fxml", "Registro");
    }	
    @FXML
    public void initialize() throws URISyntaxException {
        chBoxTipoDeBusqueda.getItems().addAll("favoritos", "nuevo", "más populares");
        chBoxTipoDeBusqueda.getSelectionModel().selectFirst();

        chboxFiltro.getItems().addAll("nombre", "autor", "editorial", "ISBN");
        chboxFiltro.getSelectionModel().selectFirst();

        labelUsuario.setText(UsuarioIniciado.getUsuario().getNickname());

        cargarLibrosPorTipo("favoritos");

        chBoxTipoDeBusqueda.setOnAction(event -> {
            String tipoSeleccionado = chBoxTipoDeBusqueda.getSelectionModel().getSelectedItem();
            try {
				cargarLibrosPorTipo(tipoSeleccionado);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
    }
    


    private void mostrarLibros(ArrayList<Libro> libros) {
        ImageView[] imageViews = {
            imageView1, imageView2, imageView3, imageView4,
            imageView5, imageView6, imageView7, imageView8,
            imageView9, imageView10, imageView11, imageView12
        };

        Label[] labels = {
            tituloJuego1, tituloJuego2, tituloJuego3, tituloJuego4,
            tituloJuego5, tituloJuego6, tituloJuego7, tituloJuego8,
            tituloJuego9, tituloJuego10, tituloJuego11, tituloJuego12
        };

        for (int i = 0; i < 12; i++) {
            labels[i].setText("");
            imageViews[i].setImage(null);
        }

        /**
         * algunas imagenes no traen portada, en ese caso se le añade una 
         */
        for (int i = 0; i < Math.min(12, libros.size()); i++) {
            Libro libro = libros.get(i);
            labels[i].setText(libro.getNombre());

            try {
                String imagenURL = libro.getImagenPeque();
                if (imagenURL != null && !imagenURL.isEmpty()) {
                    Image imagen = new Image(imagenURL, true);
                    if (imagen.isError()) {
                        imageViews[i].setImage(new Image("/images/libro.png")); 
                    } else {
                        imageViews[i].setImage(imagen);
                    }
                } else {
                    imageViews[i].setImage(new Image("/images/libro.png")); 
                }
            } catch (Exception e) {
                imageViews[i].setImage(new Image("/images/libro.png")); 
            }

        }
    }
    

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
    void buscar(KeyEvent event) {
       /* String textoBusqueda = searchField.getText().trim();

        if (!textoBusqueda.isEmpty()) {
            ArrayList<Libro> librosEncontrados = ApiMetodos.searchLibros(textoBusqueda, "", "intitle");
            mostrarLibros(librosEncontrados);
        } else {
            System.out.println("Campo de búsqueda vacío.");
        }*/
    }
    
    @FXML
    void buscarClick(MouseEvent event) throws URISyntaxException {
    	buscar();
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
    
    private ArrayList<Libro> searchNuevosLanzamientos() throws URISyntaxException {

        return ApiMetodos.searchLibros("2024", "", "inpublisher");
    }

    private ArrayList<Libro> searchLibrosPopulares() throws URISyntaxException {
   
        return ApiMetodos.searchLibros("bestseller", "", "subject");
    }
    
    private void buscar() throws URISyntaxException {
        String textoBusqueda = searchField.getText().trim();

        if (!textoBusqueda.isEmpty()) {
            String filtroSeleccionado = chboxFiltro.getSelectionModel().getSelectedItem().toLowerCase();
            String tipoBusqueda;

            switch (filtroSeleccionado) {
                case "nombre":
                    tipoBusqueda = "intitle";
                    break;
                case "autor":
                    tipoBusqueda = "inauthor";
                    break;
                case "editorial":
                    tipoBusqueda = "inpublisher";
                    break;
                case "isbn":
                    tipoBusqueda = "isbn";
                    break;
                default:
                    tipoBusqueda = "intitle"; 
            }

            ArrayList<Libro> librosEncontrados = ApiMetodos.searchLibros(textoBusqueda, "", tipoBusqueda);
            librosTotales = ApiMetodos.searchLibros(textoBusqueda, "", tipoBusqueda);
            paginaActual = 1;
            actualizarPaginacion();
        } else {
        	Metodos.mostrarMensajeError("Error: se dejo el campo de busqueda vacio");
        }
    	
    }
    
    
}

