package com.personal_game.datn.Response;

import java.util.List;

public class Message_CostumeWithStyle {
    private int status;
    private String notification;
    private List<CostumeWithStyle> costumes;

    public Message_CostumeWithStyle(int status, String notification, List<CostumeWithStyle> costumes) {
        this.status = status;
        this.notification = notification;
        this.costumes = costumes;
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

    public List<CostumeWithStyle> getCostumes() {
        return costumes;
    }

    public void setCostumes(List<CostumeWithStyle> costumes) {
        this.costumes = costumes;
    }
}
