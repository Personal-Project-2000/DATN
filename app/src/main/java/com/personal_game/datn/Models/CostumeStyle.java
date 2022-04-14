package com.personal_game.datn.Models;

import java.io.Serializable;
import java.util.List;

public class CostumeStyle implements Serializable {
    private String id ;
    private String name ;
    private String img ;
    private List<Size> menSizes;
    private List<Size> womenSizes;

    public CostumeStyle() {
    }

    public CostumeStyle(String id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public List<Size> getMenSizes() {
        return menSizes;
    }

    public void setMenSizes(List<Size> menSizes) {
        this.menSizes = menSizes;
    }

    public List<Size> getWomenSizes() {
        return womenSizes;
    }

    public void setWomenSizes(List<Size> womenSizes) {
        this.womenSizes = womenSizes;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
