package io.rbetik12.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private final String url = "jdbc:mysql://localhost:3306/prog?serverTimezone=UTC";
    private final String user = "root";
    private final String password = "";

    private Connection connection;

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e) {
            System.out.println("Can't create connection: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

}
