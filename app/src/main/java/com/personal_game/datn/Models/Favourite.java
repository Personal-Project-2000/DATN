package com.personal_game.datn.Models;

import java.io.Serializable;

public class Favourite implements Serializable {
    private String id ;
    private String account ;
    private String costumeId ;
    private String date ;

    public Favourite(String id, String account, String costumeId, String date) {
        this.id = id;
        this.account = account;
        this.costumeId = costumeId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
