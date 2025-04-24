package Api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.Libro;

/**
 * cambiar lo de url que esta deprecated
 */
public class ApiMetodos {

    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = "AIzaSyCYR3bBaBBWHk-GqWY_hR2R-IqY6CUPA6Q";

    /**
     * metodo que se encarga de las busquedas en funcion de parametros
     * @param busqueda
     * @param filtro1
     * @param filtro2
     * @return
     */
    public static ArrayList<Libro> searchLibros(String busqueda, String filtro1, String filtro2) {
        ArrayList<Libro> libros = new ArrayList<>();

        try {
            String urlString = API_URL + filtro2 + ":" + busqueda + "&maxResults=36&key=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            JsonObject jsonResponse = JsonParser.parseString(content.toString()).getAsJsonObject();
            JsonArray items = jsonResponse.getAsJsonArray("items");

            if (items != null) {
                for (JsonElement item : items) {
                    JsonObject itemObj = item.getAsJsonObject();
                    JsonObject volumeInfo = itemObj.getAsJsonObject("volumeInfo");

                    String titulo = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "";
                    String autores = "";
                    if (volumeInfo.has("authors")) {
                        JsonArray autoresArray = volumeInfo.getAsJsonArray("authors");
                        autores = autoresArray.size() > 0 ? autoresArray.get(0).getAsString() : "";
                    }
                    String fechaPublicacion = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : "";
                    String isbn = "";
                    if (volumeInfo.has("industryIdentifiers")) {
                        JsonArray identifiers = volumeInfo.getAsJsonArray("industryIdentifiers");
                        for (JsonElement identifier : identifiers) {
                            JsonObject idObj = identifier.getAsJsonObject();
                            if (idObj.has("identifier")) {
                                isbn = idObj.get("identifier").getAsString();
                                break;
                            }
                        }
                    }
                    String link = volumeInfo.has("infoLink") ? volumeInfo.get("infoLink").getAsString() : "";

                    String imagenPeque = "";
                    String imagenGrande = "";
                    if (volumeInfo.has("imageLinks")) {
                        JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                        imagenPeque = imageLinks.has("smallThumbnail") ? imageLinks.get("smallThumbnail").getAsString() : "";
                        imagenGrande = imageLinks.has("thumbnail") ? imageLinks.get("thumbnail").getAsString() : "";
                    }

                    Libro libro = new Libro(0, titulo, link, autores, fechaPublicacion, isbn);
                    libro.setImagenPeque(imagenPeque);
                    libro.setImageGrande(imagenGrande);

                    libros.add(libro);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return libros;
    }
}