package com.rodri.demo.db;

import java.sql.*;

import com.rodri.demo.controlador.EquipoGestor;
import com.rodri.demo.entidad.Equipo;

public class EquipoDb {

    public static void cargarEquipos() {
        String sql = "SELECT id, nombre, descripcion FROM equipo";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Equipo equipo = new Equipo(
                        rs.getString("nombre"),
                        rs.getString("descripcion"));
                EquipoGestor.crearEquipo(equipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Guardar un equipo en MySQL
    public static void guardarEquipo(Equipo equipo) {
        String sql = "INSERT INTO equipo (nombre, descripcion) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, equipo.getNombre());
            pstmt.setString(2, equipo.getDescripcion());
            pstmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    equipo.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar equipo:");
            e.printStackTrace();
        }
    }

    // Actualizar equipo en MySQL
    public static void actualizarEquipo(Equipo equipo) {
        String sql = "UPDATE equipo SET nombre = ?, descripcion = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipo.getNombre());
            pstmt.setString(2, equipo.getDescripcion());
            pstmt.setInt(3, equipo.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar equipo:");
            e.printStackTrace();
        }
    }

    // Eliminar equipo de MySQL
    public static void eliminarEquipo(int id) {
        String sql = "DELETE FROM equipo WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar equipo:");
            e.printStackTrace();
        }
    }
}