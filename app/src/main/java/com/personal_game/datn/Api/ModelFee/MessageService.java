package com.personal_game.datn.Api.ModelFee;

import java.util.List;

public class MessageService {
    private int code;
    private String code_message_value;
    private List<Service> data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCode_message_value() {
        return code_message_value;
    }

    public void setCode_message_value(String code_message_value) {
        this.code_message_value = code_message_value;
    }

    public List<Service> getData() {
        return data;
    }

    public void setData(List<Service> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
