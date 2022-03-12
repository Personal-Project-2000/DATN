package com.personal_game.datn.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeStyleAdapter;
import com.personal_game.datn.Adapter.SuggestAdapter;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ActivityRegistrationBinding;
import com.personal_game.datn.databinding.ActivityStyleBinding;

import java.util.ArrayList;

public class StyleActivity extends AppCompatActivity {
    private ActivityStyleBinding binding;
    private SuggestAdapter suggestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStyleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setStyle();
        setListeners();
    }

    private void setStyle(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        suggestAdapter = new SuggestAdapter(temp, this, new SuggestAdapter.SuggestListeners() {
            @Override
            public void onClick(String suggest) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclStyle.setLayoutManager(gridLayoutManager);
        binding.rclStyle.setAdapter(suggestAdapter);
    }

    private void setListeners(){
        binding.btnCheck.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), TestActivity.class);
            startActivity(intent);
        });
    }
}