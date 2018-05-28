package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserRoom implements Serializable {

    private Room room;

    private ReservedRoom reservedRoom;

    private RoomTariff tariff;

    public UserRoom() {
    }

    public UserRoom(Room room, ReservedRoom reservedRoom, RoomTariff tariff) {
        this.room = room;
        this.reservedRoom = reservedRoom;
        this.tariff = tariff;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public ReservedRoom getReservedRoom() {
        return reservedRoom;
    }

    public void setReservedRoom(ReservedRoom reservedRoom) {
        this.reservedRoom = reservedRoom;
    }

    public RoomTariff getTariff() {
        return tariff;
    }

    public void setTariff(RoomTariff tariff) {
        this.tariff = tariff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoom that = (UserRoom) o;
        return Objects.equals(room, that.room) &&
                Objects.equals(reservedRoom, that.reservedRoom) &&
                Objects.equals(tariff, that.tariff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, reservedRoom, tariff);
    }

    @Override
    public String toString() {
        return "UserRoom{" +
                "room=" + room +
                ", reservedRoom=" + reservedRoom +
                ", tariff=" + tariff +
                '}';
    }

}
