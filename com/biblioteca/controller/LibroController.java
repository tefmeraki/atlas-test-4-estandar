package com.biblioteca.controller;

import com.biblioteca.model.Libro;
import com.biblioteca.service.LibroService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/libros")
public class LibroController extends HttpServlet {
    private final LibroService service = new LibroService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Libro> libros = service.listar();
            req.setAttribute("libros", libros);
        } catch (SQLException e) {
            req.setAttribute("error", "Error al listar libros: " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/libros.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String titulo = req.getParameter("titulo");
        String autor = req.getParameter("autor");
        String isbn = req.getParameter("isbn");
        try {
            service.crearLibro(titulo, autor, isbn);
        } catch (Exception e) {
            req.getSession().setAttribute("flash", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/libros");
    }
}