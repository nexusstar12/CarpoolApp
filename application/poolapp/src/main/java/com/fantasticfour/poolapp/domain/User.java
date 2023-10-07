package com.fantasticfour.poolapp.domain;

import jakarta.persistence.*;

@Entity // This annotation marks this class as a JPA entity
public class User {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use database to auto-generate this value
    private int user_id;

    private String name;

    private String email;

    // Standard getters and setters go here

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
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