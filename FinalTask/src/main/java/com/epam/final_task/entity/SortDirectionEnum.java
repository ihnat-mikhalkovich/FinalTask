package com.epam.final_task.entity;

import java.io.Serializable;

public enum SortDirectionEnum implements Serializable {

    ASC("asc"),
    DESC("desc");

    private String name;

    private SortDirectionEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
