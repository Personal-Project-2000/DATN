package com.personal_game.datn.Api.ModelLocation1;

import java.util.List;

public class MessageDistrict {
    private int code;
    private String message;
    private List<DistrictModel> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DistrictModel> getData() {
        return data;
    }

    public void setData(List<DistrictModel> data) {
        this.data = data;
    }
}
