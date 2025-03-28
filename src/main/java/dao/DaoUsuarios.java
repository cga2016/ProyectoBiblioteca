package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.Conexion;
import models.Usuario;

public class DaoUsuarios {
	public static void addUser(Usuario user) {
	    Connection connection = Conexion.conectar();

	    // Query de inserción en la tabla Usuarios, sin incluir el campo Id porque es autoincremental
	    String insertQuery = "INSERT INTO Usuario (Nombre, Apellidos, generoFavorito, contrasena, correo, nickname) " +
	                         "VALUES (?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

	        // Asignar los valores del objeto Usuario a la consulta
	        insertStatement.setString(1, user.getNombre());
	        insertStatement.setString(2, user.getApellidos());
	        insertStatement.setString(3, user.getGeneroFavorito()); 
	        insertStatement.setString(4, user.getContrasena());
	        insertStatement.setString(5, user.getCorreo());
	        insertStatement.setString(6, user.getNickname());

	        // Ejecutar la consulta
	        insertStatement.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace(); // Manejo básico de errores, puedes mejorarlo registrando logs o lanzando excepciones personalizadas
	    }

	}
	
	public static ArrayList<Usuario> loadUsers() {
	    Connection connection = Conexion.conectar();
	    ArrayList<Usuario> usuarios = new ArrayList<>();

	    // Query para obtener todos los usuarios
	    String selectQuery = "SELECT  Nombre, Apellidos, fecha_Nacimiento, Apodo, Email, Correo FROM Usuarios";

	    try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
	         ResultSet resultSet = selectStatement.executeQuery()) {

	        while (resultSet.next()) {
	            // Crear un objeto Usuario con los datos de cada fila
	            Usuario user = null;
	            
	            user.setNombre(resultSet.getString("Nombre"));
	            user.setApellidos(resultSet.getString("Apellidos"));
	            user.setGeneroFavorito(resultSet.getString("generoFavorito")); // Convertir a LocalDate
	            user.setNickname(resultSet.getString("contrasena"));
	            user.setCorreo(resultSet.getString("correo"));
	            user.setNickname(resultSet.getString("nickname"));
	            
	            // Agregar el usuario al ArrayList
	            usuarios.add(user);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace(); // Manejo básico de errores
	    }

	    return usuarios; // Devolver la lista de usuarios
	}

}