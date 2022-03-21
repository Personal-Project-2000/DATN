package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Address;
import com.personal_game.datn.Models.User;

import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {
    private User user ;
    private Address addressDefault ;
    private List<CostumeFavouriteInfo> costumeFavourites ;
    private int quantityCart ;

    public UserInfo(User user, Address addressDefault, List<CostumeFavouriteInfo> costumeFavourites, int quantityCart) {
        this.user = user;
        this.addressDefault = addressDefault;
        this.costumeFavourites = costumeFavourites;
        this.quantityCart = quantityCart;
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

    public List<CostumeFavouriteInfo> getCostumeFavourites() {
        return costumeFavourites;
    }

    public void setCostumeFavourites(List<CostumeFavouriteInfo> costumeFavourites) {
        this.costumeFavourites = costumeFavourites;
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(int quantityCart) {
        this.quantityCart = quantityCart;
    }
}
