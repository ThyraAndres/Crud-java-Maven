package com.rodri.demo.db;

import java.sql.*;

import com.rodri.demo.controlador.UsuarioGestor;
import com.rodri.demo.entidad.Usuario;

public class UsuarioDb {

    public static void cargarUsuarios() {
        String sql = "SELECT id, nombre, email, password FROM usuario";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password"));
                UsuarioGestor.crearUsuario(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Guardar un usuario en MySQL
    public static void guardarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPassword());
            pstmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario:");
            e.printStackTrace();
        }
    }

    // Actualizar usuario en MySQL
    public static void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, email = ?, password = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getPassword());
            pstmt.setInt(4, usuario.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario:");
            e.printStackTrace();
        }
    }

    // Eliminar usuario de MySQL
    public static void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario:");
            e.printStackTrace();
        }
    }
}