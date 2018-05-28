package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserOrderBy implements Serializable {

    private UserFieldEnum orderBy;

    private SortDirectionEnum direction;

    public UserOrderBy() {
    }

    public UserOrderBy(UserFieldEnum orderBy, SortDirectionEnum direction) {
        this.orderBy = orderBy;
        this.direction = direction;
    }

    public UserFieldEnum getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(UserFieldEnum orderBy) {
        this.orderBy = orderBy;
    }

    public SortDirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(SortDirectionEnum direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOrderBy that = (UserOrderBy) o;
        return orderBy == that.orderBy &&
                direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderBy, direction);
    }

    @Override
    public String toString() {
        return "UserOrderBy{" +
                "orderBy=" + orderBy +
                ", direction=" + direction +
                '}';
    }

}
