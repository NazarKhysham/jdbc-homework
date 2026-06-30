package com.goit.jdbc;

public class MaxProjectCountClient {
    private String name;
    private int projectCount;

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setProjectCount(int projectCount) { this.projectCount = projectCount; }
    public int getProjectCount() { return projectCount; }

    @Override
    public String toString() {
        return "MaxProjectCountClient{name='" + name + "', projectCount=" + projectCount + "}";
    }
}
