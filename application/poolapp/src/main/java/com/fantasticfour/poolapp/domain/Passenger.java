package com.fantasticfour.poolapp.domain;

import jakarta.persistence.*;

@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id", nullable = false)
    private int passengerId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    //Getters and Setters
    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
