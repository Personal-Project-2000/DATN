package com.personal_game.datn.Response;

import java.io.Serializable;

public class Message implements Serializable {
    private int status;
    private String notification;
    private String id;

    public Message(int status, String notification, String id) {
        this.status = status;
        this.notification = notification;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
