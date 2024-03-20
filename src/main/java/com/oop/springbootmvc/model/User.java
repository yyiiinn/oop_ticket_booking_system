package com.oop.springbootmvc.model;

import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import jakarta.persistence.*;

@Entity
@EnableAutoConfiguration
@Table(name = "users")
public class User {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean hasPasswordChange;
    @Column(nullable = false)
    private float balance;
    @ManyToOne
    // @Column(nullable = false)
    Role role;
    public Role getRole() {
        return role;
    }

    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @OneToMany(fetch = FetchType.EAGER, mappedBy="user")
    private Set<Transaction> transactions;
    
    public Set<Transaction> getTransactions() {
        return this.transactions;
    }


    public User(String username, String name, String password, Boolean hasPasswordChange, float balance, Role role) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.hasPasswordChange = hasPasswordChange;
        this.balance = balance;
        this.role = role;
    }

    public User() {

    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean getHasPasswordChange() {
        return this.hasPasswordChange;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHasPasswordChange(boolean change) {
        this.hasPasswordChange = change;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}