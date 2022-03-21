package com.personal_game.datn.Response;

import java.io.Serializable;
import java.util.List;

public class CostumeFavourite implements Serializable {
    private List<CostumeFavouriteInfo> costumes ;
    private int quantityCart ;

    public CostumeFavourite(List<CostumeFavouriteInfo> costumes, int quantityCart) {
        this.costumes = costumes;
        this.quantityCart = quantityCart;
    }

    public List<CostumeFavouriteInfo> getCostumes() {
        return costumes;
    }

    public void setCostumes(List<CostumeFavouriteInfo> costumes) {
        this.costumes = costumes;
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(int quantityCart) {
        this.quantityCart = quantityCart;
    }
}
