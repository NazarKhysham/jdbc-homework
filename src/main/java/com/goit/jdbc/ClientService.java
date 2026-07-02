package com.goit.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private final Connection connection = Database.getInstance().getConnection();

    public long create(String name) {
        validateName(name);

        try {
            String sql = "INSERT INTO client(name) VALUES(?)";

            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, name);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getLong(1);
            }

            throw new RuntimeException("Client was not created");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getById(long id) {
        try {
            PreparedStatement ps =
                    connection.prepareStatement(
                            "SELECT name FROM client WHERE id = ?"
                    );

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }

            throw new RuntimeException("Client not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setName(long id, String name) {
        validateName(name);

        try {
            PreparedStatement ps =
                    connection.prepareStatement(
                            "UPDATE client SET name = ? WHERE id = ?"
                    );

            ps.setString(1, name);
            ps.setLong(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(long id) {
        try {
            PreparedStatement ps =
                    connection.prepareStatement(
                            "DELETE FROM client WHERE id = ?"
                    );

            ps.setLong(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Client> listAll() {
        try {
            List<Client> clients = new ArrayList<>();

            PreparedStatement ps =
                    connection.prepareStatement(
                            "SELECT * FROM client"
                    );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                clients.add(
                        new Client(
                                rs.getLong("id"),
                                rs.getString("name")
                        )
                );
            }

            return clients;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateName(String name) {
        if (name == null ||
                name.length() < 2 ||
                name.length() > 1000) {
            throw new IllegalArgumentException(
                    "Client name must be 2-1000 chars"
            );
        }
    }
}