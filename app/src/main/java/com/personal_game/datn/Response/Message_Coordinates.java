package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Coordinate;

import java.util.List;

public class Message_Coordinates {
    private int status;
    private String notification;
    private List<Coordinate> coordinates;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public Message_Coordinates(int status, String notification, List<Coordinate> coordinates) {
        this.status = status;
        this.notification = notification;
        this.coordinates = coordinates;
    }
}
