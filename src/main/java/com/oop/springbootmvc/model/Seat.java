package com.oop.springbootmvc.model;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import jakarta.persistence.*;

@Entity
@EnableAutoConfiguration
@Table(name = "seats")
public class Seat {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private float cost;
    @Column(nullable = false)
    private int numberOfSits;
    @ManyToOne
    // @Column(nullable = false)
    Event event;  
    public Seat( String type, float cost, int numberOfSits, Event event) {
        this.type = type;
        this.cost = cost;
        this.numberOfSits =  numberOfSits;
        this.event = event;
    }

    public Seat() {

    }

    public int getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public Float getCost() {
        return this.cost;
    }

    public int getNumberOfSeats() {
        return this.numberOfSits;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public void setNumberOfSeats(int numberOfSits) {
        this.numberOfSits = numberOfSits;
    }
   
}