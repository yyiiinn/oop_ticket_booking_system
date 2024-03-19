package com.oop.springbootmvc.viewmodel;

import java.sql.Timestamp;

public class TransactionViewModel {
    private long id;
    private String status;
    private long customerId;
    private long sitId;
    private Timestamp purchasedDateTime;
    private float cost;
    private float cancellationCost;

    public TransactionViewModel(long id, String status, long customerId, long sitId, Timestamp purchasedDateTime, float cost, float cancellationCost) {
        this.id = id;
        this.status = status;
        this.customerId = customerId;
        this.sitId = sitId;
        this.purchasedDateTime = purchasedDateTime;
        this.cost = cost;
        this.cancellationCost = cancellationCost;
    }

    public TransactionViewModel(){
        
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getSitId() {
        return sitId;
    }

    public void setSitId(long sitId) {
        this.sitId = sitId;
    }

    public Timestamp getPurchasedDateTime() {
        return purchasedDateTime;
    }

    public void setPurchasedDateTime(Timestamp purchasedDateTime) {
        this.purchasedDateTime = purchasedDateTime;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getCancellationCost() {
        return cancellationCost;
    }

    public void setCancellationCost(float cancellationCost) {
        this.cancellationCost = cancellationCost;
    }
}

