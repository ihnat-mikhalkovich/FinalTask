package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Javabean class describing the Room.
 *
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public class Room implements Serializable {

    private int id = -1;

    private int roomsAmount;

    private String name;

    private double cost;

    private String image;

    private String description;

    private int numberOfPersons;

    /**
     * Constructor without parameters.
     */
    public Room() {
    }

    /**
     * Constructor with parameters. Used to add a room to the database.
     *
     * @param roomsAmount number of rooms of this type
     * @param name name of the room of this type
     * @param cost cost of this type
     * @param image image of a room of this type
     * @param description description of this room type
     * @param numberOfPersons room capacity of this type
     */
    public Room(int roomsAmount, String name, double cost, String image, String description, int numberOfPersons) {
        this.roomsAmount = roomsAmount;
        this.name = name;
        this.cost = cost;
        this.image = image;
        this.description = description;
        this.numberOfPersons = numberOfPersons;
    }

    /**
     * Constructor with parameters. Used for get information from database.
     *
     * @param id id rooms of this type
     * @param roomsAmount number of rooms of this type
     * @param name name of the room of this type
     * @param cost cost of this type
     * @param image image of a room of this type
     * @param description description of this room type
     * @param numberOfPersons room capacity of this type
     */
    public Room(int id, int roomsAmount, String name, double cost, String image, String description, int numberOfPersons) {
        this.id = id;
        this.roomsAmount = roomsAmount;
        this.name = name;
        this.cost = cost;
        this.image = image;
        this.description = description;
        this.numberOfPersons = numberOfPersons;
    }

    /**
     * Used to get room ID
     *
     * @return id rooms of this type
     */
    public int getId() {
        return id;
    }

    /**
     * Used to set room ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Used to get the rooms amount
     *
     * @return number of rooms of this type
     */
    public int getRoomsAmount() {
        return roomsAmount;
    }

    /**
     * Used to set rooms amount
     */
    public void setRoomsAmount(int roomsAmount) {
        this.roomsAmount = roomsAmount;
    }

    /**
     * Used to get the room name
     *
     * @return room name
     */
    public String getName() {
        return name;
    }

    /**
     * Used to set room name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Used to get the room cost
     *
     * @return room cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Used to set room cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Used to get the room image
     *
     * @return room image
     */
    public String getImage() {
        return image;
    }

    /**
     * Used to set room image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Used to get the room description
     *
     * @return room description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Used to set room description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Used to get room capacity
     *
     * @return room description
     */
    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    /**
     * Used to set room capacity
     */
    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    /**
     * Overriding the {@code equals()} method of the {@code Object}.
     *
     * @param o compared to the current object
     * @return true if object equals {@code this}
     * false if object don't equals {@code this}
     * @see Object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                roomsAmount == room.roomsAmount &&
                Double.compare(room.cost, cost) == 0 &&
                numberOfPersons == room.numberOfPersons &&
                Objects.equals(name, room.name) &&
                Objects.equals(image, room.image) &&
                Objects.equals(description, room.description);
    }

    /**
     * Overriding the {@code hashCode()} method of the {@code Object}.
     *
     * @return hash code of {@code this}
     * @see Objects
     * @see Object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, roomsAmount, name, cost, image, description, numberOfPersons);
    }

    /**
     * Override the class {@code toString()} of class {@code Object}.
     *
     * @return string representation of the object
     * @see Object
     */
    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomsAmount=" + roomsAmount +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", numberOfPersons=" + numberOfPersons +
                '}';
    }
}
