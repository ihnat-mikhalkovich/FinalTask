package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class UserPage implements Serializable {

    private List<User> users;

    private int pageAmount;

    private int currentPage;

    private UserOrderBy orderBy;

    public UserPage() {
    }

    public UserPage(List<User> users, int pageAmount, int currentPage, UserOrderBy orderBy) {
        this.users = users;
        this.pageAmount = pageAmount;
        this.currentPage = currentPage;
        this.orderBy = orderBy;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getPageAmount() {
        return pageAmount;
    }

    public void setPageAmount(int pageAmount) {
        this.pageAmount = pageAmount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public UserOrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(UserOrderBy orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPage userPage = (UserPage) o;
        return pageAmount == userPage.pageAmount &&
                currentPage == userPage.currentPage &&
                Objects.equals(users, userPage.users) &&
                orderBy == userPage.orderBy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, pageAmount, currentPage, orderBy);
    }

    @Override
    public String toString() {
        return "UserPage{" +
                "users=" + users +
                ", pageAmount=" + pageAmount +
                ", currentPage=" + currentPage +
                ", orderBy=" + orderBy +
                '}';
    }

}
