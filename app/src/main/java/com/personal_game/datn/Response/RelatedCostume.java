package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.Models.Picture;

public class RelatedCostume {
    private Costume costume ;
    private Picture image ;
    private Boolean isFavourite ;

    public RelatedCostume(Costume costume, Picture image, Boolean isFavourite) {
        this.costume = costume;
        this.image = image;
        this.isFavourite = isFavourite;
    }

    public Costume getCostume() {
        return costume;
    }

    public void setCostume(Costume costume) {
        this.costume = costume;
    }

    public Picture getImage() {
        return image;
    }

    public void setImage(Picture image) {
        this.image = image;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}
