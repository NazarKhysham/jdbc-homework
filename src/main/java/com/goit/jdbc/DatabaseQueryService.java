package com.goit.jdbc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Runs the SELECT queries stored in the {@code sql/} directory and maps their
 * results to plain Java objects.
 *
 * <p>Every query is executed through a {@link PreparedStatement} — even the ones
 * that currently have no parameters — so that adding a parameter later remains
 * safe from SQL-injection.
 */
public class DatabaseQueryService {

    private String readSqlFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read SQL file: " + fileName, e);
        }
    }

    public List<MaxProjectCountClient> findMaxProjectsClient() {
        List<MaxProjectCountClient> result = new ArrayList<>();
        String sql = readSqlFile("sql/find_max_projects_client.sql");
        Connection conn = Database.getInstance().getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                MaxProjectCountClient client = new MaxProjectCountClient();
                client.setName(rs.getString("name"));
                client.setProjectCount(rs.getInt("project_count"));
                result.add(client);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute findMaxProjectsClient", e);
        }
        return result;
    }

    public List<LongestProject> findLongestProject() {
        List<LongestProject> result = new ArrayList<>();
        String sql = readSqlFile("sql/find_longest_project.sql");
        Connection conn = Database.getInstance().getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                LongestProject project = new LongestProject();
                project.setName(rs.getString("name"));
                project.setMonthCount(rs.getInt("month_count"));
                result.add(project);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute findLongestProject", e);
        }
        return result;
    }

    public List<MaxSalaryWorker> findMaxSalaryWorker() {
        List<MaxSalaryWorker> result = new ArrayList<>();
        String sql = readSqlFile("sql/find_max_salary_worker.sql");
        Connection conn = Database.getInstance().getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                MaxSalaryWorker worker = new MaxSalaryWorker();
                worker.setName(rs.getString("name"));
                worker.setSalary(rs.getInt("salary"));
                result.add(worker);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute findMaxSalaryWorker", e);
        }
        return result;
    }

    public List<YoungestEldestWorkers> findYoungestEldestWorkers() {
        List<YoungestEldestWorkers> result = new ArrayList<>();
        String sql = readSqlFile("sql/find_youngest_eldest_workers.sql");
        Connection conn = Database.getInstance().getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                YoungestEldestWorkers worker = new YoungestEldestWorkers();
                worker.setType(rs.getString("type"));
                worker.setName(rs.getString("name"));
                worker.setBirthday(rs.getDate("birthday").toLocalDate());
                result.add(worker);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute findYoungestEldestWorkers", e);
        }
        return result;
    }

    public List<ProjectPrices> printProjectPrices() {
        List<ProjectPrices> result = new ArrayList<>();
        String sql = readSqlFile("sql/print_project_prices.sql");
        Connection conn = Database.getInstance().getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                ProjectPrices project = new ProjectPrices();
                project.setName(rs.getString("name"));
                project.setPrice(rs.getInt("price"));
                result.add(project);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute printProjectPrices", e);
        }
        return result;
    }
}
