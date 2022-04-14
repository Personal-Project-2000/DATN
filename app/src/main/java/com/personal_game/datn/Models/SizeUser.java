package com.personal_game.datn.Models;

import java.io.Serializable;

public class SizeUser implements Serializable {
    private String costumeId;
    private String size;

    public SizeUser(String costumeId, String size) {
        this.costumeId = costumeId;
        this.size = size;
    }

    public String getCostumeId() {
        return costumeId;
    }

    public void setCostumeId(String costumeId) {
        this.costumeId = costumeId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
