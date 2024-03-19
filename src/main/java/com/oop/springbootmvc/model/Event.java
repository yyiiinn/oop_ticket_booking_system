package com.oop.springbootmvc.model;
import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity
@Table(name = "events")


public class Event {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",  unique = true)
    long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String venue;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private Timestamp eventStartDate;
    @Column(nullable = false)
    private Timestamp eventEndDate;
    @Column(nullable = false)
    private Timestamp eventStartTime;
    @Column(nullable = false)
    private Timestamp eventEndTime;
    @Column(nullable = false)
    private Timestamp ticketSaleStartDateTime;
    @Column(nullable = false)
    private Timestamp ticketSaleEndDateTime;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private float cancellationCost;


    public Event(String name, String description, String venue, String imageUrl, Timestamp eventStartDate,
                 Timestamp eventEndDate, Timestamp eventStartTime, Timestamp eventEndTime, Timestamp ticketSaleStartDateTime,
                 Timestamp ticketSaleEndDateTime, String status, String category, float cancellationCost) {
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.imageUrl = imageUrl;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.ticketSaleStartDateTime = ticketSaleStartDateTime;
        this.ticketSaleEndDateTime = ticketSaleEndDateTime;
        this.status = status;
        this.category = category;
        this.cancellationCost = cancellationCost;
    }

    public Event(){

    }

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVenue() {
        return venue;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Timestamp getEventStartDate() {
        return eventStartDate;
    }

    public Timestamp getEventEndDate() {
        return eventEndDate;
    }

    public Timestamp getEventStartTime() {
        return eventStartTime;
    }

    public Timestamp getEventEndTime() {
        return eventEndTime;
    }

    public Timestamp getTicketSaleStartDateTime() {
        return ticketSaleStartDateTime;
    }

    public Timestamp getTicketSaleEndDateTime() {
        return ticketSaleEndDateTime;
    }

    public String getStatus() {
        return status;
    }

    public String getCategory(){
        return category;
    }

    public float getCancellationCost() {
        return cancellationCost;
    }

}
