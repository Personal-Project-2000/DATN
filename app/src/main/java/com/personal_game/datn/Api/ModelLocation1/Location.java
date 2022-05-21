package com.personal_game.datn.Api.ModelLocation1;

public class Location {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
