<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.biblioteca.model.Prestamo" %>
<%@ page import="com.biblioteca.model.Socio" %>
<%@ page import="com.biblioteca.model.Libro" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>PrÃ©stamos</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style.css">
</head>
<body>
  <div class="container">
    <%@ include file="_header.jspf" %>

    <h1>PrÃ©stamos</h1>

    <div class="grid">
      <div class="card">
        <h2>Crear prÃ©stamo</h2>

        <%
          List<Socio> socios = (List<Socio>) request.getAttribute("socios");
          List<Libro> libros = (List<Libro>) request.getAttribute("libros");
        %>

        <% if (socios == null || libros == null) { %>
          <p class="muted">
            Nota: faltan listas de socios/libros. El controller debe cargarlas para poder elegir.
          </p>
        <% } %>

        <form method="post" action="<%= request.getContextPath() %>/prestamos">
          <input type="hidden" name="action" value="crear">

          <label>Socio</label>
          <select name="socioId" required>
            <% if (socios != null) { for (Socio s : socios) { %>
              <option value="<%= s.getId() %>"><%= s.getId() %> - <%= s.getNombre() %></option>
            <% }} %>
          </select>

          <label>Libro</label>
          <select name="libroId" required>
            <% if (libros != null) { for (Libro l : libros) { %>
              <option value="<%= l.getId() %>"><%= l.getId() %> - <%= l.getTitulo() %></option>
            <% }} %>
          </select>

          <label>Fecha prÃ©stamo</label>
          <input type="date" name="fechaPrestamo" value="<%= LocalDate.now() %>" required>

          <label>Fecha vencimiento</label>
          <input type="date" name="fechaVencimiento" required>

          <button type="submit">Crear prÃ©stamo</button>
        </form>
      </div>

      <div class="card">
        <h2>Listado de prÃ©stamos</h2>

        <%
          List<Prestamo> prestamos = (List<Prestamo>) request.getAttribute("prestamos");
          LocalDate hoy = LocalDate.now();
        %>

        <% if (prestamos == null) { %>
          <p class="muted">No hay datos cargados.</p>
        <% } else if (prestamos.isEmpty()) { %>
          <p class="muted">No hay prÃ©stamos todavÃ­a.</p>
        <% } else { %>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Socio</th>
                <th>Libro</th>
                <th>F. prÃ©stamo</th>
                <th>F. vencimiento</th>
                <th>F. devoluciÃ³n</th>
                <th>Estado</th>
                <th>AcciÃ³n</th>
              </tr>
            </thead>
            <tbody>
              <% for (Prestamo p : prestamos) {
                   String estado;
                   if (!p.isActivo()) estado = "DEVUELTO";
                   else if (p.isRetrasado(hoy)) estado = "RETRASADO";
                   else estado = "ACTIVO";
              %>
                <tr>
                  <td><%= p.getId() %></td>
                  <td><%= p.getSocioId() %></td>
                  <td><%= p.getLibroId() %></td>
                  <td><%= p.getFechaPrestamo() %></td>
                  <td><%= p.getFechaVencimiento() %></td>
                  <td><%= p.getFechaDevolucion() == null ? "" : p.getFechaDevolucion() %></td>
                  <td><span class="badge <%= estado.toLowerCase() %>"><%= estado %></span></td>
                  <td>
                    <% if (p.isActivo()) { %>
                      <form method="post" action="<%= request.getContextPath() %>/prestamos" class="inline">
                        <input type="hidden" name="action" value="devolver">
                        <input type="hidden" name="prestamoId" value="<%= p.getId() %>">
                        <button type="submit" class="secondary">Devolver</button>
                      </form>
                    <% } else { %>
                      <span class="muted">â€”</span>
                    <% } %>
                  </td>
                </tr>
              <% } %>
            </tbody>
          </table>
        <% } %>
      </div>
    </div>
  </div>
</body>
</html>