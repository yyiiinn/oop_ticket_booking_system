package com.oop.springbootmvc.model;


import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")

public class Ticket {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",  unique = true)
    long id;
    @Column(nullable = false)
    private String guid;
    @Column(nullable = false)
    private long transactionId;
    @Column(nullable = false)
    private String status;

    // Constructor
    public Ticket(String guid, long transactionId, String status) {

        this.guid = guid;
        this.transactionId = transactionId;
        this.status = status;
    }

    public Ticket(){

    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getGuid() {
        return guid;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public String getStatus() {
        return status;
    }
}

    

