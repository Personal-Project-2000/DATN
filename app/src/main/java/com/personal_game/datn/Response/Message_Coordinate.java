package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Coordinate;

import java.util.List;

public class Message_Coordinate {
    private int status;
    private String notification;
    private Coordinate coordinate;

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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Message_Coordinate(int status, String notification, Coordinate coordinate) {
        this.status = status;
        this.notification = notification;
        this.coordinate = coordinate;
    }
}
