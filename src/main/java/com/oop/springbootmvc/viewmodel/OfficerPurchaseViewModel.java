package com.oop.springbootmvc.viewmodel;

public class OfficerPurchaseViewModel {
    private long sitId;
    private int quantity;
    private String username;
    
    public OfficerPurchaseViewModel() {

    }

    public OfficerPurchaseViewModel(long sitId, int quantity, String username) {
        this.sitId = sitId;
        this.quantity = quantity;
        this.username = username;
    }

    public long getSitId() {
        return sitId;
    }
    public void setSitId(long sitId) {
        this.sitId = sitId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getUsername() {
        return username;
    }
}
