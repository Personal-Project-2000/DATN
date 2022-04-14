package com.personal_game.datn.Models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String id ;
    private String fullName ;
    private String pass ;
    private String img ;
    private String path ;
    private String phone ;
    private Boolean sex ;
    private String date ;
    private int day ;
    private int month ;
    private int year ;
    private List<SizeUser> sizes;

    public User(String fullName, Boolean sex) {
        this.fullName = fullName;
        this.sex = sex;
    }

    public User(String fullName, String pass, String phone) {
        this.fullName = fullName;
        this.pass = pass;
        this.phone = phone;
    }

    public User(String fullName, Boolean sex, List<SizeUser> sizes) {
        this.fullName = fullName;
        this.sex = sex;
        this.sizes = sizes;
    }

    public User(String id, String fullName, String pass, String img, String path, String phone, Boolean sex, String date, int day, int month, int year) {
        this.id = id;
        this.fullName = fullName;
        this.pass = pass;
        this.img = img;
        this.path = path;
        this.phone = phone;
        this.sex = sex;
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public List<SizeUser> getSizes() {
        return sizes;
    }

    public void setSizes(List<SizeUser> sizes) {
        this.sizes = sizes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
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
}
