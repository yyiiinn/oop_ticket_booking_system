package com.oop.springbootmvc.viewmodel;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.Transaction;

public class CustomerTransactionViewModel {
    private long id;
    private String eventName;
    private Date eventStartDate;    
    private Time eventStartTime;
    private Timestamp purchasedDateTime;
    private int numberOfTickets;
    private float total;
    private String status;
    private Float cancellationFee;
    public CustomerTransactionViewModel() {
    }

    public CustomerTransactionViewModel(Transaction t) {
        Event e = t.getSeat().getEvent();
        this.id = t.getId();
        this.eventName = e.getName();
        this.eventStartDate = e.getEventStartDate();
        this.cancellationFee = e.getCancellationFee();
        this.purchasedDateTime = t.getPurchasedDateTime();
        this.numberOfTickets = t.getTickets().size();
        this.total = t.getCost();
        this.status = t.getStatus();
        this.eventStartTime = e.getEventStartTime();
    }

    public CustomerTransactionViewModel(long id, String eventName, Date eventStartDate, Timestamp purchasedDateTime,
        int numberOfTickets, float total, String status, float cancellationFee, Time eventStartTime) {
        this.id = id;
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.purchasedDateTime = purchasedDateTime;
        this.numberOfTickets = numberOfTickets;
        this.total = total;
        this.status = status;
        this.cancellationFee = cancellationFee;
        this.eventStartTime = eventStartTime;

    }

    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public Date getEventStartDate() {
        return eventStartDate;
    }
    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }
    public Timestamp getPurchasedDateTime() {
        return purchasedDateTime;
    }
    public void setPurchasedDateTime(Timestamp purchasedDateTime) {
        this.purchasedDateTime = purchasedDateTime;
    }
    public int getNumberOfTickets() {
        return numberOfTickets;
    }
    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Float getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(Float cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public Time getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Time eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

}
