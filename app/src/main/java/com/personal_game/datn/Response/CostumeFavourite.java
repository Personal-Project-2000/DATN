package com.personal_game.datn.Response;

import java.io.Serializable;
import java.util.List;

public class CostumeFavourite implements Serializable {
    private List<CostumeHome> costumes ;
    private int quantityCart ;

    public CostumeFavourite(List<CostumeHome> costumes, int quantityCart) {
        this.costumes = costumes;
        this.quantityCart = quantityCart;
    }

    public List<CostumeHome> getCostumes() {
        return costumes;
    }

    public void setCostumes(List<CostumeHome> costumes) {
        this.costumes = costumes;
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(int quantityCart) {
        this.quantityCart = quantityCart;
    }
}
