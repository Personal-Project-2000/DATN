package com.personal_game.datn.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeStyleAdapter;
import com.personal_game.datn.Adapter.NotificationAdapter;
import com.personal_game.datn.databinding.ActivityInfoBinding;
import com.personal_game.datn.databinding.ActivityNotiBinding;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    private ActivityNotiBinding binding;
    private NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setNotification();
        setListeners();
    }

    private void setNotification(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        notificationAdapter = new NotificationAdapter(temp, this, new CostumeStyleAdapter.CostumeStyleListeners() {
            @Override
            public void onClick(String costumeStyle) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclNotification.setLayoutManager(gridLayoutManager);
        binding.rclNotification.setAdapter(notificationAdapter);
    }

    private void setListeners(){
        binding.imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), CartActivity.class);
            startActivity(intent);
        });

        binding.imgFavourite.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), FavouriteActivity.class);
            startActivity(intent);
        });
    }
}