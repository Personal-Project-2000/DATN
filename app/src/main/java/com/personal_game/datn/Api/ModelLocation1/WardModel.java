package com.personal_game.datn.Api.ModelLocation1;

public class WardModel {
    private String WardCode;
    private String WardName;

    public WardModel(String wardCode, String wardName) {
        WardCode = wardCode;
        WardName = wardName;
    }

    public String getWardCode() {
        return WardCode;
    }

    public void setWardCode(String wardCode) {
        WardCode = wardCode;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String wardName) {
        WardName = wardName;
    }
}
