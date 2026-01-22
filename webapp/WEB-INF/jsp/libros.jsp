<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.biblioteca.model.Libro" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Libros</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style.css">
</head>
<body>
  <div class="container">
    <%@ include file="_header.jspf" %>

    <h1>Libros</h1>

    <div class="grid">
      <div class="card">
        <h2>Alta de libro</h2>
        <form method="post" action="<%= request.getContextPath() %>/libros">
          <label>TÃ­tulo</label>
          <input type="text" name="titulo" required>

          <label>Autor</label>
          <input type="text" name="autor" required>

          <label>ISBN (opcional)</label>
          <input type="text" name="isbn">

          <button type="submit">Crear libro</button>
        </form>
      </div>

      <div class="card">
        <h2>Listado</h2>
        <%
          List<Libro> libros = (List<Libro>) request.getAttribute("libros");
          if (libros == null) {
        %>
          <p class="muted">No hay datos cargados.</p>
        <%
          } else if (libros.isEmpty()) {
        %>
          <p class="muted">No hay libros todavÃ­a.</p>
        <%
          } else {
        %>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>TÃ­tulo</th>
                <th>Autor</th>
                <th>ISBN</th>
              </tr>
            </thead>
            <tbody>
              <% for (Libro l : libros) { %>
                <tr>
                  <td><%= l.getId() %></td>
                  <td><%= l.getTitulo() %></td>
                  <td><%= l.getAutor() %></td>
                  <td><%= l.getIsbn() == null ? "" : l.getIsbn() %></td>
                </tr>
              <% } %>
            </tbody>
          </table>
        <%
          }
        %>
      </div>
    </div>
  </div>
</body>
</html>