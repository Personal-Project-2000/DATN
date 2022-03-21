package com.personal_game.datn.Response;

import java.io.Serializable;

public class Message_Costume implements Serializable {
    private int status;
    private String notification;
    private CostumeInfo costume;

    public Message_Costume(int status, String notification, CostumeInfo costume) {
        this.status = status;
        this.notification = notification;
        this.costume = costume;
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

    public CostumeInfo getCostume() {
        return costume;
    }

    public void setCostume(CostumeInfo costume) {
        this.costume = costume;
    }
}
