package com.oop.springbootmvc.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class Event {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String venue;
    @Column(nullable = false)
    private byte[] image;
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
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Float cancellation_fee;
    @Column(nullable = false)
    private String image_name;
    public Event(String name, String description, String venue, byte[] image, Date event_start_date, Date event_end_date, Time event_start_time, Time event_end_time, Timestamp timestamp, Timestamp timestamp2, String status, String category, Float cancellationFee, String image_name) {
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.image = image;
        this.event_start_date = event_start_date;
        this.event_end_date = event_end_date;
        this.event_start_time = event_start_time;
        this.event_end_time = event_end_time;
        this.ticket_sale_start_date_time = timestamp;
        this.ticket_sale_end_date_time = timestamp2;
        this.status = status;
        this.category = category;
        this.cancellation_fee = cancellationFee;
        this.image_name = image_name;
    }

    public Event() {

    }

    public int getId() {
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
        Timestamp originalTimestamp = this.ticket_sale_start_date_time;
        long originalTimeMillis = originalTimestamp.getTime();
        long utcPlus8OffsetMillis = 8 * 60 * 60 * 1000;
        long timeInUtcPlus8Millis = originalTimeMillis + utcPlus8OffsetMillis;
        Timestamp utcPlus8Timestamp = new Timestamp(timeInUtcPlus8Millis);
        return utcPlus8Timestamp;
    }

    public Timestamp getTicketSaleEndDateTime(){
        Timestamp originalTimestamp = this.ticket_sale_end_date_time;
        long originalTimeMillis = originalTimestamp.getTime();
        long utcPlus8OffsetMillis = 8 * 60 * 60 * 1000;
        long timeInUtcPlus8Millis = originalTimeMillis + utcPlus8OffsetMillis;
        Timestamp utcPlus8Timestamp = new Timestamp(timeInUtcPlus8Millis);
        return utcPlus8Timestamp;
    }

    public String getStatus(){
        return this.status;
    }

    public void setId(int id) {
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

    public void setImage(byte[] image) {
        this.image = image;
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

    public void setTicketSaleStartTime(Timestamp ticket_sale_start_time) {
        this.ticket_sale_start_date_time = ticket_sale_start_time;
    }

    public void setTicketSaleEndTime(Timestamp ticket_sale_end_time) {
        this.ticket_sale_end_date_time = ticket_sale_end_time;
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
        return this.cancellation_fee;
    }

    public void setCancellationFee(Float cancellationFee) {
        this.cancellation_fee = cancellationFee;
    }

    public String getEventCategory() {
        return this.category;
    }

    public void setEventCategory(String category) {
        this.category = category;
    }

    public String getImageName() {
        return this.image_name;
    }

    public void setImageName(String image_name) {
        this.image_name = image_name;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", venue='" + venue + '\'' +
                ", image='" + image + '\'' +
                ", event_start_date=" + event_start_date +
                ", event_end_date=" + event_end_date +
                ", event_start_time=" + event_start_time +
                ", event_end_time=" + event_end_time +
                ", ticket_sale_start_date_time=" + ticket_sale_start_date_time +
                ", ticket_sale_end_date_time=" + ticket_sale_end_date_time +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", cancellation_fee='" + cancellation_fee + '\'' +
                ", image_name='" + image_name + '\'' +
                '}';
    }
   
}