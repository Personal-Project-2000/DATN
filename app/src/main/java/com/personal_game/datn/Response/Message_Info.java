package com.personal_game.datn.Response;

import java.io.Serializable;

public class Message_Info implements Serializable {
    private int status;
    private String notification;
    private UserInfo user;

    public Message_Info(int status, String notification, UserInfo user) {
        this.status = status;
        this.notification = notification;
        this.user = user;
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

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
