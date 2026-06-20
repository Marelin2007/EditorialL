package org.example.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String STR_CONNECTION = "jdbc:sqlserver://DESKTOP-M7UIUQ1\\SQLEXPRESS;"
            + "encrypt = false;"
            + "databaseName = ProyectoDB;"
            + "trustServerCertificate = true;"
            + "user = Marelin;"
            + "password = Monterroza11";

    private Connection connection;
    private static ConexionDB instance;

    private ConexionDB() {
        this.connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Error al cargar el driver JDBC de Sql Server");
        }
    }

    public static ConexionDB getInstance() {
        if (instance == null) {
            instance = new ConexionDB();
        }
        return instance;
    }

    public synchronized Connection connect() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            try {
                this.connection = DriverManager.getConnection(STR_CONNECTION);
            } catch (SQLException ex) {
                throw new SQLException("Error al conectar con la base de datos: "
                        + ex.getMessage(), ex);
            }
        }
        return this.connection;
    }

    public static Connection getConexion() throws SQLException {
        return getInstance().connect();
    }
}