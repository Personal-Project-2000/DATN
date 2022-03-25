package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Body;
import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.Models.PersonalStyle;
import com.personal_game.datn.Models.Picture;

import java.io.Serializable;
import java.util.List;

public class CostumeInfo implements Serializable {
    private Costume costume ;
    private List<Body> bodies ;
    private List<PersonalStyle> personalStyles ;
    private Boolean isFavourite ;
    private List<CostumeHome> relatedCostumes ;
    private List<CostumeHome> suitableOutfitCostumes ;
    private int quantityCart ;
    private int quantityFavourite ;
    private List<Picture> images ;

    public List<CostumeHome> getSuitableOutfitCostumes() {
        return suitableOutfitCostumes;
    }

    public void setSuitableOutfitCostumes(List<CostumeHome> suitableOutfitCostumes) {
        this.suitableOutfitCostumes = suitableOutfitCostumes;
    }

    public Costume getCostume() {
        return costume;
    }

    public void setCostume(Costume costume) {
        this.costume = costume;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
    }

    public List<PersonalStyle> getPersonalStyles() {
        return personalStyles;
    }

    public void setPersonalStyles(List<PersonalStyle> personalStyles) {
        this.personalStyles = personalStyles;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public List<CostumeHome> getRelatedCostumes() {
        return relatedCostumes;
    }

    public void setRelatedCostumes(List<CostumeHome> relatedCostumes) {
        this.relatedCostumes = relatedCostumes;
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(int quantityCart) {
        this.quantityCart = quantityCart;
    }

    public int getQuantityFavourite() {
        return quantityFavourite;
    }

    public void setQuantityFavourite(int quantityFavourite) {
        this.quantityFavourite = quantityFavourite;
    }

    public List<Picture> getImages() {
        return images;
    }

    public void setImages(List<Picture> images) {
        this.images = images;
    }
}
