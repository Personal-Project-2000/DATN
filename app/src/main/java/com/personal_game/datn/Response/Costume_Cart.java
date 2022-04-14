package com.personal_game.datn.Response;

import com.personal_game.datn.Models.ColorObject;
import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.Models.Picture;

import java.io.Serializable;

public class Costume_Cart implements Serializable {
    private Costume costume ;
    //số lượng được trong giỏ hàng
    private int quantity ;
    private Boolean state ;
    private ColorObject color;
    private String size;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Costume getCostume() {
        return costume;
    }

    public void setCostume(Costume costume) {
        this.costume = costume;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
