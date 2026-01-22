<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Biblioteca PFC</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style.css">
</head>
<body>
  <div class="container">
    <h1>Biblioteca PFC</h1>
    <p class="muted">MVP: socios Â· libros Â· prÃ©stamos Â· devoluciÃ³n Â· retraso calculado</p>

    <div class="card">
      <h2>NavegaciÃ³n</h2>
      <ul>
        <li><a href="<%= request.getContextPath() %>/socios">GestiÃ³n de socios</a></li>
        <li><a href="<%= request.getContextPath() %>/libros">GestiÃ³n de libros</a></li>
        <li><a href="<%= request.getContextPath() %>/prestamos">GestiÃ³n de prÃ©stamos</a></li>
        <li><a href="<%= request.getContextPath() %>/health">Health check</a></li>
      </ul>
    </div>

    <div class="footer muted">
      <p>Proyecto DAM Â· Servlet 4 (javax) Â· JDBC Â· MySQL</p>
    </div>
  </div>
</body>
</html>