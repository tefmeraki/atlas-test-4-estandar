package com.biblioteca.dao;

import com.biblioteca.model.Prestamo;
import com.biblioteca.util.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDao {

    public boolean existsActiveLoanForBook(int libroId) throws SQLException {
        String sql = "SELECT COUNT(*) AS c FROM prestamo WHERE libro_id = ? AND fecha_devolucion IS NULL";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, libroId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("c") > 0;
            }
        }
    }

    public void insert(Prestamo p) throws SQLException {
        String sql = "INSERT INTO prestamo (socio_id, libro_id, fecha_prestamo, fecha_vencimiento, fecha_devolucion) VALUES (?, ?, ?, ?, NULL)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getSocioId());
            ps.setInt(2, p.getLibroId());
            ps.setDate(3, Date.valueOf(p.getFechaPrestamo()));
            ps.setDate(4, Date.valueOf(p.getFechaVencimiento()));
            ps.executeUpdate();
        }
    }

    public void markReturned(int prestamoId, LocalDate fechaDevolucion) throws SQLException {
        String sql = "UPDATE prestamo SET fecha_devolucion = ? WHERE id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fechaDevolucion));
            ps.setInt(2, prestamoId);
            ps.executeUpdate();
        }
    }

    public List<Prestamo> findAll() throws SQLException {
        String sql = "SELECT id, socio_id, libro_id, fecha_prestamo, fecha_vencimiento, fecha_devolucion FROM prestamo ORDER BY id DESC";
        List<Prestamo> prestamos = new ArrayList<>();
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Date fd = rs.getDate("fecha_devolucion");
                prestamos.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("socio_id"),
                        rs.getInt("libro_id"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        rs.getDate("fecha_vencimiento").toLocalDate(),
                        fd == null ? null : fd.toLocalDate()
                ));
            }
        }
        return prestamos;
    }
}