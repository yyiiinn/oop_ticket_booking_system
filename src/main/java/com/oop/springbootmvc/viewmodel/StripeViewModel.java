package com.oop.springbootmvc.viewmodel;

public class StripeViewModel {
    private String clientSecret;

    public StripeViewModel() {
    }

    public StripeViewModel(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

   
}

