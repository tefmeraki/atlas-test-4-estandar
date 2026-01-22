# --- Este repositorio se corresponde con la simulación ATLAS "Alumno Test-4 (simulación manual “estándar realista”)"

# Biblioteca PFC (DAM) — README técnico

Aplicación web sencilla para gestionar préstamos de libros en una biblioteca pequeña:
- Alta y listado de socios
- Alta y listado de libros
- Crear préstamo / devolver préstamo
- Estado de préstamo: ACTIVO / DEVUELTO / RETRASADO (retraso calculado con fechas)

## Stack y compatibilidad
- Java 11 (recomendado por compatibilidad en entornos de FP)
- Maven
- Servlets **4.0.1 (javax.servlet)** → compatible con Tomcat 9 / Jetty 9
- JSP (vistas)
- JDBC “a pelo” (sin framework de acceso a BD)
- MySQL

## Dependencias Maven (pom.xml)
- `javax.servlet-api:4.0.1` (scope: provided)
- `mysql-connector-j` (driver JDBC)
- `jstl` (opcional, útil si se quiere JSTL en JSP)
- `jetty-maven-plugin` para ejecutar rápido en desarrollo

> Nota: Si usas Tomcat local, puedes desplegar el WAR.
> Para este PFC se usa Jetty plugin para simplificar el arranque.

## Base de datos
1) Crear BD y tablas con el script `schema.sql`:
- Crea la BD `biblioteca_pfc`
- Tablas: `socio`, `libro`, `prestamo`

2) Configuración de conexión
En `DbConnection.java` (MVP) se usa configuración simple:
- URL: `jdbc:mysql://localhost:3306/biblioteca_pfc?...`
- USER/PASS: `root/root` (ajustar según tu entorno)

## Arranque del proyecto (desarrollo)
Requisitos:
- JDK 11 instalado
- Maven instalado
- MySQL corriendo y `schema.sql` ejecutado

Pasos:
1. `mvn clean package`
2. `mvn jetty:run`
3. Abrir en navegador:
   - `http://localhost:8080/` (index)
   - `http://localhost:8080/socios`
   - `http://localhost:8080/libros`
   - `http://localhost:8080/prestamos`
   - `http://localhost:8080/health`

## Flujo de prueba (smoke test)
1) Crear socio
2) Crear libro
3) Crear préstamo (con fechas)
4) Ver listado (ACTIVO)
5) Intentar crear otro préstamo del mismo libro sin devolver (debe fallar)
6) Devolver préstamo
7) Ver listado (DEVUELTO)

## Decisiones técnicas (explicables en defensa)
1) **Servlet 4 (javax)**:
   - máxima compatibilidad (Tomcat 9 / Jetty 9)
   - evita confusión `jakarta.*` en entornos mixtos

2) **JDBC sin framework**:
   - facilita entender qué hace la app
   - reduce “magia” y dependencia de frameworks
   - mejora defendibilidad del aprendizaje

3) **Modelo de datos mínimo (3 tablas)**:
   - Socio, Libro, Prestamo
   - Suficiente para el MVP sin sobre-diseñar

4) **Sanciones NO modeladas como entidad**:
   - el MVP solo calcula “retraso” en base a fechas
   - no se implementan pagos ni multas

5) **Regla clave del negocio (libro disponible)**:
   - un libro no puede tener dos préstamos activos
   - se comprueba con SQL: `fecha_devolucion IS NULL`

## Limitaciones conscientes (alcance MVP)
- No hay login/roles
- No hay reservas
- No hay edición/borrado (solo alta y listado)
- Validaciones simples (pensado para ser defendible y terminar a tiempo)

## Nota importante sobre la pantalla de Préstamos
Para que los selects de “Socio” y “Libro” se rellenen, el controller de préstamos debe cargar:
- lista de socios
- lista de libros
y pasarlas como atributos del request.

Ejemplo (idea):
- `request.setAttribute("socios", socioService.listar());`
- `request.setAttribute("libros", libroService.listar());`
