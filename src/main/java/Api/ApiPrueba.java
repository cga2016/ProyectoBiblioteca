package Api;

import java.io.BufferedReader;


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class ApiPrueba {
	
	//private String urlGenero = "https://www.googleapis.com/books/v1/volumes?q=subject:ficcion&key=AIzaSyCYR3bBaBBWHk-GqWY_hR2R-IqY6CUPA6Q";
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = "AIzaSyCYR3bBaBBWHk-GqWY_hR2R-IqY6CUPA6Q"; 
    
    //ejemplo
    public static String searchBooksByName(String query) throws Exception {
        String urlString = API_URL + query + "+subject&key=" + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
            System.out.println(urlString);
            System.out.println(inputLine);
            System.out.println();
        }
        
        in.close();
        connection.disconnect();
        return content.toString();
    }
    /**
     * metodo Base que realiza la busqueda
     * @param condicion
     * @param query
     * @return
     * @throws Exception
     */
    public static String searchBooks(String condicion, String query) throws Exception {
        String urlString = API_URL +condicion + ":" +  query + "+&key=" + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        //  System.out.println(urlString);
            System.out.println(inputLine);
            System.out.println();
        }
        
        in.close();
        connection.disconnect();
        return content.toString();
    }
}