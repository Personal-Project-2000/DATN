package com.personal_game.datn.Models;

import java.io.Serializable;

public class PersonalStyle implements Serializable {
    private String id ;
    private String name ;
    private Boolean sex ;
    private String description ;
    private String img ;

    public PersonalStyle(String id, String name, Boolean sex, String description, String img) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.description = description;
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

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
