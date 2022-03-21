package com.personal_game.datn.Models;

import java.io.Serializable;

public class Picture implements Serializable {
    private String id ;
    private String link ;
    private String path ;
    private String costumeId ;

    public Picture(String id, String link, String path, String costumeId) {
        this.id = id;
        this.link = link;
        this.path = path;
        this.costumeId = costumeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCostumeId() {
        return costumeId;
    }

    public void setCostumeId(String costumeId) {
        this.costumeId = costumeId;
    }
}
