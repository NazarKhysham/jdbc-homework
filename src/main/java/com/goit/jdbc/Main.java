package com.goit.jdbc;

import java.util.List;

/**
 * Demonstrates how to use {@link DatabaseQueryService} from regular Java code.
 *
 * <p>Before running this class make sure that the database has been initialized
 * and populated by running {@link DatabaseInitService} and
 * {@link DatabasePopulateService} in that order.
 */
public class Main {

    public static void main(String[] args) {
        DatabaseQueryService queryService = new DatabaseQueryService();

        printSection("Max Project Count Clients", queryService.findMaxProjectsClient());
        printSection("Longest Project(s)",        queryService.findLongestProject());
        printSection("Max Salary Worker(s)",      queryService.findMaxSalaryWorker());
        printSection("Youngest & Eldest Workers", queryService.findYoungestEldestWorkers());
        printSection("Project Prices",            queryService.printProjectPrices());
    }

    private static void printSection(String title, List<?> rows) {
        System.out.println("=== " + title + " ===");
        if (rows.isEmpty()) {
            System.out.println("(no rows)");
        } else {
            for (Object row : rows) {
                System.out.println(row);
            }
        }
        System.out.println();
    }
}
