package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Bill;
import com.personal_game.datn.Models.BillState;

import java.io.Serializable;
import java.util.List;

public class BillInfo implements Serializable {
    private Bill bill ;
    private BillState billState ;
    private List<CostumeBill> costumes ;

    public BillInfo(Bill bill, BillState billState, List<CostumeBill> costumes) {
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

    public List<CostumeBill> getCostumes() {
        return costumes;
    }

    public void setCostumes(List<CostumeBill> costumes) {
        this.costumes = costumes;
    }
}
