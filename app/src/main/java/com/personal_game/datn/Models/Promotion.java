package com.personal_game.datn.Models;

import java.io.Serializable;

public class Promotion implements Serializable {
    private String id;
    private String startTime;
    private String endTime;
    private int value;
    private String name;
    private String promotionTypeId;
    private String icon;
    private String color;
    private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPromotionTypeId() {
        return promotionTypeId;
    }

    public void setPromotionTypeId(String promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }
}
