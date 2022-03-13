package com.personal_game.datn.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.personal_game.datn.Adapter.AddressAdapter;
import com.personal_game.datn.Adapter.CostumeCartAdapter;
import com.personal_game.datn.databinding.ActivityAddressBinding;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity {
    private ActivityAddressBinding binding;
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setAddress();
        setListeners();
    }

    private void setAddress(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        addressAdapter = new AddressAdapter(temp, this, new AddressAdapter.AddressListeners() {
            @Override
            public void onClick(String address) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclAddress.setLayoutManager(gridLayoutManager);
        binding.rclAddress.setAdapter(addressAdapter);
    }

    private void setListeners(){
        binding.btnAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), DeliveryAddressActivity.class);
            startActivity(intent);
        });
    }
}