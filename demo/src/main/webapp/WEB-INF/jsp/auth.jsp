<%@ page import="java.sql.*" %>
<%
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        // 1. Cargar el driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        // 2. Establecer conexión
        String url = "jdbc:mysql://localhost:3306/tu_basedatos?useSSL=false&serverTimezone=UTC";
        String user = "tu_usuario";
        String pass = "tu_contraseña";
        conn = DriverManager.getConnection(url, user, pass);
        
        // 3. Consulta SQL para verificar credenciales
        String sql = "SELECT id, nombre FROM usuario WHERE email = ? AND password = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        rs = pstmt.executeQuery();
        
        // 4. Verificar si encontró el usuario
        if(rs.next()) {
            // Login exitoso
            session.setAttribute("user_id", rs.getInt("id"));
            session.setAttribute("user_name", rs.getString("nombre"));
            response.sendRedirect("welcome.jsp");
        } else {
            // Login fallido
            response.sendRedirect("login.jsp?error=1");
        }
    } catch(Exception e) {
        e.printStackTrace();
        response.sendRedirect("login.jsp?error=1");
    } finally {
        // 5. Cerrar recursos
        if(rs != null) try { rs.close(); } catch(SQLException e) {}
        if(pstmt != null) try { pstmt.close(); } catch(SQLException e) {}
        if(conn != null) try { conn.close(); } catch(SQLException e) {}
    }
%>