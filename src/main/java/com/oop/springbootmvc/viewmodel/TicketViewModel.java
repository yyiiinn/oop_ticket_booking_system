package com.oop.springbootmvc.viewmodel;

public class TicketViewModel {

    private String guid;
    private long transactionId;
    private String status;


    public TicketViewModel(String guid, long transactionId, String status) {

        this.guid = guid;
        this.transactionId = transactionId;
        this.status = status;
    }

    public TicketViewModel(){

    }

    // Setters
    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters

    public String getGuid() {
        return guid;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public String getStatus() {
        return status;
    }

}
