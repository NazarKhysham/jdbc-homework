package com.goit.jdbc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Reads {@code sql/populate_db.sql} and executes the statements against the database
 * to fill the tables with sample data.
 */
public class DatabasePopulateService {

    private static final String POPULATE_SQL_PATH = "sql/populate_db.sql";

    public static void main(String[] args) {
        Connection conn = Database.getInstance().getConnection();
        try {
            String script = new String(Files.readAllBytes(Paths.get(POPULATE_SQL_PATH)));
            for (String sql : script.split(";")) {
                String trimmed = sql.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                try (PreparedStatement st = conn.prepareStatement(trimmed)) {
                    st.execute();
                }
            }
            System.out.println("Database populated successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Failed to populate database", e);
        }
    }
}
