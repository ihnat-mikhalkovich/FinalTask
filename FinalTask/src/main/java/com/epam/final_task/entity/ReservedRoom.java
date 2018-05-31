package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.Objects;

public class ReservedRoom implements Serializable {

    private int reservedRoomId = -1;

    private int userId;

    private int roomId;

    private int roomsAmount;

    private DateRange range;

    private boolean approval = false;

    private boolean paid = false;

    private double totalCost = -1;

    private int tariffId;

    private boolean canceled = false;

    public ReservedRoom() {
    }

    public ReservedRoom(int reservedRoomId, int userId, int roomId, int roomsAmount, DateRange range, boolean approval, boolean paid, double totalCost, int tariffId, boolean canceled) {
        this.reservedRoomId = reservedRoomId;
        this.userId = userId;
        this.roomId = roomId;
        this.roomsAmount = roomsAmount;
        this.range = range;
        this.approval = approval;
        this.paid = paid;
        this.totalCost = totalCost;
        this.tariffId = tariffId;
        this.canceled = canceled;
    }

    public ReservedRoom(int userId, int roomId, int roomsAmount, DateRange range, boolean approval, boolean paid, int tariffId) {
        this.userId = userId;
        this.roomId = roomId;
        this.roomsAmount = roomsAmount;
        this.range = range;
        this.approval = approval;
        this.paid = paid;
        this.tariffId = tariffId;
    }

    public ReservedRoom(int userId, int roomId, int roomsAmount, DateRange range, int tariffId) {
        this.userId = userId;
        this.roomId = roomId;
        this.roomsAmount = roomsAmount;
        this.range = range;
        this.tariffId = tariffId;
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

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getTariffId() {
        return tariffId;
    }

    public void setTariffId(int tariffId) {
        this.tariffId = tariffId;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
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
                approval == that.approval &&
                paid == that.paid &&
                Double.compare(that.totalCost, totalCost) == 0 &&
                tariffId == that.tariffId &&
                canceled == that.canceled &&
                Objects.equals(range, that.range);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reservedRoomId, userId, roomId, roomsAmount, range, approval, paid, totalCost, tariffId, canceled);
    }

    @Override
    public String toString() {
        return "ReservedRoom{" +
                "reservedRoomId=" + reservedRoomId +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", roomsAmount=" + roomsAmount +
                ", range=" + range +
                ", approval=" + approval +
                ", paid=" + paid +
                ", totalCost=" + totalCost +
                ", tariffId=" + tariffId +
                ", canceled=" + canceled +
                '}';
    }

}
