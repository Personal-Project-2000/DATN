package com.personal_game.datn.Models;

import java.io.Serializable;

public class User implements Serializable {
    private String Id ;
    private String FullName ;
    private String Pass ;
    private String Img ;
    private String Path ;
    private String Phone ;
    private Boolean Sex ;
    private String Date ;
    private int Day ;
    private int Month ;
    private int Year ;

    public User(String id, String fullName, String pass, String img, String path, String phone, Boolean sex, String date, int day, int month, int year) {
        Id = id;
        FullName = fullName;
        Pass = pass;
        Img = img;
        Path = path;
        Phone = phone;
        Sex = sex;
        Date = date;
        Day = day;
        Month = month;
        Year = year;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Boolean getSex() {
        return Sex;
    }

    public void setSex(Boolean sex) {
        Sex = sex;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }
}
