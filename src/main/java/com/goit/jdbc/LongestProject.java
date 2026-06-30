package com.goit.jdbc;

public class LongestProject {
    private String name;
    private int monthCount;

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setMonthCount(int monthCount) { this.monthCount = monthCount; }
    public int getMonthCount() { return monthCount; }

    @Override
    public String toString() {
        return "LongestProject{name='" + name + "', monthCount=" + monthCount + "}";
    }
}
