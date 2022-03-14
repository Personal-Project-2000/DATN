package com.personal_game.datn.Api.ModelLocation;

public class Location {
    private String code;
    private String name;

    public Location(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
