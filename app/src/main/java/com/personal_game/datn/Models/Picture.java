package com.personal_game.datn.Models;

import java.io.Serializable;

public class Picture implements Serializable {
    private String link ;
    private String path ;

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
}
