package com.personal_game.datn.Models;

import java.io.Serializable;

public class Cart implements Serializable {
    private String id ;
    private String costumeId ;
    private String userId ;
    private int quantity ;
    private Boolean state ;
    private ColorObject color;
    private String size;

    public Cart(String costumeId, int quantity, Boolean state) {
        this.costumeId = costumeId;
        this.quantity = quantity;
        this.state = state;
    }

    public Cart(String costumeId, String userId, int quantity, Boolean state) {
        this.costumeId = costumeId;
        this.userId = userId;
        this.quantity = quantity;
        this.state = state;
    }

    public Cart(String id, String costumeId, String userId, int quantity, Boolean state) {
        this.id = id;
        this.costumeId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public ColorObject getColor() {
        return color;
    }

    public void setColor(ColorObject color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
