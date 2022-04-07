package com.personal_game.datn.Models;

import java.io.Serializable;

public class Answer implements Serializable {
    private String name ;
    private String content ;
    private String img ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
