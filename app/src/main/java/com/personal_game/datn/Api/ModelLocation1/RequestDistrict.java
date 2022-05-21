package com.personal_game.datn.Api.ModelLocation1;

public class RequestDistrict {
    private int province_id;

    public RequestDistrict(int province_id) {
        this.province_id = province_id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }
}
