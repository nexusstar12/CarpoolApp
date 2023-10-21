package com.fantasticfour.poolapp.domain;

import jakarta.persistence.*;

@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean fastrakVerification;

    @Column(nullable = false)
    private String driversLicense;

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
}
