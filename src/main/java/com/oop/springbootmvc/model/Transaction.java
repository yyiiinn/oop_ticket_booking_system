package com.oop.springbootmvc.model;
import java.sql.Timestamp;

import ch.qos.logback.core.joran.action.TimestampAction;
import jakarta.persistence.*;

@Entity
@Table(name = "transactions")

public class Transaction {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",  unique = true)
    long id;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private long customerId;
    @Column(nullable = false)
    private long sitId;
    @Column(nullable = false)
    private Timestamp purchasedDateTime;
    @Column(nullable = false)
    private float cost;
    @Column(nullable = false)
    private float cancellationCost;




    public Transaction(String status, long customerId, long sitId, Timestamp purchasedDateTime, float cost, float cancellationCost) {

      this.status =status;
      this.customerId = customerId;
      this.sitId = sitId;
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

    // Getter for customerId
    public long getCustomerId() {
        return customerId;
    }

    // Getter for sitsId
    public long getSitId() {
        return sitId;
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
    
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    
    public void setSitId(long sitId) {
        this.sitId = sitId;
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
