package com.oop.springbootmvc.viewmodel;

public class PurchaseViewModel {
    private long sitId;
    private int quantity;
    
    public PurchaseViewModel() {

    }

    public PurchaseViewModel(long sitId, int quantity) {
        this.sitId = sitId;
        this.quantity = quantity;
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
}
