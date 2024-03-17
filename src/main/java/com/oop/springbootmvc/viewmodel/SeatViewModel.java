package com.oop.springbootmvc.viewmodel;


public class SeatViewModel {
    private String type;
    private double cost;
    private int numberOfSeats;
    private double cancellationFee;

    public SeatViewModel(String type, double cost, int numberOfSeats, double cancellationFee) {
        this.type = type;
        this.cost = cost;
        this.numberOfSeats = numberOfSeats;
        this.cancellationFee = cancellationFee;
    }
    public SeatViewModel(){

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
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    public double getCancellationFee() {
        return cancellationFee;
    }
    public void setCancellationFee(double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }
}
