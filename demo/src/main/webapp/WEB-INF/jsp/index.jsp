<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%! 
    // Simulación de una lista de usuarios (en un proyecto real usarías UsuarioDb)
    List<String> usuarios = new ArrayList<>();
%>

<%
    // Procesar login
    if ("login".equals(request.getParameter("action"))) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Credenciales hardcodeadas (solo para demo)
        if ("admin".equals(username) && "1234".equals(password)) {
            session.setAttribute("logueado", true);
        } else {
            out.println("<script>alert('Usuario o contraseña incorrectos');</script>");
        }
    }

    // Procesar logout
    if ("logout".equals(request.getParameter("action"))) {
        session.invalidate();
        response.sendRedirect("index.jsp");
        return;
    }

    // Si está logueado, cargar usuarios (simulados)
    if (session.getAttribute("logueado") != null) {
        usuarios.add("Usuario 1");
        usuarios.add("Usuario 2");
        usuarios.add("Usuario 3");
    }
%>

<html>
<head>
    <title>Sistema CRUD</title>
    <style>
        body { font-family: Arial; max-width: 500px; margin: 50px auto; }
        .login-box, .crud-box { 
            border: 1px solid #ddd; 
            padding: 20px; 
            border-radius: 5px; 
            margin-bottom: 20px;
        }
        input { width: 100%; padding: 8px; margin: 5px 0; }
        button { background: #4CAF50; color: white; border: none; padding: 8px; cursor: pointer; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    </style>
</head>
<body>

    <%-- **Si NO está logueado, mostrar Login** --%>
    <% if (session.getAttribute("logueado") == null) { %>
        <div class="login-box">
            <h2>Login</h2>
            <form method="post">
                <input type="hidden" name="action" value="login">
                <input type="text" name="username" placeholder="Usuario" required>
                <input type="password" name="password" placeholder="Contraseña" required>
                <button type="submit">Ingresar</button>
            </form>
        </div>
    <% } %>

    <%-- **Si está logueado, mostrar CRUD** --%>
    <% if (session.getAttribute("logueado") != null) { %>
        <div class="crud-box">
            <h2>Bienvenido, Admin!</h2>
            <a href="index.jsp?action=logout">Cerrar Sesión</a>
            
            <h3>Lista de Usuarios</h3>
            <table>
                <tr><th>ID</th><th>Nombre</th></tr>
                <% for (int i = 0; i < usuarios.size(); i++) { %>
                    <tr>
                        <td><%= i + 1 %></td>
                        <td><%= usuarios.get(i) %></td>
                    </tr>
                <% } %>
            </table>
        </div>
    <% } %>

</body>
</html>