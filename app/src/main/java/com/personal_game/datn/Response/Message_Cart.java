package com.personal_game.datn.Response;

import java.io.Serializable;
import java.util.List;

public class Message_Cart implements Serializable {
    private int status;
    private String notification;
    private List<Costume_Cart> costumes;

    public Message_Cart(int status, String notification, List<Costume_Cart> costumes) {
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

    public List<Costume_Cart> getCostumes() {
        return costumes;
    }

    public void setCostumes(List<Costume_Cart> costumes) {
        this.costumes = costumes;
    }
}
