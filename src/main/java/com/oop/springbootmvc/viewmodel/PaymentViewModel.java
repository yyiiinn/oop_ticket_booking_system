package com.oop.springbootmvc.viewmodel;

public class PaymentViewModel {
    private float topUpValue;

    public PaymentViewModel() {
    }

    public PaymentViewModel(float topUpValue) {
        this.topUpValue = topUpValue;
    }

    public float getTopUpValue() {
        return topUpValue;
    }

    public void setTopUpValue(float topUpValue) {
        this.topUpValue = topUpValue;
    }

    
}

