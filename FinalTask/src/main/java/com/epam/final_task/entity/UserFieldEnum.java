package com.epam.final_task.entity;

import java.io.Serializable;

public enum UserFieldEnum implements Serializable {

    ID("id"),
    FIRST_NAME("first_name"),
    LAST_NAME("last_name"),
    TELEPHONE_NUMBER("telephone_number"),
    EMAIL("email"),
    ROLE("role"),
    REGISTRATION_DATE("registration_date"),
    DISCOUNT("discount"),
    BALANCE("balance");

    private String name;

    private UserFieldEnum(String name) {
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
