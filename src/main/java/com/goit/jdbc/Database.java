package com.goit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton utility class that encapsulates the connection to the H2 database.
 * The connection is established lazily on first call to {@link #getInstance()}
 * and reused for all subsequent calls.
 */
public final class Database {

    private static final String JDBC_URL = "jdbc:h2:./testdb;MODE=MySQL;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static Database instance;

    private final Connection connection;

    private Database() {
        try {
            this.connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
