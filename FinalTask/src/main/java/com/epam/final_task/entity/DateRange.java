package com.epam.final_task.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class DateRange implements Serializable {

    private Date arrival;

    private Date departure;

    public DateRange() {
    }

    public DateRange(Date arrival, Date departure) {
        this.arrival = arrival;
        this.departure = departure;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateRange range = (DateRange) o;
        return Objects.equals(arrival, range.arrival) &&
                Objects.equals(departure, range.departure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrival, departure);
    }

    @Override
    public String toString() {
        return "DateRange{" +
                "arrival=" + arrival +
                ", departure=" + departure +
                '}';
    }

}
