package com.oop.springbootmvc.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class Event {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String venue;
    @Column(nullable = false)
    private String image_url;
    @Column(nullable = false)
    private Date event_start_date;
    @Column(nullable = false)
    private Date event_end_date;
    @Column(nullable = false)
    private Time event_start_time;
    @Column(nullable = false)
    private Time event_end_time;
    @Column(nullable = false)
    private Timestamp ticket_sale_start_date_time;
    @Column(nullable = false)
    private Timestamp ticket_sale_end_date_time;
    @Column(nullable = false)
    private String status;
    public Event(String name, String description, String venue, String image_url, Date event_start_date, Date event_end_date, Time event_start_time, Time event_end_time, Timestamp ticket_sale_start_date_time, Timestamp ticket_sale_end_date_time, String status) {
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

    public Event() {

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