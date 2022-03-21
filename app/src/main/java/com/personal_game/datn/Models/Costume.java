package com.personal_game.datn.Models;

import java.io.Serializable;

public class Costume implements Serializable {
    private String id ;
    private String name ;
    private int price ;
    private Boolean sex ;
    private String costumeStyleId ;
    private Boolean state ;
    private int quantity ;
    private int quantityBuy ;
    private String description ;

    public Costume(String id, String name, int price, Boolean sex, String costumeStyleId, Boolean state, int quantity, int quantityBuy, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sex = sex;
        this.costumeStyleId = costumeStyleId;
        this.state = state;
        this.quantity = quantity;
        this.quantityBuy = quantityBuy;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getCostumeStyleId() {
        return costumeStyleId;
    }

    public void setCostumeStyleId(String costumeStyleId) {
        this.costumeStyleId = costumeStyleId;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityBuy() {
        return quantityBuy;
    }

    public void setQuantityBuy(int quantityBuy) {
        this.quantityBuy = quantityBuy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
