package com.biblioteca.dao;

import com.biblioteca.model.Libro;
import com.biblioteca.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDao {

    public void insert(Libro libro) throws SQLException {
        String sql = "INSERT INTO libro (titulo, autor, isbn) VALUES (?, ?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.executeUpdate();
        }
    }

    public List<Libro> findAll() throws SQLException {
        String sql = "SELECT id, titulo, autor, isbn FROM libro ORDER BY id DESC";
        List<Libro> libros = new ArrayList<>();
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                libros.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn")
                ));
            }
        }
        return libros;
    }
}