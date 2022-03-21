package com.personal_game.datn.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeCartAdapter;
import com.personal_game.datn.databinding.ActivityCartBinding;
import com.personal_game.datn.databinding.ActivitySuggestionBinding;

import java.util.ArrayList;

public class SuggestionActivity extends AppCompatActivity {
    private ActivitySuggestionBinding binding;
    private CostumeAdapter costumeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuggestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setCostume();
        setListeners();
    }

    private void setCostume(){
//        ArrayList<String> temp = new ArrayList<>();
//        for(int i = 0; i < 20; i ++){
//            temp.add("Sy");
//        }
//
//        costumeAdapter = new CostumeAdapter(temp, this, new CostumeAdapter.CostumeListeners() {
//            @Override
//            public void onClick(String costumeStyle) {
//
//            }
//        });
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
//        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
//
//        binding.rclCostume.setLayoutManager(gridLayoutManager);
//        binding.rclCostume.setAdapter(costumeAdapter);
    }

    private void setListeners(){
        binding.layoutStyle.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), StyleActivity.class);
            startActivity(intent);
        });
    }
}