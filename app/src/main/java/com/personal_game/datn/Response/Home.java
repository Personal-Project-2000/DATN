package com.personal_game.datn.Response;

import com.personal_game.datn.Models.CostumeStyle;

import java.io.Serializable;
import java.util.List;

public class Home implements Serializable {
    private int quantityCart ;
    private int quantityFavourite ;
    private List<Event> events;
    private List<CostumeStyle> costumeStyles ;
    private List<CostumeHome> costumeHots ;
    private List<CostumeHome> costumeNews ;

    public int getQuantityFavourite() {
        return quantityFavourite;
    }

    public void setQuantityFavourite(int quantityFavourite) {
        this.quantityFavourite = quantityFavourite;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(int quantityCart) {
        this.quantityCart = quantityCart;
    }

    public List<CostumeStyle> getCostumeStyles() {
        return costumeStyles;
    }

    public void setCostumeStyles(List<CostumeStyle> costumeStyles) {
        this.costumeStyles = costumeStyles;
    }

    public List<CostumeHome> getCostumeHots() {
        return costumeHots;
    }

    public void setCostumeHots(List<CostumeHome> costumeHots) {
        this.costumeHots = costumeHots;
    }

    public List<CostumeHome> getCostumeNews() {
        return costumeNews;
    }

    public void setCostumeNews(List<CostumeHome> costumeNews) {
        this.costumeNews = costumeNews;
    }
}
