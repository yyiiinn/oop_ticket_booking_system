package com.oop.springbootmvc.viewmodel;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class EventViewModel {
    private Long id;
    private String name;
    private String description;
    private String venue;
    private String image_url;
    private Date event_start_date;
    private Date event_end_date;
    private Time event_start_time;
    private Time event_end_time;
    private Timestamp ticket_sale_start_date_time;
    private Timestamp ticket_sale_end_date_time;
    private String status;

    public EventViewModel(String name, String description, String venue, String image_url, Date event_start_date, Date event_end_date, Time event_start_time, Time event_end_time, Timestamp ticket_sale_start_date_time, Timestamp ticket_sale_end_date_time, String status) {
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.image_url = image_url;
        this.event_start_date = event_start_date;
        this.event_end_date = event_end_date;
        this.event_start_time = event_start_time;
        this.event_end_time = event_end_time;
        this.ticket_sale_start_date_time = ticket_sale_start_date_time;
        this.ticket_sale_end_date_time = ticket_sale_end_date_time;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getVenue() {
        return this.venue;
    }

    public String getImageUrl() {
        return this.image_url;
    }

    public Date getEventStartDate() {
        return this.event_start_date;
    }

    public Date getEventEndDate() {
        return this.event_end_date;
    }

    public Time getEventStartTime() {
        return this.event_start_time;
    }

    public Time getEventEndTime() {
        return this.event_end_time;
    }
    
    public Timestamp getTicketSaleStartDateTime(){
        return this.ticket_sale_start_date_time;
    }

    public Timestamp getTicketSaleEndDateTime(){
        return this.ticket_sale_end_date_time;
    }

    public String getStatus(){
        return this.status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public void setEventStartDate(Date event_start_date) {
        this.event_start_date = event_start_date;
    }

    public void setEventEndDate(Date event_end_date) {
        this.event_end_date = event_end_date;
    }

    public void setEventStartTime(Time event_start_time) {
        this.event_start_time = event_start_time;
    }

    public void setEventEndTime(Time event_end_time) {
        this.event_end_time = event_end_time;
    }

    public void setTicketSaleStartDateTime(Timestamp ticket_sale_start_date_time) {
        this.ticket_sale_start_date_time = ticket_sale_start_date_time;
    }

    public void setTicketSaleEndDateTime(Timestamp ticket_sale_end_date_time) {
        this.ticket_sale_end_date_time = ticket_sale_end_date_time;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   
}
