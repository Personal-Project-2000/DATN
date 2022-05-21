package com.personal_game.datn.Api.ModelFee;

public class RequestFee {
    private int service_id;
    private int insurance_value;
    private String coupon;
    private int from_district_id;
    private int to_district_id;
    private int to_ward_code;
    private int height;
    private int length;
    private int weight;
    private int width;

    public RequestFee(int service_id, int insurance_value, String coupon, int from_district_id, int to_district_id, int to_ward_code) {
        this.service_id = service_id;
        this.insurance_value = insurance_value;
        this.coupon = coupon;
        this.from_district_id = from_district_id;
        this.to_district_id = to_district_id;
        this.to_ward_code = to_ward_code;
        this.height = 1;
        this.length = 1;
        this.weight = 1000;
        this.width = 1;
    }
}
