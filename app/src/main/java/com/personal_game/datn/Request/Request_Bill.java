package com.personal_game.datn.Request;

import com.personal_game.datn.Models.Promotion;

import java.util.List;

public class Request_Bill {
    private String addressId ;
    private List<Request_CostumeBill> costumes ;
    private Promotion promotion;
    private int fee;
    private boolean isPayment;

    public Request_Bill(String addressId, List<Request_CostumeBill> costumes, int fee, boolean isPayment) {
        this.addressId = addressId;
        this.costumes = costumes;
        this.isPayment = isPayment;
        this.fee = fee;
    }

    public Request_Bill(String addressId, List<Request_CostumeBill> costumes, Promotion promotion, int fee, boolean isPayment) {
        this.addressId = addressId;
        this.costumes = costumes;
        this.promotion = promotion;
        this.isPayment = isPayment;
        this.fee = fee;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public boolean isPayment() {
        return isPayment;
    }

    public void setPayment(boolean payment) {
        isPayment = payment;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public List<Request_CostumeBill> getCostumes() {
        return costumes;
    }

    public void setCostumes(List<Request_CostumeBill> costumes) {
        this.costumes = costumes;
    }
}
