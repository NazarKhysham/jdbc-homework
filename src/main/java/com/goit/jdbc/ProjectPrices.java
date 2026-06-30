package com.goit.jdbc;

public class ProjectPrices {
    private String name;
    private int price;

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setPrice(int price) { this.price = price; }
    public int getPrice() { return price; }

    @Override
    public String toString() {
        return "ProjectPrices{name='" + name + "', price=" + price + "}";
    }
}
