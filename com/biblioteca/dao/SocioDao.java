package com.biblioteca.dao;

import com.biblioteca.model.Socio;
import com.biblioteca.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocioDao {

    public void insert(Socio socio) throws SQLException {
        String sql = "INSERT INTO socio (nombre, email) VALUES (?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getEmail());
            ps.executeUpdate();
        }
    }

    public List<Socio> findAll() throws SQLException {
        String sql = "SELECT id, nombre, email FROM socio ORDER BY id DESC";
        List<Socio> socios = new ArrayList<>();
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                socios.add(new Socio(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email")
                ));
            }
        }
        return socios;
    }
}