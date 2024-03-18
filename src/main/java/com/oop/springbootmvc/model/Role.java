package com.oop.springbootmvc.model;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.oop.springbootmvc.entities.RoleEnum;

import jakarta.persistence.*;

@Table(name = "roles")
@EnableAutoConfiguration
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }

}