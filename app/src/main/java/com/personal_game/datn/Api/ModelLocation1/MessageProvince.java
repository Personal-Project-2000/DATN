package com.personal_game.datn.Api.ModelLocation1;

import java.util.List;

public class MessageProvince {
    private int code;
    private String message;
    private List<ProvinceModel> data;

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

    public List<ProvinceModel> getData() {
        return data;
    }

    public void setData(List<ProvinceModel> data) {
        this.data = data;
    }
}
