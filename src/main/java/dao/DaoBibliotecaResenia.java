package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connection.Conexion;
import models.Biblioteca;
import models.Libro;
import models.Resenia;
import models.Metodos;

public class DaoBibliotecaResenia {

    public static void guardarBiblioteca(Biblioteca b) {
        String sql = "INSERT INTO Biblioteca (idUsuario, isbnLibro, enBiblioteca, comprado, leido, prestado) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, b.getIdUsuario());
            ps.setString(2, b.getIsbnLibro());
            ps.setBoolean(3, b.isEnBiblioteca());
            ps.setBoolean(4, b.isComprado());
            ps.setBoolean(5, b.isLeido());
            ps.setBoolean(6, b.isPrestado());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            Metodos.mostrarMensajeError("Error al guardar en Biblioteca");
        }
    }

    public static void guardarResenia(Resenia r) {
        String sql = "INSERT INTO Resenia (comentario, nota, idUsuario, isbnLibro, fechaLeido) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getComentario());
            ps.setDouble(2, r.getNota());
            ps.setInt(3, r.getIdUsuario());
            ps.setString(4, r.getIsbnLibro());
            ps.setDate(5, r.getFechaLeido());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            Metodos.mostrarMensajeError("Error al guardar la reseña");
        }
    }
    
    public static void guardarLibro(Libro libro) {
        String sql = "INSERT INTO Libro (iSBN, nombre, linkPropio, autor, fechaPublicacion, imagenPeque, imagenGrande, prestado, idUsuarioPrestamo, fechaPrestamo, estadoPrestamo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setString(1, libro.getiSBN()); 
            ps.setString(2, libro.getNombre()); 
            ps.setString(3, libro.getLinkPropio()); 
            ps.setString(4, libro.getAutor()); 
 
            ps.setString(5, libro.getFechaPublicacion());
            ps.setString(6, libro.getImagenPeque()); 
            ps.setString(7, libro.getImageGrande()); 
            ps.setBoolean(8, libro.isPrestado()); 
            ps.setInt(9, libro.getIdUsuarioPrestamo()); 
            ps.setString(10, libro.getFechaPrestamo()); 
            ps.setBoolean(11, libro.isEstadoPrestamo()); 


            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            Metodos.mostrarMensajeError("Error al guardar el libro");
        }
    }
} 
