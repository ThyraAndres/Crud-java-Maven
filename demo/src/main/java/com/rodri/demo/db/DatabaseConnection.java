package com.rodri.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String HOST = "localhost";
    private static final String PUERTO = "3307";
    private static final String DATABASE = "proyecto";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + DATABASE +
                "?useSSL=false&serverTimezone=UTC";

        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            throw new SQLException("Error al cargar el driver JDBC", e);
        }

        return DriverManager.getConnection(url, USER, PASSWORD);
    }
}
