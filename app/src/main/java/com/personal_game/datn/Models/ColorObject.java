package com.personal_game.datn.Models;

import java.io.Serializable;

public class ColorObject implements Serializable {
    private String name;
    private String code;
    private boolean isCheck;

    public ColorObject() {
        this.name = "";
        this.code = "";
        this.isCheck = false;
    }

    public ColorObject(String name, String code) {
        this.name = name;
        this.code = code;
        this.isCheck = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
