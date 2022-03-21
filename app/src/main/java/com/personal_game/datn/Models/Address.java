package com.personal_game.datn.Models;

import java.io.Serializable;

public class Address implements Serializable {
    public Address(String id, String name, String ward, String district, String city, String street, String phone, Boolean aDefault, String account) {
        this.id = id;
        this.name = name;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.street = street;
        this.phone = phone;
        Default = aDefault;
        this.account = account;
    }

    private String id ;
    private String name ;
    private String ward ;
    private String district ;
    private String city ;
    private String street ;
    private String phone ;
    private Boolean Default ;
    private String account ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getDefault() {
        return Default;
    }

    public void setDefault(Boolean aDefault) {
        Default = aDefault;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
