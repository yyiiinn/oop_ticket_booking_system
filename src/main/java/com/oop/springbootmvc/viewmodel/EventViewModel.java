package com.oop.springbootmvc.viewmodel;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.Column;



public class EventViewModel {
    private String name;
    private String description;

    private String category;

    private String venue;
    private String imageURL;
    private String eventStartDate;
    private String eventEndDate;
    private String eventStartTime;
    private String eventEndTime;
    private String ticketSaleStartDateTime;
    private String ticketSaleEndDateTime;
    private String status;
    private ArrayList<SeatViewModel> seats;
    
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public EventViewModel(){
        
    }

    public EventViewModel(String name, String description, String category, String venue, String imageURL,
            String eventStartDate, String eventEndDate, String eventStartTime, String eventEndTime,
            String ticketSaleStartDateTime, String ticketSaleEndDateTime, String status,
            ArrayList<SeatViewModel> seats) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.venue = venue;
        this.imageURL = imageURL;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.ticketSaleStartDateTime = ticketSaleStartDateTime;
        this.ticketSaleEndDateTime = ticketSaleEndDateTime;
        this.status = status;
        this.seats = seats;
    }
    public ArrayList<SeatViewModel> getSeats() {
        return seats;
    }
    public String getEventStartDate() {
        return eventStartDate;
    }
    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }
    public String getEventEndDate() {
        return eventEndDate;
    }
    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }
    public String getEventStartTime() {
        return eventStartTime;
    }
    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }
    public String getEventEndTime() {
        return eventEndTime;
    }
    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }
    public void setSeats(ArrayList<SeatViewModel> seats) {
        this.seats = seats;
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
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTicketSaleStartDateTime() {
        return ticketSaleStartDateTime;
    }
    public void setTicketSaleStartDateTime(String ticketSaleStartDateTime) {
        this.ticketSaleStartDateTime = ticketSaleStartDateTime;
    }
    public String getTicketSaleEndDateTime() {
        return ticketSaleEndDateTime;
    }
    public void setTicketSaleEndDateTime(String ticketSaleEndDateTime) {
        this.ticketSaleEndDateTime = ticketSaleEndDateTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
