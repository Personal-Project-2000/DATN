package com.personal_game.datn.Request;

import java.util.List;

public class Request_Bill {
    private String addressId ;
    private List<Request_CostumeBill> costumes ;

    public Request_Bill(String addressId, List<Request_CostumeBill> costumes) {
        this.addressId = addressId;
        this.costumes = costumes;
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
