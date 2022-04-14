package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Address;
import com.personal_game.datn.Models.CostumeStyle;
import com.personal_game.datn.Models.User;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {
    private User user ;
    private Address addressDefault ;
    private List<CostumeHome> costumeFavourites ;
    private int quantityCart;
    private List<CostumeStyle> costumeStyles;

    public UserInfo(User user, Address addressDefault, List<CostumeHome> costumeFavourites, int quantityCart) {
        this.user = user;
        this.addressDefault = addressDefault;
        this.costumeFavourites = costumeFavourites;
        this.quantityCart = quantityCart;
    }

    public List<CostumeStyle> getCostumeStyles() {
        return costumeStyles;
    }

    public void setCostumeStyles(List<CostumeStyle> costumeStyles) {
        this.costumeStyles = costumeStyles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddressDefault() {
        return addressDefault;
    }

    public void setAddressDefault(Address addressDefault) {
        this.addressDefault = addressDefault;
    }

    public List<CostumeHome> getCostumeFavourites() {
        return costumeFavourites;
    }

    public void setCostumeFavourites(List<CostumeHome> costumeFavourites) {
        this.costumeFavourites = costumeFavourites;
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(int quantityCart) {
        this.quantityCart = quantityCart;
    }
}
