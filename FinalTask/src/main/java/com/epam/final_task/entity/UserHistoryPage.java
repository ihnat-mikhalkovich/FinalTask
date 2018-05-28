package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class UserHistoryPage implements Serializable {

    private List<UserRoom> roomHistory;

    private int currentPage;

    private int pageAmount;

    public UserHistoryPage() {
    }

    public UserHistoryPage(List<UserRoom> roomHistory, int currentPage, int pageAmount) {
        this.roomHistory = roomHistory;
        this.currentPage = currentPage;
        this.pageAmount = pageAmount;
    }

    public List<UserRoom> getRoomHistory() {
        return roomHistory;
    }

    public void setRoomHistory(List<UserRoom> roomHistory) {
        this.roomHistory = roomHistory;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageAmount() {
        return pageAmount;
    }

    public void setPageAmount(int pageAmount) {
        this.pageAmount = pageAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHistoryPage that = (UserHistoryPage) o;
        return currentPage == that.currentPage &&
                pageAmount == that.pageAmount &&
                Objects.equals(roomHistory, that.roomHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomHistory, currentPage, pageAmount);
    }

    @Override
    public String toString() {
        return "UserHistoryPage{" +
                "roomHistory=" + roomHistory +
                ", currentPage=" + currentPage +
                ", pageAmount=" + pageAmount +
                '}';
    }

}
