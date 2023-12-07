package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class MySqlConnector {
    public static Connection getConnection(Properties properties) throws SQLException {
        return DriverManager.getConnection(properties.getProperty("database.url"), properties);
    }
}
