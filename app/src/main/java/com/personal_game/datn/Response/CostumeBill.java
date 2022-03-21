package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.Models.Picture;

import java.io.Serializable;

public class CostumeBill implements Serializable {
    private Costume costume ;
    private Picture image ;

    public CostumeBill(Costume costume, Picture image) {
        this.costume = costume;
        this.image = image;
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
}
