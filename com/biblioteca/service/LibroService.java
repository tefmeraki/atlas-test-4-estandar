package com.biblioteca.service;

import com.biblioteca.dao.LibroDao;
import com.biblioteca.model.Libro;

import java.sql.SQLException;
import java.util.List;

public class LibroService {
    private final LibroDao libroDao = new LibroDao();

    public void crearLibro(String titulo, String autor, String isbn) throws SQLException {
        if (titulo == null || titulo.isBlank() || autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("TÃ­tulo y autor son obligatorios");
        }
        libroDao.insert(new Libro(titulo.trim(), autor.trim(), isbn == null ? null : isbn.trim()));
    }

    public List<Libro> listar() throws SQLException {
        return libroDao.findAll();
    }
}