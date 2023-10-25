package com.fantasticfour.poolapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ZipData {
   @Id
    private String zipCode;

    private double longitude;

    private double latitude;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
