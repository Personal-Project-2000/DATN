package com.personal_game.datn.Request;

import com.personal_game.datn.Models.ColorObject;

public class Request_CostumeBill {
    private String costumeId ;
    private int quantity ;
    private ColorObject color;
    private String size;

    public Request_CostumeBill(String costumeId, int quantity, ColorObject color, String size) {
        this.costumeId = costumeId;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
    }

    public Request_CostumeBill(String costumeId, int quantity) {
        this.costumeId = costumeId;
        this.quantity = quantity;
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
}
