package com.personal_game.datn.Models;

import java.io.Serializable;

public class BillDetail implements Serializable {
    private String costumeId ;
    private String name;
    private String image;
    private int quantity ;
    private int price ;
    private ColorObject color;
    private String size;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
