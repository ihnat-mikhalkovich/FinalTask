package com.epam.final_task.entity;

import java.io.Serializable;

public enum UserRoleEnum implements Serializable {

    ADMINISTRATOR("administrator"),
    MODERATOR("moderator"),
    USER("user"),
    BANNED("banned"),
    GUEST("guest");

    private String name;

    private UserRoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
