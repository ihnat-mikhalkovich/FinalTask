package com.epam.final_task.entity;

import java.util.Date;

public class DateRange {

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
}
