<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.biblioteca.model.Socio" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Socios</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style.css">
</head>
<body>
  <div class="container">
    <%@ include file="_header.jspf" %>

    <h1>Socios</h1>

    <div class="grid">
      <div class="card">
        <h2>Alta de socio</h2>
        <form method="post" action="<%= request.getContextPath() %>/socios">
          <label>Nombre</label>
          <input type="text" name="nombre" required>

          <label>Email</label>
          <input type="email" name="email" required>

          <button type="submit">Crear socio</button>
        </form>
      </div>

      <div class="card">
        <h2>Listado</h2>
        <%
          List<Socio> socios = (List<Socio>) request.getAttribute("socios");
          if (socios == null) {
        %>
          <p class="muted">No hay datos cargados.</p>
        <%
          } else if (socios.isEmpty()) {
        %>
          <p class="muted">No hay socios todavÃ­a.</p>
        <%
          } else {
        %>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Email</th>
              </tr>
            </thead>
            <tbody>
              <% for (Socio s : socios) { %>
                <tr>
                  <td><%= s.getId() %></td>
                  <td><%= s.getNombre() %></td>
                  <td><%= s.getEmail() %></td>
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