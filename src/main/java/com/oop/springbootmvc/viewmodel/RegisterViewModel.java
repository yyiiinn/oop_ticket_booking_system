package com.oop.springbootmvc.viewmodel;

public class RegisterViewModel {
    private String username;
    private String name;
    private String password;

    public RegisterViewModel(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public RegisterViewModel() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
