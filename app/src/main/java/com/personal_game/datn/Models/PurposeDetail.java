package com.personal_game.datn.Models;

import java.io.Serializable;

public class PurposeDetail implements Serializable {
    private String id ;
    private String costumeId ;
    private String purposeId ;

    public PurposeDetail(String id, String costumeId, String purposeId) {
        this.id = id;
        this.costumeId = costumeId;
        this.purposeId = purposeId;
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

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }
}
