package com.personal_game.datn.Response;

import com.personal_game.datn.Models.Address;

import java.io.Serializable;

public class Data implements Serializable {
    private String token ;
    private String name ;
    private String image ;
    private Address addressDefault ;

    public Data(String token, String name, String image, Address addressDefault) {
        this.token = token;
        this.name = name;
        this.image = image;
        this.addressDefault = addressDefault;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Address getAddressDefault() {
        return addressDefault;
    }

    public void setAddressDefault(Address addressDefault) {
        this.addressDefault = addressDefault;
    }
}
