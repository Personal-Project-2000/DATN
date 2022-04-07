package com.personal_game.datn.Models;

import java.io.Serializable;

public class BillDetail implements Serializable {
    private String costumeId ;
    private int quantity ;
    private int price ;

    public BillDetail(String costumeId, int quantity, int price) {
        this.costumeId = costumeId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCostumeId() {
        return costumeId;
    }

    public void setCostumeId(String costumeId) {
        this.costumeId = costumeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
