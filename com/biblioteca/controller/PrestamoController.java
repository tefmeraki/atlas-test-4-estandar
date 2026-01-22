package com.biblioteca.controller;

import com.biblioteca.model.Libro;
import com.biblioteca.model.Prestamo;
import com.biblioteca.model.Socio;
import com.biblioteca.service.LibroService;
import com.biblioteca.service.PrestamoService;
import com.biblioteca.service.SocioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/prestamos")
public class PrestamoController extends HttpServlet {

    private final PrestamoService prestamoService = new PrestamoService();
    private final SocioService socioService = new SocioService();
    private final LibroService libroService = new LibroService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // ✅ NUEVO: cargar socios y libros para los <select> del formulario
            List<Socio> socios = socioService.listar();
            List<Libro> libros = libroService.listar();
            req.setAttribute("socios", socios);
            req.setAttribute("libros", libros);

            // Listado de préstamos
            List<Prestamo> prestamos = prestamoService.listar();
            req.setAttribute("prestamos", prestamos);

            // Fecha de hoy (por si quieres usarla en JSP, aunque ya calculamos allí)
            req.setAttribute("hoy", LocalDate.now());

        } catch (SQLException e) {
            req.setAttribute("error", "Error al cargar préstamos/socios/libros: " + e.getMessage());
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

                prestamoService.crearPrestamo(socioId, libroId, fp, fv);

            } else if ("devolver".equals(action)) {
                int prestamoId = Integer.parseInt(req.getParameter("prestamoId"));
                prestamoService.devolverPrestamo(prestamoId);
            } else {
                req.getSession().setAttribute("flash", "Acción no reconocida");
            }

        } catch (Exception e) {
            // MVP: mensaje simple y redirección
            req.getSession().setAttribute("flash", e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/prestamos");
    }
}
