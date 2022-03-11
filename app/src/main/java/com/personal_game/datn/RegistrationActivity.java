package com.personal_game.datn;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.personal_game.datn.databinding.ActivityRegistrationBinding;
import com.personal_game.datn.databinding.ActivitySignInBinding;

public class RegistrationActivity extends AppCompatActivity {
    private ActivityRegistrationBinding activityRegistrationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegistrationBinding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        View view = activityRegistrationBinding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}