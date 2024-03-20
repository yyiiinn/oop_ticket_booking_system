package com.oop.springbootmvc.viewmodel;

import com.oop.springbootmvc.model.Seat;


public class CustomerSeatViewModel {
    int id;
    private String type;
    private float cost;
    private int numberOfSeats;
    public CustomerSeatViewModel(Seat s) {
        this.id = s.getId();
        this.type = s.getType();
        this.cost = s.getCost();
        this.numberOfSeats = s.getNumberOfSeats();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
