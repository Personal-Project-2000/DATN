package com.personal_game.datn.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.personal_game.datn.Adapter.PersonalStyleAdapter;
import com.personal_game.datn.Models.PersonalStyle;
import com.personal_game.datn.databinding.ActivityStyleBinding;

import java.util.List;

public class StyleActivity extends AppCompatActivity {
    private ActivityStyleBinding binding;
    private PersonalStyleAdapter personalStyleAdapter;

    private List<PersonalStyle> personalStyles;

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

        personalStyles = (List<PersonalStyle>)getIntent().getSerializableExtra("personalStyles");

        if(personalStyles != null) {
            setStyle();
        }
        setListeners();
    }

    private void setStyle(){
        personalStyleAdapter = new PersonalStyleAdapter(personalStyles, this, new PersonalStyleAdapter.SuggestListeners() {
            @Override
            public void onClick(PersonalStyle personalStyle, int postion) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclStyle.setLayoutManager(gridLayoutManager);
        binding.rclStyle.setAdapter(personalStyleAdapter);
    }

    private void setListeners(){
        binding.btnCheck.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), TestActivity.class);
            startActivity(intent);
        });
    }
}