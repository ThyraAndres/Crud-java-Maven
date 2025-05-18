<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h2>Inicio de Sesión</h2>
    <form action="auth.jsp" method="post">
        <div>
            <label>Email:</label>
            <input type="text" name="email" required>
        </div>
        <div>
            <label>Contraseña:</label>
            <input type="password" name="password" required>
        </div>
        <button type="submit">Ingresar</button>
    </form>
    
    <%-- Mostrar mensaje de error si existe --%>
    <% if(request.getParameter("error") != null) { %>
        <p style="color:red;">Usuario o contraseña incorrectos</p>
    <% } %>
</body>
</html>