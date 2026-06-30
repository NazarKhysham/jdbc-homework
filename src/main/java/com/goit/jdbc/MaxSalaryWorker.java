package com.goit.jdbc;

public class MaxSalaryWorker {
    private String name;
    private int salary;

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setSalary(int salary) { this.salary = salary; }
    public int getSalary() { return salary; }

    @Override
    public String toString() {
        return "MaxSalaryWorker{name='" + name + "', salary=" + salary + "}";
    }
}
