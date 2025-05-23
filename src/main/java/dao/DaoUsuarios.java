package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import connection.Conexion;
import models.Metodos;
import models.Usuario;
/**
 * Clase que gestiona los datos de usurios con la bd
 */
public class DaoUsuarios {
	public static void addUser(Usuario user) {
	try {
	    Connection connection = Conexion.conectar();

	    String insertQuery = "INSERT INTO Usuario (  nombre, Apellidos, generoFavorito, contrasena, correo, nickname) " +
	                         "VALUES (?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

	    	
	        insertStatement.setString(1, user.getNombre());
	        insertStatement.setString(2, user.getApellidos());
	        insertStatement.setString(3, user.getGeneroFavorito()); 
	        insertStatement.setString(4, user.getContrasena());
	        insertStatement.setString(5, user.getCorreo());
	        insertStatement.setString(6, user.getNickname());
	        


	        insertStatement.executeUpdate();
		 } catch (SQLIntegrityConstraintViolationException f) {
			 Metodos.mostrarMensajeError("Error, usuario ya existente con ese email");
			 
		 }
    } catch (SQLException e) {
        e.printStackTrace(); 
    }
	    


	}
	
	public static ArrayList<Usuario> loadUsers() {
	    Connection connection = Conexion.conectar();
	    ArrayList<Usuario> usuarios = new ArrayList<>();

	    String selectQuery = "SELECT id, nombre, apellidos, generoFavorito, contrasena, correo, nickname FROM Usuario";

	    try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
	         ResultSet resultSet = selectStatement.executeQuery()) {

	        while (resultSet.next()) {
	   
	            Usuario user = new Usuario(
	                resultSet.getString("nombre"),
	                resultSet.getString("apellidos"),
	                resultSet.getString("generoFavorito"),
	                resultSet.getString("contrasena"),
	                resultSet.getString("correo"),
	                resultSet.getString("nickname")
	            );
	            user.setId(resultSet.getInt("id")); 
	            usuarios.add(user);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }

	    return usuarios; 
	}


}