package com.personal_game.datn.Request;

public class Request_CostumeBill {
    private String costumeId ;
    private int quantity ;

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
