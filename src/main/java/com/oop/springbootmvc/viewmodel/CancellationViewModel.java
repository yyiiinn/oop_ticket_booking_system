package com.oop.springbootmvc.viewmodel;

public class CancellationViewModel {
    private long numberOfSits;
    private float cost;
    private float cancellationCost;

    public CancellationViewModel() {
        // Default constructor
    }
    
    public CancellationViewModel(long numberOfSits, float cost, float cancellationCost){
        this.numberOfSits = numberOfSits;
        this.cost = cost;
        this.cancellationCost = cancellationCost;
    }

    public long getNumberOfSits() {
        return numberOfSits;
    }

    public void setNumberOfSits(long numberOfSits) {
        this.numberOfSits = numberOfSits;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getCancellationCost(){
        return cancellationCost;
    }

    public void setCancellationCost(float cancellationCost){
        this.cancellationCost = cancellationCost;
    }
}
