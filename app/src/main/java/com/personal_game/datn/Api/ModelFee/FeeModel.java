package com.personal_game.datn.Api.ModelFee;

public class FeeModel {
    private int total;
    private int service_fee;
    private int insurance_fee;
    private int pick_station_fee;
    private int coupon_value;
    private int r2s_fee;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getService_fee() {
        return service_fee;
    }

    public void setService_fee(int service_fee) {
        this.service_fee = service_fee;
    }

    public int getInsurance_fee() {
        return insurance_fee;
    }

    public void setInsurance_fee(int insurance_fee) {
        this.insurance_fee = insurance_fee;
    }

    public int getPick_station_fee() {
        return pick_station_fee;
    }

    public void setPick_station_fee(int pick_station_fee) {
        this.pick_station_fee = pick_station_fee;
    }

    public int getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(int coupon_value) {
        this.coupon_value = coupon_value;
    }

    public int getR2s_fee() {
        return r2s_fee;
    }

    public void setR2s_fee(int r2s_fee) {
        this.r2s_fee = r2s_fee;
    }
}
