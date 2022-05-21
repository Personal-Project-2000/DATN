package com.personal_game.datn.Models;

import java.io.Serializable;

public class Address implements Serializable {

    private String id ;
    private String name ;
    private String ward ;
    private String district ;
    private String city ;
    private String street ;
    private String cityId;
    private String districtId;
    private String wardId;
    private String phone ;
    private Boolean addressDefault ;
    private String userId ;

    public Address(){
        this.id = "";
        this.name = "";
        this.ward = "";
        this.district = "";
        this.city = "";
        this.street = "";
        this.phone = "";
    }

    public Address(String id, String name, String ward, String district, String city, String street, String phone, String wardId, String districtId, String cityId) {
        this.id = id;
        this.name = name;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.street = street;
        this.phone = phone;
        this.wardId = wardId;
        this.districtId = districtId;
        this.cityId = cityId;
    }

    public Address(String id, String name, String ward, String district, String city, String street, String cityId, String districtId, String wardId, String phone, Boolean addressDefault) {
        this.id = id;
        this.name = name;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.street = street;
        this.phone = phone;
        this.wardId = wardId;
        this.districtId = districtId;
        this.cityId = cityId;
        this.addressDefault = addressDefault;
    }

    public Address(String name, String ward, String district, String city, String street, String phone, Boolean addressDefault, String userId) {
        this.name = name;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.street = street;
        this.phone = phone;
        this.addressDefault = addressDefault;
        this.userId = userId;
    }

    public Address(String name, String ward, String district, String city, String street, String cityId, String districtId, String wardId, String phone, Boolean addressDefault, String userId) {
        this.name = name;
        this.ward = ward;
        this.district = district;
        this.city = city;
        this.street = street;
        this.cityId = cityId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.phone = phone;
        this.addressDefault = addressDefault;
        this.userId = userId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public Boolean getAddressDefault() {
        return addressDefault;
    }

    public void setAddressDefault(Boolean addressDefault) {
        this.addressDefault = addressDefault;
    }

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
        return addressDefault;
    }

    public void setDefault(Boolean aDefault) {
        addressDefault = aDefault;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
