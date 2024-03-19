package com.oop.springbootmvc.model;
import java.util.List;

public class EventWithSits {
    private List<Event> events;
    private List<Sit> seats;

    public EventWithSits(List<Event> events, List<Sit> seats) {
        this.events = events;
        this.seats = seats;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Sit> getSeats() {
        return seats;
    }
}
