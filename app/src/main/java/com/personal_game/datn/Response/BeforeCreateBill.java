package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Promotion;

import java.util.List;

public class BeforeCreateBill {
    private int status;
    private String notification;
    private List<Promotion> data;

    public BeforeCreateBill(int status, String notification, List<Promotion> data) {
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

    public List<Promotion> getData() {
        return data;
    }

    public void setData(List<Promotion> data) {
        this.data = data;
    }
}
