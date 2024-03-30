package com.oop.springbootmvc.viewmodel;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.oop.springbootmvc.model.Event;
import com.oop.springbootmvc.model.Seat;

public class CustomerEventViewModel {
    long id;
    private String name;
    private String description;
    private String venue;
    private byte[] image;
    private Date eventStartDate;
    private Time eventStartTime;
    private Time eventEndTime;
    private Timestamp ticketSaleStartDateTime;
    private Timestamp ticketSaleEndDateTime;
    private String status;
    private String category;
    private Float cancellationFee;
    private ArrayList<CustomerSeatViewModel> seats;

    public CustomerEventViewModel(Event e) {
        this.name = e.getName();
        this.id = e.getId();
        this.description = e.getDescription();
        this.venue = e.getVenue();
        this.image = e.getImage();
        this.eventStartDate = e.getEventStartDate();
        this.eventStartTime = e.getEventStartTime();
        this.eventEndTime = e.getEventEndTime();
        this.ticketSaleStartDateTime = e.getTicketSaleStartDateTime();
        this.ticketSaleEndDateTime = e.getTicketSaleEndDateTime();
        this.status = e.getStatus();
        this.category = e.getCategory();
        this.cancellationFee = e.getCancellationFee();
        this.seats = new ArrayList<>();
        for(Seat s: e.getSeats()){
            this.seats.add(new CustomerSeatViewModel(s));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public Time getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Time eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public Time getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(Time eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public Timestamp getTicketSaleStartDateTime() {
        return ticketSaleStartDateTime;
    }

    public void setTicketSaleStartDateTime(Timestamp ticketSaleStartDateTime) {
        this.ticketSaleStartDateTime = ticketSaleStartDateTime;
    }

    public Timestamp getTicketSaleEndDateTime() {
        return ticketSaleEndDateTime;
    }

    public void setTicketSaleEndDateTime(Timestamp ticketSaleEndDateTime) {
        this.ticketSaleEndDateTime = ticketSaleEndDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(Float cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public ArrayList<CustomerSeatViewModel> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<CustomerSeatViewModel> seats) {
        this.seats = seats;
    }

    
    
}
