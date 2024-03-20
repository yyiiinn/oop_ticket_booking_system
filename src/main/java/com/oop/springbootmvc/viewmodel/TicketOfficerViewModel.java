package com.oop.springbootmvc.viewmodel;

import com.oop.springbootmvc.model.User;

public class TicketOfficerViewModel {
    private long id;
    private String name;
    private String email;
    public TicketOfficerViewModel() {
    }
    public TicketOfficerViewModel(User u) {
        this.id = u.getId();
        this.name = u.getName();
        this.email = u.getUsername();
    }
    public TicketOfficerViewModel(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
