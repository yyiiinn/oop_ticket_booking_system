package com.oop.springbootmvc.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DashboardDataDTO {
    @Id
    private Long ticketId;
    private String ticketGuid;
    private String ticketStatus;

    private Long transactionId;
    private Float transactionCancellationCost;
    private String transactionStatus;
    private Timestamp transactionPurchasedDateTime;
    private Float transactionCost;

    private int seatId;
    private String seatType;
    private Float seatCost;
    private Integer numberOfSeats;

    private Long eventId;
    private String eventName;
    private String eventDescription;
    private String eventCategory;
    private String eventVenue;
    private float eventCancellationFee;
    private Date eventStartDate;
    private Time eventStartTime;
    private Time eventEndTime;
    private Timestamp eventTicketSaleStartDateTime;
    private Timestamp eventTicketSaleEndDateTime;
    private String eventStatus;

    //constructor
    public DashboardDataDTO(Long ticketId, String ticketGuid, String ticketStatus, Long transactionId,
        Float transactionCancellationCost, String transactionStatus, Timestamp transactionPurchasedDateTime,
        Float transactionCost, int seatId, String seatType, Float seatCost, Integer numberOfSeats, Long eventId, 
        String eventName, String eventDescription, String eventCategory, String eventVenue, float eventCancellationFee,
        Date eventStartDate, Time eventStartTime, Time eventEndTime,Timestamp eventTicketSaleStartDateTime,
        Timestamp eventTicketSaleEndDateTime, String eventStatus) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventCategory = eventCategory;
        this.eventVenue = eventVenue;
        this.eventCancellationFee = eventCancellationFee;
        this.eventStartDate = eventStartDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventTicketSaleStartDateTime = eventTicketSaleStartDateTime;
        this.eventTicketSaleEndDateTime = eventTicketSaleEndDateTime;
        this.eventStatus = eventStatus;
        this.seatId = seatId;
        this.seatType = seatType;
        this.seatCost = seatCost;
        this.numberOfSeats = numberOfSeats;
        this.transactionId = transactionId;
        this.transactionCancellationCost = transactionCancellationCost;
        this.transactionStatus = transactionStatus;
        this.transactionPurchasedDateTime = transactionPurchasedDateTime;
        this.transactionCost = transactionCost;
        this.ticketId = ticketId;
        this.ticketGuid = ticketGuid;
        this.ticketStatus = ticketStatus;
    }

    //getter and setter
    
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketGuid() {
        return ticketGuid;
    }

    public void setTicketGuid(String ticketGuid) {
        this.ticketGuid = ticketGuid;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Float getTransactionCancellationCost() {
        return transactionCancellationCost;
    }

    public void setTransactionCancellationCost(Float transactionCancellationCost) {
        this.transactionCancellationCost = transactionCancellationCost;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Timestamp getTransactionPurchasedDateTime() {
        return transactionPurchasedDateTime;
    }

    public void setTransactionPurchasedDateTime(Timestamp transactionPurchasedDateTime) {
        this.transactionPurchasedDateTime = transactionPurchasedDateTime;
    }

    public Float getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(Float transactionCost) {
        this.transactionCost = transactionCost;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public Float getSeatCost() {
        return seatCost;
    }

    public void setSeatCost(Float seatCost) {
        this.seatCost = seatCost;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public float getEventCancellationFee() {
        return eventCancellationFee;
    }

    public void setEventCancellationFee(float eventCancellationFee) {
        this.eventCancellationFee = eventCancellationFee;
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

    public Timestamp getEventTicketSaleStartDateTime() {
        return eventTicketSaleStartDateTime;
    }

    public void setEventTicketSaleStartDateTime(Timestamp eventTicketSaleStartDateTime) {
        this.eventTicketSaleStartDateTime = eventTicketSaleStartDateTime;
    }

    public Timestamp getEventTicketSaleEndDateTime() {
        return eventTicketSaleEndDateTime;
    }

    public void setEventTicketSaleEndDateTime(Timestamp eventTicketSaleEndDateTime) {
        this.eventTicketSaleEndDateTime = eventTicketSaleEndDateTime;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
}
