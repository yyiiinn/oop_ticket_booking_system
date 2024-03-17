package com.oop.springbootmvc.model;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import jakarta.persistence.*;

@Entity
@EnableAutoConfiguration
@Table(name = "events")
public class Event {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String category;
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    @Column(nullable = false)
    private String venue;
    @Column(nullable = false)
    private String imageURL;
    @Column(nullable = false)
    private Date eventStartDate;
    @Column(nullable = false)
    private Date eventEndDate;
    @Column(nullable = false)
    private Time eventStartTime;
    @Column(nullable = false)
    private Time eventEndTime;
    public Date getEventStartDate() {
        return eventStartDate;
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
    @Column(nullable = false)
    private LocalDateTime ticketSaleStartDateTime;
    @Column(nullable = false)
    private LocalDateTime ticketSaleEndDateTime;
    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy="event")
    private Set<Seat> seats;

    public Set<Seat> getSeats() { return seats; }

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
    public String getImageURL() {
        return this.imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public Date getEventStartDateTime() {
        return this.eventStartDate;
    }
    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }
    public Date getEventEndDate() {
        return this.eventEndDate;
    }
    public void setEventEndDate(Date eventEndDate) {
        this.eventEndDate = eventEndDate;
    }
    public LocalDateTime getTicketSaleStartDateTime() {
        return this.ticketSaleStartDateTime;
    }
    public void setTicketSaleStartDateTime(LocalDateTime ticketSaleStartDateTime) {
        this.ticketSaleStartDateTime = ticketSaleStartDateTime;
    }
    public LocalDateTime getTicketSaleEndDateTime() {
        return this.ticketSaleEndDateTime;
    }
    public void setTicketSaleEndDateTime(LocalDateTime ticketSaleEndDateTime) {
        this.ticketSaleEndDateTime = ticketSaleEndDateTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}