package com.goit.jdbc;

import java.time.LocalDate;

public class YoungestEldestWorkers {
    private String type;
    private String name;
    private LocalDate birthday;

    public void setType(String type) { this.type = type; }
    public String getType() { return type; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public LocalDate getBirthday() { return birthday; }

    @Override
    public String toString() {
        return "YoungestEldestWorkers{type='" + type + "', name='" + name + "', birthday=" + birthday + "}";
    }
}
