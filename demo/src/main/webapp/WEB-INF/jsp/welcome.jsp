<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bienvenido</title>
</head>
<body>
    <% 
        // Verificar si el usuario está logueado
        if(session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>
    
    <h1>Bienvenido, <%= session.getAttribute("user_name") %></h1>
    <p>Has iniciado sesión correctamente.</p>
    
    <a href="logout.jsp">Cerrar sesión</a>
</body>
</html>