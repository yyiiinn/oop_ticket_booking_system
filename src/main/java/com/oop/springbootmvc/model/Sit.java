package com.oop.springbootmvc.model;


import jakarta.persistence.*;

@Entity
@Table(name = "sits")
public class Sit {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",  unique = true)
    long id;
    @Column(nullable = false)
    private long eventId;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private float cost;
    @Column(nullable = false)
    private long numberOfSits;

    public Sit() {
        // Default constructor
    }

    public Sit(long eventId, String type, float cost, long numberOfSits) {

        this.eventId = eventId;
        this.type = type;
        this.cost = cost;
        this.numberOfSits = numberOfSits;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public long getNumberOfSits() {
        return numberOfSits;
    }

    public void setNumberOfSits(long numberOfSits) {
        this.numberOfSits = numberOfSits;
    }
}
