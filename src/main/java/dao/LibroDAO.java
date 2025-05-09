package dao;

import connection.Conexion;
import models.Libro;
import models.UsuarioIniciado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LibroDAO {

    public static ArrayList<Libro> obtenerLibrosDeBibliotecaDelUsuario() {
        ArrayList<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.* FROM Libro l " +
                     "JOIN Biblioteca b ON l.iSBN = b.isbnLibro " +
                     "WHERE b.idUsuario = ? AND b.enBiblioteca = true";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, UsuarioIniciado.getUsuario().getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                libros.add(new Libro(rs)); // Constructor que construya desde ResultSet
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return libros;
    }

    public static ArrayList<Libro> obtenerLibrosComprados(int idUsuario) {
        ArrayList<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.* FROM Libro l " +
                     "JOIN Biblioteca b ON l.iSBN = b.isbnLibro " +
                     "WHERE b.idUsuario = ? AND b.comprado = true";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                libros.add(new Libro(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return libros;
    }

    public static ArrayList<Libro> obtenerLibrosLeidos(int idUsuario) {
        ArrayList<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.* FROM Libro l " +
                     "JOIN Biblioteca b ON l.iSBN = b.isbnLibro " +
                     "WHERE b.idUsuario = ? AND b.leido = true";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                libros.add(new Libro(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return libros;
    }
}

