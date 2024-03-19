package com.oop.springbootmvc.model;
import java.util.List;

public class EventWithSeats {
    private List<Event> events;
    private List<Seat> seats;

    public EventWithSeats(List<Event> events, List<Seat> seats) {
        this.events = events;
        this.seats = seats;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
