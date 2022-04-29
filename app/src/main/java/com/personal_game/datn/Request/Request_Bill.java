package com.personal_game.datn.Request;

import com.personal_game.datn.Models.Promotion;

import java.util.List;

public class Request_Bill {
    private String addressId ;
    private List<Request_CostumeBill> costumes ;
    private Promotion promotion;
    private boolean isPayment;

    public Request_Bill(String addressId, List<Request_CostumeBill> costumes, boolean isPayment) {
        this.addressId = addressId;
        this.costumes = costumes;
        this.isPayment = isPayment;
    }

    public Request_Bill(String addressId, List<Request_CostumeBill> costumes, Promotion promotion, boolean isPayment) {
        this.addressId = addressId;
        this.costumes = costumes;
        this.promotion = promotion;
        this.isPayment = isPayment;
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
