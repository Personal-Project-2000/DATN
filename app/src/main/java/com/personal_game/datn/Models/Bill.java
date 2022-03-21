package com.personal_game.datn.Models;

import java.io.Serializable;

public class Bill implements Serializable {
    private String id ;
    private String account ;
    private String name ;
    private String street ;
    private String address ;
    private String phone ;
    private String stateId ;
    private String date ;
    private int day ;
    private int month ;
    private int year ;
    private int total ;

    public Bill(String id, String account, String name, String street, String address, String phone, String stateId, String date, int day, int month, int year, int total) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.street = street;
        this.address = address;
        this.phone = phone;
        this.stateId = stateId;
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
