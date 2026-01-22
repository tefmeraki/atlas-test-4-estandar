package com.biblioteca.service;

import com.biblioteca.dao.SocioDao;
import com.biblioteca.model.Socio;

import java.sql.SQLException;
import java.util.List;

public class SocioService {
    private final SocioDao socioDao = new SocioDao();

    public void crearSocio(String nombre, String email) throws SQLException {
        if (nombre == null || nombre.isBlank() || email == null || email.isBlank()) {
            throw new IllegalArgumentException("Nombre y email son obligatorios");
        }
        socioDao.insert(new Socio(nombre.trim(), email.trim()));
    }

    public List<Socio> listar() throws SQLException {
        return socioDao.findAll();
    }
}