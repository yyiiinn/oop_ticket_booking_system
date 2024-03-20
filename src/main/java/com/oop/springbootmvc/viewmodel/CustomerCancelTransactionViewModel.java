package com.oop.springbootmvc.viewmodel;

public class CustomerCancelTransactionViewModel {
    long id;

    public CustomerCancelTransactionViewModel() {
    }
    
    public CustomerCancelTransactionViewModel(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
