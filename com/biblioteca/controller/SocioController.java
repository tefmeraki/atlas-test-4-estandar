package com.biblioteca.controller;

import com.biblioteca.model.Socio;
import com.biblioteca.service.SocioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/socios")
public class SocioController extends HttpServlet {
    private final SocioService service = new SocioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Socio> socios = service.listar();
            req.setAttribute("socios", socios);
        } catch (SQLException e) {
            req.setAttribute("error", "Error al listar socios: " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/socios.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");
        try {
            service.crearSocio(nombre, email);
        } catch (Exception e) {
            req.getSession().setAttribute("flash", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/socios");
    }
}