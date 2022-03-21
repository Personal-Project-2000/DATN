package com.personal_game.datn.Models;

import java.io.Serializable;

public class StyleDetail implements Serializable {
    private String id ;
    private String costumeId ;
    private String styleId ;

    public StyleDetail(String id, String costumeId, String styleId) {
        this.id = id;
        this.costumeId = costumeId;
        this.styleId = styleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
