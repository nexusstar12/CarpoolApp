package com.fantasticfour.poolapp.CustomResponse;

import com.fantasticfour.poolapp.domain.Driver;
import com.fantasticfour.poolapp.domain.Passenger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PoolResponse {

    private String description;
    private int poolId;
    private String startLocation;
    private String endLocation;
    private LocalDateTime startTime;

    private CustomDriver driver;

    private List<CustomPassenger> passengers;

    public PoolResponse() {
        passengers = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    // Add the setter for the description field
    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoolId() {
        return poolId;
    }

    public void setPoolId(int poolId) {
        this.poolId = poolId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public CustomDriver getDriver() {
        return driver;
    }

    public void setDriver(CustomDriver driver) {
        this.driver = driver;
    }

    public List<CustomPassenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<CustomPassenger> passengers) {
        this.passengers = passengers;
    }

    public void addPassenger (CustomPassenger passenger) {
        this.passengers.add(passenger);
    };

    @Override
    public String toString() {
        return "PoolResponse{" +
                "poolId=" + poolId +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", startTime=" + startTime +
                ", driver=" + driver +
                ", passengers=" + passengers +
                '}';
    }
}
