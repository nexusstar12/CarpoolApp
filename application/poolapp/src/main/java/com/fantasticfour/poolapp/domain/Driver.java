package com.fantasticfour.poolapp.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "driverId")

public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;

    //added to fix infinit serialization
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean fastrakVerification;

    @Column(nullable = false)
    private String driversLicense;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    private Profile profile;

    // Getters and setters

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter and Setter for fastrakVerification
    public boolean isFastrakVerification() {
        return fastrakVerification;
    }

    public void setFastrakVerification(boolean fastrakVerification) {
        this.fastrakVerification = fastrakVerification;
    }

    // Getter and Setter for driversLicense
    public String getDriversLicense() {
        return driversLicense;
    }

    public void setDriversLicense(String driversLicense) {
        this.driversLicense = driversLicense;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
