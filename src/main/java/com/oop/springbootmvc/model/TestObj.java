package com.oop.springbootmvc.model;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import jakarta.persistence.*;

@Entity
@EnableAutoConfiguration
@Table(name = "test")
public class TestObj {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    long id;
    private String name;
    private String role;

    public TestObj(String name, String role) {
  
      this.name = name;
      this.role = role;
    }

    public TestObj() {

    }

    public Long getId() {
      return this.id;
    }
  
    public String getName() {
      return this.name;
    }
  
    public String getRole() {
      return this.role;
    }
  
    public void setId(Long id) {
      this.id = id;
    }
  
    public void setName(String name) {
      this.name = name;
    }
  
    public void setRole(String role) {
      this.role = role;
    }
}