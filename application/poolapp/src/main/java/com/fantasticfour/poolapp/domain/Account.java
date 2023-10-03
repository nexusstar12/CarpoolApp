package com.fantasticfour.poolapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;

@Entity
public class Account extends User {

    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id")
    private Password password;

    // Getters Setters
}
