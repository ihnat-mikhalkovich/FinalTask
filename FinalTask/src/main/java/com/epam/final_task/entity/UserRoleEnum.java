package com.epam.final_task.entity;

public enum UserRoleEnum {

    ADMINISTRATOR("administrator"),
    MODERATOR("moderator"),
    USER("user"),
    GUEST("guest");

    private String name;

    private UserRoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
