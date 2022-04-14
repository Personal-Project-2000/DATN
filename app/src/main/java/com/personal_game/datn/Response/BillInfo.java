package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Bill;
import com.personal_game.datn.Models.BillState;
import com.personal_game.datn.Models.Costume;

import java.io.Serializable;
import java.util.List;

public class BillInfo implements Serializable {
    private Bill bill ;
    private BillState billState ;

    public BillInfo(Bill bill, BillState billState) {
        this.bill = bill;
        this.billState = billState;
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
}
