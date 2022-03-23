package com.personal_game.datn.Models;

import java.io.Serializable;

public class Cart implements Serializable {
    private String id ;
    private String costumeId ;
    private String account ;
    private int quantity ;
    private Boolean state ;

    public Cart(String costumeId, int quantity, Boolean state) {
        this.costumeId = costumeId;
        this.quantity = quantity;
        this.state = state;
    }

    public Cart(String costumeId, String account, int quantity, Boolean state) {
        this.costumeId = costumeId;
        this.account = account;
        this.quantity = quantity;
        this.state = state;
    }

    public Cart(String id, String costumeId, String account, int quantity, Boolean state) {
        this.id = id;
        this.costumeId = costumeId;
        this.account = account;
        this.quantity = quantity;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCostumeId() {
        return costumeId;
    }

    public void setCostumeId(String costumeId) {
        this.costumeId = costumeId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
