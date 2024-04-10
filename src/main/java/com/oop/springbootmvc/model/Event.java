package com.oop.springbootmvc.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
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
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String venue;
    @Column(nullable = false)
    private byte[] image;
    @Column(nullable = false)
    private Date eventStartDate;
    @Column(nullable = false)
    private Time eventStartTime;
    @Column(nullable = false)
    private Time eventEndTime;
    @Column(nullable = false)
    private Timestamp ticketSaleStartDateTime;
    @Column(nullable = false)
    private Timestamp ticketSaleEndDateTime;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Float cancellationFee;
    @OneToMany(mappedBy="event")
    private Set<Seat> seats;
    
    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }

    public Event(String name, String description, String venue, byte[] image, Date eventStartDate, Time eventStartTime, Time eventEndTime, Timestamp timestamp, Timestamp timestamp2, String status, String category, Float cancellationFee) {
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.image = image;
        this.eventStartDate = eventStartDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.ticketSaleStartDateTime = timestamp;
        this.ticketSaleEndDateTime = timestamp2;
        this.status = status;
        this.category = category;
        this.cancellationFee = cancellationFee;
    }

    public Event() {

    }

    public long getId() {
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

    public byte[] getImage() {
        return this.image;
    }

    public Date getEventStartDate() {
        return this.eventStartDate;
    }

    public Time getEventStartTime() {
        return this.eventStartTime;
    }

    public Time getEventEndTime() {
        return this.eventEndTime;
    }
    
    public Timestamp getTicketSaleStartDateTime(){
        return this.ticketSaleStartDateTime;
    }

    public Timestamp getTicketSaleEndDateTime(){
        return this.ticketSaleEndDateTime;
    }

    public String getStatus(){
        return this.status;
    }

    public void setId(long result) {
        this.id = result;
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

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public void setEventStartTime(Time eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public void setEventEndTime(Time eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public void setTicketSaleStartTime(Timestamp ticket_sale_start_time) {
        this.ticketSaleStartDateTime = ticket_sale_start_time;
    }

    public void setTicketSaleEndTime(Timestamp ticket_sale_end_time) {
        this.ticketSaleEndDateTime = ticket_sale_end_time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public Float getCancellationFee() {
        return this.cancellationFee;
    }

    public void setCancellationFee(Float cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public String getEventCategory() {
        return this.category;
    }

    public void setEventCategory(String category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", venue='" + venue + '\'' +
                ", image='" + image + '\'' +
                ", eventStartDate=" + eventStartDate +
                ", eventStartTime=" + eventStartTime +
                ", eventEndTime=" + eventEndTime +
                ", ticketSaleStartDateTime=" + ticketSaleStartDateTime +
                ", ticketSaleEndDateTime=" + ticketSaleEndDateTime +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", cancellationFee='" + cancellationFee + '\'' +
                '}';
    }
   
}