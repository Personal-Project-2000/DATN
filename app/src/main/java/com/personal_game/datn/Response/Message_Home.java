package com.personal_game.datn.Response;

import java.io.Serializable;

public class Message_Home implements Serializable {
    private int status;
    private String notification;
    private Home home;

    public Message_Home(int status, String notification, Home home) {
        this.status = status;
        this.notification = notification;
        this.home = home;
    }

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

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }
}
