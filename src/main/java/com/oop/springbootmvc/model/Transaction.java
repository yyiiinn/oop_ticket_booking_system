package com.oop.springbootmvc.model;
import java.sql.Timestamp;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
@EnableAutoConfiguration
public class Transaction {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    long id;
    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    public void setSeat(Seat seat) {
        this.seat = seat;
    }
    public Seat getSeat() {
        return this.seat;
    }
    @Column(nullable = false)
    private Timestamp purchasedDateTime;
    @Column(nullable = false)
    private float cost;
    @Column(nullable = false)
    private float cancellationCost;

    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @OneToMany(fetch = FetchType.EAGER, mappedBy="transaction")
    private Set<Ticket> tickets;
    
    public Set<Ticket> getTickets() {
        return tickets;
    }
    
    public Transaction(String status, Timestamp purchasedDateTime, float cost, float cancellationCost) {

      this.status =status;
      this.purchasedDateTime = purchasedDateTime;
      this.cost = cost;
      this.cancellationCost = cancellationCost;

    }

    public Transaction() {
    }
    
    public Long getId() {
        return this.id;
    }

    // Getter for status
    public String getStatus() {
        return status;
    }

    // Getter for purchasedDateTime
    public Timestamp getPurchasedDateTime() {
        return purchasedDateTime;
    }

    // Getter for cost
    public float getCost() {
        return cost;
    }

    public float getCancellationCost(){
        return cancellationCost;
    }



    public void setId(long id) {
        this.id = id;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    
    public void setPurchasedDateTime(Timestamp purchasedDateTime) {
        this.purchasedDateTime = purchasedDateTime;
    }
    
    public void setCost(float cost) {
        this.cost = cost;
    }
    
    public void setCancellationCost(float cancellationCost){
        this.cancellationCost = cancellationCost;
    }

    
    
    

}