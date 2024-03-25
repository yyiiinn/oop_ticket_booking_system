package com.oop.springbootmvc.viewmodel;
import java.sql.Date;
import java.sql.Time;
import java.util.List;


public class CustomerBookingDetailsViewModel{
    private String eventName;
    private Date eventStartDate;
    private Time eventStartTime;
    private Time eventEndTime;   
    private List<CustomerTicketViewModel> tickets;
    
    public CustomerBookingDetailsViewModel(String eventName, Date eventStartDate, Time eventStartTime,
            Time eventEndTime, List<CustomerTicketViewModel> tickets) {
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.tickets = tickets;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
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
    public List<CustomerTicketViewModel> getTickets() {
        return tickets;
    }
    public void setTickets(List<CustomerTicketViewModel> tickets) {
        this.tickets = tickets;
    }

   
    
}
