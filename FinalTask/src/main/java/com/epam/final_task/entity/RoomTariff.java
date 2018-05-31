package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.Objects;

public class RoomTariff implements Serializable {

    private int id = -1;

    private String name;

    private String description;

    private double cost;

    private boolean visibility;

    public RoomTariff() {
    }

    public RoomTariff(int id, String name, String description, double cost, boolean visibility) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.visibility = visibility;
    }

    public RoomTariff(String name, String description, double cost, boolean visibility) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.visibility = visibility;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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
        RoomTariff that = (RoomTariff) o;
        return id == that.id &&
                visibility == that.visibility &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, cost, visibility);
    }

    @Override
    public String toString() {
        return "RoomTariff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost='" + cost + '\'' +
                ", visibility=" + visibility +
                '}';
    }

}
