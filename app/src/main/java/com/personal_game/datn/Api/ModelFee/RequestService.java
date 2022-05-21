package com.personal_game.datn.Api.ModelFee;

public class RequestService {
    private int shop_id;
    private int from_district;
    private int to_district;

    public RequestService(int shop_id, int from_district, int to_district) {
        this.shop_id = shop_id;
        this.from_district = from_district;
        this.to_district = to_district;
    }
}
