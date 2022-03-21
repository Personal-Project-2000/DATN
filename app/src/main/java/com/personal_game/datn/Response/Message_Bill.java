package com.personal_game.datn.Response;

import java.io.Serializable;
import java.util.List;

public class Message_Bill implements Serializable {
    private int status;
    private String notification;
    private List<BillInfo> bills;

    public Message_Bill(int status, String notification, List<BillInfo> bills) {
        this.status = status;
        this.notification = notification;
        this.bills = bills;
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

    public List<BillInfo> getBills() {
        return bills;
    }

    public void setBills(List<BillInfo> bills) {
        this.bills = bills;
    }
}
