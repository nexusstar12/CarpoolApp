package com.fantasticfour.poolapp.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profileId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pool_id", referencedColumnName = "poolId")
    private Pool pool;

    // Here, you might want to use an enum or another mechanism to distinguish between passenger and driver.
    // For simplicity, let's use a String. Depending on your actual data model, this might change.
    private String userType;

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
