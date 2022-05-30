package com.personal_game.datn.Models;

import java.io.Serializable;
import java.util.List;

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
    private List<String> bodies;
    private List<String> personalStyles;
    private List<String> purposes;
    private List<Picture> pictures;
    private List<Picture> picture_Nones;
    private List<ColorObject> colors;
    private List<Size> sizes;
    private Promotion promotion;

    public List<Picture> getPicture_Nones() {
        return picture_Nones;
    }

    public void setPicture_Nones(List<Picture> picture_Nones) {
        this.picture_Nones = picture_Nones;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public List<String> getBodies() {
        return bodies;
    }

    public void setBodies(List<String> bodies) {
        this.bodies = bodies;
    }

    public List<String> getPersonalStyles() {
        return personalStyles;
    }

    public void setPersonalStyles(List<String> personalStyles) {
        this.personalStyles = personalStyles;
    }

    public List<String> getPurposes() {
        return purposes;
    }

    public void setPurposes(List<String> purposes) {
        this.purposes = purposes;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
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

    public List<ColorObject> getColors() {
        return colors;
    }

    public void setColors(List<ColorObject> colors) {
        this.colors = colors;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }
}
