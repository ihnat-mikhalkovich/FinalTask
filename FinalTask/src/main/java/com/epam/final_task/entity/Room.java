package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.Objects;

public class Room implements Serializable {

    private int id = -1;

    private int roomsAmount;

    private String name;

    private double cost;

    private String image;

    private String description;

    public Room() {
    }

    public Room(int roomsAmount, String name, double cost, String image, String description) {
        this.roomsAmount = roomsAmount;
        this.name = name;
        this.cost = cost;
        this.image = image;
        this.description = description;
    }

    public Room(int id, int roomsAmount, String name, double cost, String image, String description) {
        this.id = id;
        this.roomsAmount = roomsAmount;
        this.name = name;
        this.cost = cost;
        this.image = image;
        this.description = description;
    }

    public int getRoomsAmount() {
        return roomsAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoomsAmount(int roomsAmount) {
        this.roomsAmount = roomsAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                roomsAmount == room.roomsAmount &&
                Double.compare(room.cost, cost) == 0 &&
                Objects.equals(name, room.name) &&
                Objects.equals(image, room.image) &&
                Objects.equals(description, room.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomsAmount, name, cost, image, description);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomsAmount=" + roomsAmount +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
