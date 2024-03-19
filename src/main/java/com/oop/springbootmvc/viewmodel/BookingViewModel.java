package com.oop.springbootmvc.viewmodel;

public class BookingViewModel {
    private float bookingCost;
    private long noOfSits;

    // Constructor
    public BookingViewModel(float bookingCost, long noOfSits) {
        this.bookingCost = bookingCost;
        this.noOfSits = noOfSits;
    }

    public BookingViewModel(){
        
    }

    // Getter for bookingCost
    public float getBookingCost() {
        return bookingCost;
    }

    public long getNoOfSits(){
        return noOfSits;
    }

    // Setter for bookingCost
    public void setBookingCost(float bookingCost) {
        this.bookingCost = bookingCost;
    }

    public void setNoOfSits(long noOfSits){
        this.noOfSits = noOfSits;
    }
}

