package com.personal_game.datn.Models;

import java.io.Serializable;

public class Picture implements Serializable {
    private String link ;
    private String path ;
    private String codeColor;

    public String getCodeColor() {
        return codeColor;
    }

    public void setCodeColor(String codeColor) {
        this.codeColor = codeColor;
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
}
