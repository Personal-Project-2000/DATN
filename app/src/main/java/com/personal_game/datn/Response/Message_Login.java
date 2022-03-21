package com.personal_game.datn.Response;

import java.io.Serializable;

public class Message_Login implements Serializable {
    private int status;
    private String notification;
    private Data data;

    public Message_Login(int status, String notification, Data data) {
        this.status = status;
        this.notification = notification;
        this.data = data;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
