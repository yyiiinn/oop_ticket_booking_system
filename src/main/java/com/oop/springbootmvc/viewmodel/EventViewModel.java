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
    private int id;
    private String eventName;
    private String eventDescription;
    private String eventCategory;
    private String eventVenue;
    private String eventImageFile;
    private Date eventStartDate;
    private Date eventEndDate;
    private Time eventStartTime;
    private Time eventEndTime;
    // private Date salesStartDate;
    // private Date salesEndDate;
    private Timestamp salesStartTime;
    private Timestamp salesEndTime;
    private float cancellationFee;
    private List<SitViewModel> seatingOptions;

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

    public Date getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public Date getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Date EventEndDate) {
        this.eventEndDate = eventEndDate;
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

    public List<SitViewModel> getSeatingOptions() {
        return seatingOptions;
    }

    public void setSeatingOptions(List<SitViewModel> seatingOptions) {
        this.seatingOptions = seatingOptions;
    }

    public float getCancellationFee() {
        return this.cancellationFee;
    }

    public void setCancellationFee(float cancellationFee) {
        this.cancellationFee = cancellationFee;
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
