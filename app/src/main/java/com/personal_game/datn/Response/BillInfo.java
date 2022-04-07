package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Bill;
import com.personal_game.datn.Models.BillState;
import com.personal_game.datn.Models.Costume;

import java.io.Serializable;
import java.util.List;

public class BillInfo implements Serializable {
    private Bill bill ;
    private BillState billState ;
    private List<Costume> costumes ;

    public BillInfo(Bill bill, BillState billState, List<Costume> costumes) {
        this.bill = bill;
        this.billState = billState;
        this.costumes = costumes;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public BillState getBillState() {
        return billState;
    }

    public void setBillState(BillState billState) {
        this.billState = billState;
    }

    public List<Costume> getCostumes() {
        return costumes;
    }

    public void setCostumes(List<Costume> costumes) {
        this.costumes = costumes;
    }
}
