package com.personal_game.datn.Models;

import java.io.Serializable;

public class Body implements Serializable {
    private String id ;
    private String name ;
    private String description ;
    private Boolean sex ;
    private String img ;

    public Body(String id, String name, String description, Boolean sex, String img) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sex = sex;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
