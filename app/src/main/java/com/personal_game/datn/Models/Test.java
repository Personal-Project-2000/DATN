package com.personal_game.datn.Models;

import java.io.Serializable;

public class Test implements Serializable {
    private String id ;
    private String name ;
    private boolean sex;

    public Test(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Test(String id, String name, boolean sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
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

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
