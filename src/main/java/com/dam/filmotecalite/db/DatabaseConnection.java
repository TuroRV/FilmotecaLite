package com.dam.filmotecalite.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/filmotecalite";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                } catch (ClassNotFoundException e) {
                    System.out.println("No se encontro el driver.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
