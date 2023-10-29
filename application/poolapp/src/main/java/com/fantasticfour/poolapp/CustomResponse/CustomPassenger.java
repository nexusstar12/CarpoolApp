package com.fantasticfour.poolapp.CustomResponse;

public class CustomPassenger {
    private int passengerId;
    private String name;

    public CustomPassenger() {
    }

    public CustomPassenger(int passengerId, String name) {
        this.passengerId = passengerId;
        this.name = name;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CustomPassenger{" +
                "userId=" + passengerId +
                ", name='" + name + '\'' +
                '}';
    }
}
