package com.oop.springbootmvc.viewmodel;

public class BookingViewModel {
    private float bookingCost;
    private int noOfSits;

    // Constructor
    public BookingViewModel(float bookingCost, int noOfSits) {
        this.bookingCost = bookingCost;
        this.noOfSits = noOfSits;
    }

    public BookingViewModel(){
        
    }

    // Getter for bookingCost
    public float getBookingCost() {
        return bookingCost;
    }

    public int getNoOfSits(){
        return noOfSits;
    }

    // Setter for bookingCost
    public void setBookingCost(float bookingCost) {
        this.bookingCost = bookingCost;
    }

    public void setNoOfSits(int noOfSits){
        this.noOfSits = noOfSits;
    }
}

