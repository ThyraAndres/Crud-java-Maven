package com.rodri.demo.db;

import java.sql.*;
import com.rodri.demo.controlador.ArchivoBinarioGestor;
import com.rodri.demo.entidad.ArchivoBinario;

public class ArchivoDb {

    public static void cargarArchivoBinarios() {
        String sql = "SELECT id, nombre, tipoArchivo, contenido, LENGTH(contenido) as capacidad FROM archivobinario";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ArchivoBinario archivo = new ArchivoBinario();
                archivo.setId(rs.getInt("id"));
                archivo.setNombre(rs.getString("nombre"));
                archivo.setTipoArchivo(rs.getString("tipoArchivo"));
                archivo.setContenido(rs.getBytes("contenido"));
                // capacidad se calcula automáticamente en setContenido()

                ArchivoBinarioGestor.crearArchivo(archivo);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar archivos binarios:");
            e.printStackTrace();
        }
    }

    // Guardar un archivo binario en MySQL
    public static void guardarArchivoBinario(ArchivoBinario archivo) {
        String sql = "INSERT INTO archivobinario (nombre, tipoArchivo, contenido) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, archivo.getNombre());
            pstmt.setString(2, archivo.getTipoArchivo());
            pstmt.setBytes(3, archivo.getContenido());
            pstmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    archivo.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar archivo binario:");
            e.printStackTrace();
        }
    }

    // Actualizar archivo binario en MySQL
    public static void actualizarArchivoBinario(ArchivoBinario archivo) {
        String sql = "UPDATE archivobinario SET nombre = ?, tipoArchivo = ?, contenido = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, archivo.getNombre());
            pstmt.setString(2, archivo.getTipoArchivo());
            pstmt.setBytes(3, archivo.getContenido());
            pstmt.setInt(4, archivo.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar archivo binario:");
            e.printStackTrace();
        }
    }

    // Eliminar archivo binario de MySQL
    public static void eliminarArchivoBinario(int id) {
        String sql = "DELETE FROM archivobinario WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar archivo binario:");
            e.printStackTrace();
        }
    }

    // Método adicional para obtener solo el contenido (útil para archivos grandes)
    public static byte[] obtenerContenidoArchivo(int id) {
        String sql = "SELECT contenido FROM archivobinario WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("contenido");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener contenido de archivo:");
            e.printStackTrace();
        }
        return null;
    }
}