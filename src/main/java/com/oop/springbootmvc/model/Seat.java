package com.oop.springbootmvc.model;

import java.util.Set;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    private int numberOfSeats;
    // @ManyToOne
    // @Column(nullable = false)
    //Event event;  
    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    public Event getEvent() {
        return event;
    }

    @OneToMany(mappedBy="seat")
    private Set<Transaction> transactions;

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }


    public Seat( String type, float cost, int numberOfSeats, Event event) {
        this.type = type;
        this.cost = cost;
        this.numberOfSeats =  numberOfSeats;
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
        return this.numberOfSeats;
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

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
   
}