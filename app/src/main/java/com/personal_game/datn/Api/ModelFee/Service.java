package com.personal_game.datn.Api.ModelFee;

public class Service {
    private int service_id;
    private String short_name;

    public Service(int service_id, String short_name) {
        this.service_id = service_id;
        this.short_name = short_name;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }
}
