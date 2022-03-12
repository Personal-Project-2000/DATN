package com.personal_game.datn;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.databinding.ActivityFavouriteBinding;
import com.personal_game.datn.databinding.ActivityInfoBinding;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    private ActivityInfoBinding binding;
    private CostumeAdapter costumeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setCostumeFavourite();
    }

    private void setCostumeFavourite(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        costumeAdapter = new CostumeAdapter(temp, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClick(String costumeStyle) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclCostumeFavourite.setLayoutManager(gridLayoutManager);
        binding.rclCostumeFavourite.setAdapter(costumeAdapter);
    }
}