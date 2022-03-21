package com.personal_game.datn.Backup;

import android.content.Context;
import android.content.SharedPreferences;

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

    public String getAccount(){
        return sharedPreferences.getString("Account", "");
    }

    public String getPass(){
        return sharedPreferences.getString("Pass", "");
    }

    public String getToken(){
        return sharedPreferences.getString("token", "");
    }
}
