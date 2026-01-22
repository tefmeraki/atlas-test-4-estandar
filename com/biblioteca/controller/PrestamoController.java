package com.biblioteca.controller;

import com.biblioteca.model.Prestamo;
import com.biblioteca.service.PrestamoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/prestamos")
public class PrestamoController extends HttpServlet {
    private final PrestamoService service = new PrestamoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Prestamo> prestamos = service.listar();
            req.setAttribute("prestamos", prestamos);
            req.setAttribute("hoy", LocalDate.now());
        } catch (SQLException e) {
            req.setAttribute("error", "Error al listar prÃ©stamos: " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/jsp/prestamos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        try {
            if ("crear".equals(action)) {
                int socioId = Integer.parseInt(req.getParameter("socioId"));
                int libroId = Integer.parseInt(req.getParameter("libroId"));
                LocalDate fp = LocalDate.parse(req.getParameter("fechaPrestamo"));
                LocalDate fv = LocalDate.parse(req.getParameter("fechaVencimiento"));
                service.crearPrestamo(socioId, libroId, fp, fv);
            } else if ("devolver".equals(action)) {
                int prestamoId = Integer.parseInt(req.getParameter("prestamoId"));
                service.devolverPrestamo(prestamoId);
            }
        } catch (Exception e) {
            req.getSession().setAttribute("flash", e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/prestamos");
    }
}