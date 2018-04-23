package com.epam.final_task.entity;

import java.util.Objects;

public class ReservedRoom {

    private int reservedRoomId = -1;

    private int userId;

    private int roomId;

    private int roomsAmount;

    private DateRange range;

    public ReservedRoom() {
    }

    public ReservedRoom(int userId, int roomId, int roomsAmount, DateRange range) {
        this.userId = userId;
        this.roomId = roomId;
        this.roomsAmount = roomsAmount;
        this.range = range;
    }

    public ReservedRoom(int reservedRoomId, int userId, int roomId, int roomsAmount, DateRange range) {
        this.reservedRoomId = reservedRoomId;
        this.userId = userId;
        this.roomId = roomId;
        this.roomsAmount = roomsAmount;
        this.range = range;
    }

    public int getReservedRoomId() {
        return reservedRoomId;
    }

    public void setReservedRoomId(int reservedRoomId) {
        this.reservedRoomId = reservedRoomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomsAmount() {
        return roomsAmount;
    }

    public void setRoomsAmount(int roomsAmount) {
        this.roomsAmount = roomsAmount;
    }

    public DateRange getRange() {
        return range;
    }

    public void setRange(DateRange range) {
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservedRoom that = (ReservedRoom) o;
        return reservedRoomId == that.reservedRoomId &&
                userId == that.userId &&
                roomId == that.roomId &&
                roomsAmount == that.roomsAmount &&
                Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservedRoomId, userId, roomId, roomsAmount, range);
    }

}
