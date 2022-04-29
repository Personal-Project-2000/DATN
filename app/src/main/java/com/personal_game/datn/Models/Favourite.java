package com.personal_game.datn.Models;

import java.io.Serializable;

public class Favourite implements Serializable {
    private String id ;
    private String userId ;
    private String costumeId ;
    private String date ;

    public Favourite(String id, String userId, String costumeId, String date) {
        this.id = id;
        this.userId = userId;
        this.costumeId = costumeId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCostumeId() {
        return costumeId;
    }

    public void setCostumeId(String costumeId) {
        this.costumeId = costumeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
