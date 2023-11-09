package com.fantasticfour.poolapp.CustomResponse;

public class CustomDriver {

    private int driverId;
    private String name;

    public CustomDriver() {
    }

    public CustomDriver(int driverId, String name) {
        this.driverId = driverId;
        this.name = name;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CustomDriver{" +
                "driverId=" + driverId +
                ", name='" + name + '\'' +
                '}';
    }
}
