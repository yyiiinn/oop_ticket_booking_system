package com.oop.springbootmvc.viewmodel;

public class ConfirmPaymentViewModel {
    private String paymentIntent;

    public ConfirmPaymentViewModel() {
    }

    public ConfirmPaymentViewModel(String paymentIntent) {
        this.paymentIntent = paymentIntent;
    }

    public String getPaymentIntent() {
        return paymentIntent;
    }

    public void setPaymentIntent(String paymentIntent) {
        this.paymentIntent = paymentIntent;
    }



    
}

