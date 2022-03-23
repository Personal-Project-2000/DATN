package com.personal_game.datn.Backup;

import android.content.Context;
import android.content.SharedPreferences;

import com.personal_game.datn.Models.Address;

public class Shared_Preferences {
    private Context context;
    private SharedPreferences sharedPreferences;

    public Shared_Preferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    public void saveAccount(String phone, String pass){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Account", phone);
        editor.putString("Pass", pass);
        editor.apply();
        editor.commit();
    }

    public void saveToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
        editor.commit();
    }

    public void saveName(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
        editor.commit();
    }

    public void saveImg(String img){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("img", img);
        editor.apply();
        editor.commit();
    }

    public void saveAddress(Address address){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("NameAddress", address.getName());
        editor.putString("PhoneAddress", address.getPhone());
        editor.putString("Street", address.getStreet());
        editor.putString("Ward", address.getWard());
        editor.putString("District", address.getDistrict());
        editor.putString("City", address.getCity());
        editor.putString("IdAddress", address.getId());
        editor.apply();
        editor.commit();
    }

    public String getAccount(){
        return sharedPreferences.getString("Account", "");
    }

    public String getPass(){
        return sharedPreferences.getString("Pass", "");
    }

    public String getToken(){
        return sharedPreferences.getString("token", "");
    }

    public String getName(){
        return sharedPreferences.getString("name", "");
    }

    public String getImg(){
        return sharedPreferences.getString("img", "");
    }

    public Address getAddress(){
        //String id, String name, String ward, String district, String city, String street, String phone
        return new Address(sharedPreferences.getString("IdAddress", ""),
                sharedPreferences.getString("NameAddress", ""),
                sharedPreferences.getString("Ward", ""),
                sharedPreferences.getString("District", ""),
                sharedPreferences.getString("City", ""),
                sharedPreferences.getString("Street", ""),
                sharedPreferences.getString("PhoneAddress", ""));
    }
}
