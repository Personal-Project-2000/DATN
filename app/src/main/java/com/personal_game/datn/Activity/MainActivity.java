package com.personal_game.datn.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeStyleAdapter;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ActivityMainBinding;
import com.personal_game.datn.databinding.ActivitySignInBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private CostumeAdapter costumeAdapter;
    private CostumeStyleAdapter costumeStyleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setListeners();
        setCostumeStyle();
        setCostume();
        setPromotion();
    }

    private void setListeners(){
        activityMainBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_info: {
                        Intent intent = new Intent(getApplication(), InfoActivity.class);
                        startActivityForResult(intent, 1);
                        break;
                    }
                    case R.id.nav_favourite: {
                        Intent intent = new Intent(getApplication(), FavouriteActivity.class);
                        startActivityForResult(intent, 2);
                        break;
                    }
                    case R.id.nav_notification: {
                        Intent intent = new Intent(getApplication(), NotificationActivity.class);
                        startActivityForResult(intent, 3);
                        break;
                    }
                    case R.id.nav_suggest: {
                        Intent intent = new Intent(getApplication(), SuggestionActivity.class);
                        startActivityForResult(intent, 3);
                        break;
                    }
                    case R.id.nav_signout: {
                        Intent intent = new Intent(getApplication(), SignInActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

        activityMainBinding.btnMenu.setOnClickListener(v -> {
            DrawerLayout d =  findViewById(R.id.drawerLayout);
            d.openDrawer(GravityCompat.START);
        });

        activityMainBinding.imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), CartActivity.class);
            startActivity(intent);
        });
    }

    private void setCostumeStyle(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        costumeStyleAdapter = new CostumeStyleAdapter(temp, this, new CostumeStyleAdapter.CostumeStyleListeners() {
            @Override
            public void onClick(String costumeStyle) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        activityMainBinding.rclCostumeStyle.setLayoutManager(gridLayoutManager);
        activityMainBinding.rclCostumeStyle.setAdapter(costumeStyleAdapter);
    }

    private void setPromotion(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        costumeAdapter = new CostumeAdapter(temp, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClick(String costume) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        activityMainBinding.rclPromotion.setLayoutManager(gridLayoutManager);
        activityMainBinding.rclPromotion.setAdapter(costumeAdapter);
    }

    private void setCostume(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        costumeAdapter = new CostumeAdapter(temp, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClick(String costume) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        activityMainBinding.rclCostume.setLayoutManager(gridLayoutManager);
        activityMainBinding.rclCostume.setAdapter(costumeAdapter);
    }
}