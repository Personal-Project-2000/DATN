package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Address;

import java.io.Serializable;
import java.util.List;

public class Message_Address implements Serializable {
    private int status;
    private String notification;
    private List<Address> addresses;

    public Message_Address(int status, String notification, List<Address> addresses) {
        this.status = status;
        this.notification = notification;
        this.addresses = addresses;
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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
