package com.goit.jdbc;

import org.flywaydb.core.Flyway;

public class DatabaseInitService {

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        "jdbc:h2:./testdb;MODE=MySQL;DB_CLOSE_DELAY=-1",
                        "sa",
                        ""
                )
                .load();

        flyway.migrate();

        System.out.println("Flyway migrations completed successfully!");
    }
}