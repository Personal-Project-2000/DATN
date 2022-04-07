package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.Models.Picture;

import java.io.Serializable;

public class CostumeHome implements Serializable {
    private Costume costume ;
    private Boolean isFavourite ;

    public Costume getCostume() {
        return costume;
    }

    public void setCostume(Costume costume) {
        this.costume = costume;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}
