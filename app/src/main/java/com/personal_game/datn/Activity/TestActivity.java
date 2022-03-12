package com.personal_game.datn.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.personal_game.datn.Adapter.AddressAdapter;
import com.personal_game.datn.Adapter.QuestionAdapter;
import com.personal_game.datn.databinding.ActivitySuggestionBinding;
import com.personal_game.datn.databinding.ActivityTestBinding;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    private ActivityTestBinding binding;
    private QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setQuestion();
    }

    private void setQuestion(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        questionAdapter = new QuestionAdapter(temp, this, new QuestionAdapter.QuestionListeners() {
            @Override
            public void onClick(String suggest) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclQuestion.setLayoutManager(gridLayoutManager);
        binding.rclQuestion.setAdapter(questionAdapter);
    }
}