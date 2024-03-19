package com.oop.springbootmvc.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Sit {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int id;
    @Column(nullable = false)
    private int event_id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private float cost;
    @Column(nullable = false)
    private int number_of_sits;
    public Sit(int event_id, String type, float cost, int number_of_sits) {
        this.event_id = event_id;
        this.type = type;
        this.cost = cost;
        this.number_of_sits =  number_of_sits;
    }

    public Sit() {

    }

    public int getId() {
        return this.id;
    }

    public int getEventId() {
        return this.event_id;
    }

    public String getType() {
        return this.type;
    }

    public Float getCost() {
        return this.cost;
    }

    public int getNumberOfSeats() {
        return this.number_of_sits;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEventId(int event_id) {
        this.event_id = event_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public void setNumberOfSeats(int number_of_sits) {
        this.number_of_sits = number_of_sits;
    }
   
}