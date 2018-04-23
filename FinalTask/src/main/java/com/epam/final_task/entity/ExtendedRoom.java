package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ExtendedRoom implements Serializable {

    private int id = -1;

    private int roomsAmount;

    private Map<LanguageEnum, String> name;

    private double cost;

    private String image = null;

    private Map<LanguageEnum, String> description;

    private boolean visibility;

    public ExtendedRoom() {
        name = new EnumMap<>(LanguageEnum.class);
        description = new EnumMap<>(LanguageEnum.class);
    }

    public ExtendedRoom(int id, int roomsAmount, Map<LanguageEnum, String> name, double cost, String image, Map<LanguageEnum, String> description, boolean visibility) {
        this.id = id;
        this.roomsAmount = roomsAmount;
        this.name = name;
        this.cost = cost;
        this.image = image;
        this.description = description;
        this.visibility = visibility;
    }

    public ExtendedRoom(int roomsAmount, Map<LanguageEnum, String> name, double cost, String image, Map<LanguageEnum, String> description, boolean visibility) {
        this.roomsAmount = roomsAmount;
        this.name = name;
        this.cost = cost;
        this.image = image;
        this.description = description;
        this.visibility = visibility;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomsAmount() {
        return roomsAmount;
    }

    public void setRoomsAmount(int roomsAmount) {
        this.roomsAmount = roomsAmount;
    }

    public Map<LanguageEnum, String> getName() {
        return name;
    }

    public void setName(Map<LanguageEnum, String> name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<LanguageEnum, String> getDescription() {
        return description;
    }

    public void setDescription(Map<LanguageEnum, String> description) {
        this.description = description;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtendedRoom that = (ExtendedRoom) o;
        return id == that.id &&
                roomsAmount == that.roomsAmount &&
                Double.compare(that.cost, cost) == 0 &&
                visibility == that.visibility &&
                Objects.equals(name, that.name) &&
                Objects.equals(image, that.image) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomsAmount, name, cost, image, description, visibility);
    }

    @Override
    public String toString() {
        return "ExtendedRoom{" +
                "id=" + id +
                ", roomsAmount=" + roomsAmount +
                ", name=" + name +
                ", cost=" + cost +
                ", image='" + image + '\'' +
                ", description=" + description +
                ", visibility=" + visibility +
                '}';
    }

}
