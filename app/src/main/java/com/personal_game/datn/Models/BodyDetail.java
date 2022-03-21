package com.personal_game.datn.Models;

import java.io.Serializable;

public class BodyDetail implements Serializable {
    private String id ;
    private String costumeId ;
    private String bodyId ;

    public BodyDetail(String id, String costumeId, String bodyId) {
        this.id = id;
        this.costumeId = costumeId;
        this.bodyId = bodyId;
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

    public String getBodyId() {
        return bodyId;
    }

    public void setBodyId(String bodyId) {
        this.bodyId = bodyId;
    }
}
