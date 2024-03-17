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
    long id;
    @ManyToOne
    // @Column(nullable = false)
    Event event;    
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private double cost;
    @Column(nullable = false)
    private double cancellationFee;
    @Column(nullable = false)
    private int numberOfSits;
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public double getCancellationFee() {
        return cancellationFee;
    }
    public void setCancellationFee(double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }
    public int getNumberOfSits() {
        return numberOfSits;
    }
    public void setNumberOfSits(int numberOfSits) {
        this.numberOfSits = numberOfSits;
    }
}