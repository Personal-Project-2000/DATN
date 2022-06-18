package com.personal_game.datn.Models;

import com.personal_game.datn.Response.CostumeHome;

import java.io.Serializable;

public class CostumeCoordinate implements Serializable {
    private String costumeId;
    private String styleId;
    private String image;
    private ColorObject color;
    private Costume costumeInfo;

    public CostumeCoordinate(String costumeId, String styleId, String image, ColorObject color, Costume costumeInfo) {
        this.costumeId = costumeId;
        this.styleId = styleId;
        this.image = image;
        this.color = color;
        this.costumeInfo = costumeInfo;
    }

    public Costume getCostumeInfo() {
        return costumeInfo;
    }

    public void setCostumeInfo(Costume costumeInfo) {
        this.costumeInfo = costumeInfo;
    }

    public String getCostumeId() {
        return costumeId;
    }

    public void setCostumeId(String costumeId) {
        this.costumeId = costumeId;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ColorObject getColor() {
        return color;
    }

    public void setColor(ColorObject color) {
        this.color = color;
    }
}
