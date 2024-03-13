package com.oop.springbootmvc.viewmodel;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventViewModel {
    private long id;
    private String eventName;
    private String eventDescription;
    private String eventCategory;
    private String eventVenue;
    private String eventImageFile;
    private Date eventDate;
    private Time eventStartTime;
    private Time eventEndTime;
    // private Date salesStartDate;
    // private Date salesEndDate;
    private Timestamp salesStartTime;
    private Timestamp salesEndTime;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String geEventImageFile() {
        return eventImageFile;
    }

    public void setEventImageFile(String eventImageFile) {
        this.eventImageFile = eventImageFile;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Time getEventStartTime() {
        return this.eventStartTime;
    }

    @JsonProperty("eventStartTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    public void setEventStartTime(String timeString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            java.util.Date parsedDate = dateFormat.parse(timeString);
            this.eventStartTime = new Time(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Time getEventEndTime() {
        return this.eventEndTime;
    }

    @JsonProperty("eventEndTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    public void setEventEndTime(String timeString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            java.util.Date parsedDate = dateFormat.parse(timeString);
            this.eventEndTime = new Time(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Timestamp getSalesStartTime() {
        return salesStartTime;
    }

    public void setSalesStartTime(Timestamp salesStartTime) {
        this.salesStartTime = salesStartTime;
    }

    public Timestamp getSalesEndTime() {
        return salesEndTime;
    }

    public void setSalesEndTime(Timestamp salesEndTime) {
        this.salesEndTime = salesEndTime;
    }

    public Timestamp getTicketSaleStartDateTime() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTicketSaleStartDateTime'");
    }

    public String getStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStatus'");
    }
}
