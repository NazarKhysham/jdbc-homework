package com.goit.jdbc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Reads {@code sql/init_db.sql} and executes the statements against the database
 * to create the tables and relations described there.
 */
public class DatabaseInitService {

    private static final String INIT_SQL_PATH = "sql/init_db.sql";

    public static void main(String[] args) {
        Connection conn = Database.getInstance().getConnection();
        try {
            String script = new String(Files.readAllBytes(Paths.get(INIT_SQL_PATH)));
            for (String sql : script.split(";")) {
                String trimmed = sql.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                try (PreparedStatement st = conn.prepareStatement(trimmed)) {
                    st.execute();
                }
            }
            System.out.println("Database initialized successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
