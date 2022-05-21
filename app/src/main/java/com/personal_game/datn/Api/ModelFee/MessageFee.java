package com.personal_game.datn.Api.ModelFee;

public class MessageFee {
    private int code;
    private String message;
    private FeeModel data;

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

    public FeeModel getData() {
        return data;
    }

    public void setData(FeeModel data) {
        this.data = data;
    }
}
