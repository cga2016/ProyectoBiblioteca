package Api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.Libro;

public class ApiMetodos {
	
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = "AIzaSyCYR3bBaBBWHk-GqWY_hR2R-IqY6CUPA6Q"; 
	
	public static ArrayList<Libro> searchLibros(String busqueda, String filtro1, String filtro2){
        try {
			
		        String urlString = API_URL +filtro2 + ":" +  busqueda + "+&key=" + API_KEY;
		        URL url = new URL(urlString);
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setRequestMethod("GET");
		        
		        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        
               /* JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonArray results = json.getAsJsonArray("results");*/
		        String inputLine;
		        StringBuilder content = new StringBuilder();
		        while ((inputLine = in.readLine()) != null) {
		            content.append(inputLine);
		            //System.out.println(urlString);
		            System.out.println(inputLine);
		            System.out.println();
		            
		            in.close();
		            connection.disconnect();
		        }
		        

			} catch (IOException e) {
				e.printStackTrace();
			}
	    
		return null;
	}
	
}
