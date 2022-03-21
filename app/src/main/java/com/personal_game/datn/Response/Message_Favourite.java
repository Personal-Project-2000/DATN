package com.personal_game.datn.Response;

import java.io.Serializable;

public class Message_Favourite implements Serializable {
    private int status;
    private String notification;
    private CostumeFavourite costumes;

    public Message_Favourite(int status, String notification, CostumeFavourite costumes) {
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

    public CostumeFavourite getCostumes() {
        return costumes;
    }

    public void setCostumes(CostumeFavourite costumes) {
        this.costumes = costumes;
    }
}
