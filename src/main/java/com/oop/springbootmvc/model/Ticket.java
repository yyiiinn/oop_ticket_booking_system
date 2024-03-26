package com.oop.springbootmvc.model;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
@EnableAutoConfiguration

public class Ticket {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    long id;
    @Column(nullable = false)
    private String guid;

    @Column(nullable = false)
    private String status;
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    // Constructor
    public Ticket(String guid, String status) {

        this.guid = guid;
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

    public String getStatus() {
        return status;
    }
}