package com.fantasticfour.poolapp.CustomResponse;

public class CustomPassenger {
    private int userId;
    private String name;

    public CustomPassenger() {
    }

    public CustomPassenger(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
                "userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
