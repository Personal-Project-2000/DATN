package com.personal_game.datn.Models;

import java.io.Serializable;

public class Costume implements Serializable {
    private String Id ;
    private String Name ;
    private int Price ;
    private Boolean Sex ;
    private String CostumeStyleId ;
    private Boolean State ;
    private int Quantity ;
    private int QuantityBuy ;
    private String Description ;

    public Costume(String id, String name, int price, Boolean sex, String costumeStyleId, Boolean state, int quantity, int quantityBuy, String description) {
        Id = id;
        Name = name;
        Price = price;
        Sex = sex;
        CostumeStyleId = costumeStyleId;
        State = state;
        Quantity = quantity;
        QuantityBuy = quantityBuy;
        Description = description;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public Boolean getSex() {
        return Sex;
    }

    public void setSex(Boolean sex) {
        Sex = sex;
    }

    public String getCostumeStyleId() {
        return CostumeStyleId;
    }

    public void setCostumeStyleId(String costumeStyleId) {
        CostumeStyleId = costumeStyleId;
    }

    public Boolean getState() {
        return State;
    }

    public void setState(Boolean state) {
        State = state;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getQuantityBuy() {
        return QuantityBuy;
    }

    public void setQuantityBuy(int quantityBuy) {
        QuantityBuy = quantityBuy;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
