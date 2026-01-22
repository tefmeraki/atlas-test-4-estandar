package com.biblioteca.service;

import com.biblioteca.dao.PrestamoDao;
import com.biblioteca.model.Prestamo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PrestamoService {
    private final PrestamoDao prestamoDao = new PrestamoDao();

    public void crearPrestamo(int socioId, int libroId, LocalDate fechaPrestamo, LocalDate fechaVencimiento) throws SQLException {
        if (socioId <= 0 || libroId <= 0) throw new IllegalArgumentException("Socio y libro son obligatorios");
        if (fechaPrestamo == null || fechaVencimiento == null) throw new IllegalArgumentException("Fechas obligatorias");
        if (fechaVencimiento.isBefore(fechaPrestamo)) throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior al prÃ©stamo");

        if (prestamoDao.existsActiveLoanForBook(libroId)) {
            throw new IllegalStateException("Libro ya prestado (prÃ©stamo activo)");
        }

        prestamoDao.insert(new Prestamo(socioId, libroId, fechaPrestamo, fechaVencimiento));
    }

    public void devolverPrestamo(int prestamoId) throws SQLException {
        if (prestamoId <= 0) throw new IllegalArgumentException("PrÃ©stamo invÃ¡lido");
        prestamoDao.markReturned(prestamoId, LocalDate.now());
    }

    public List<Prestamo> listar() throws SQLException {
        return prestamoDao.findAll();
    }
}