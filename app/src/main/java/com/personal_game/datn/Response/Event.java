package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Promotion;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable {
    private Promotion promotion;
    private List<CostumeHome> costumes;

    public Event(Promotion promotion, List<CostumeHome> costumes) {
        this.promotion = promotion;
        this.costumes = costumes;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public List<CostumeHome> getCostumes() {
        return costumes;
    }

    public void setCostumes(List<CostumeHome> costumes) {
        this.costumes = costumes;
    }
}
