package com.epam.final_task.entity;

public enum LanguageEnum {

    RU("ru"),
    EN("en");

    private String name;

    private LanguageEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
