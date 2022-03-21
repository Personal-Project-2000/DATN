package com.personal_game.datn.Request;

public class Request_UpdateBill {
    private String billId ;
    private String stateId ;

    public Request_UpdateBill(String billId, String stateId) {
        this.billId = billId;
        this.stateId = stateId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }
}
