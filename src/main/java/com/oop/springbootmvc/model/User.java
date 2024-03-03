package com.oop.springbootmvc.model;

import jakarta.persistence.*;

@Entity
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
    private String role;
    @Column(nullable = false)
    private Boolean hasPasswordChange;
    @Column(nullable = false)
    private float balance;
    public User(String name, String role, String username, String password,  Boolean hasPasswordChange, float balance) {

        this.name = name;
        this.role = role;
        this.balance = balance;
        this.password = password;
        this.hasPasswordChange = hasPasswordChange;
        this.username = username;
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

    public String getRole() {
        return this.role;
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

    public void setRole(String role) {
        this.role = role;
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