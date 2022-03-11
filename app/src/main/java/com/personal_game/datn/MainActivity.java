package com.personal_game.datn;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeStyleAdapter;
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
        activityMainBinding.btnMenu.setOnClickListener(v -> {
            DrawerLayout d =  findViewById(R.id.drawerLayout);
            d.openDrawer(GravityCompat.START);
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