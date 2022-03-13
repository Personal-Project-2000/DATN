package com.personal_game.datn.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.R;
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
        setListeners();
    }

    private void changeLayoutHeader(boolean isChange){
        if(isChange){
            binding.txtTitle.setVisibility(View.GONE);
            binding.layout6.setVisibility(View.GONE);
            binding.layoutHeader1.setVisibility(View.VISIBLE);
        }else{
            binding.txtTitle.setVisibility(View.VISIBLE);
            binding.layout6.setVisibility(View.VISIBLE);
            binding.layoutHeader1.setVisibility(View.GONE);
        }
    }

    //khi scroll sẽ thay đổi giao diện
    private void changeViewScroll(boolean isChange){
        if(isChange){
            binding.txtInfoPersonal.setTextColor(getResources().getColor(R.color.secondary_text, null));
            binding.txtFavourite.setTextColor(getResources().getColor(R.color.black, null));
            binding.layoutIntroCostume.setVisibility(View.VISIBLE);
            binding.layoutInfoCostume.setVisibility(View.GONE);
        }else{
            binding.txtInfoPersonal.setTextColor(getResources().getColor(R.color.black, null));
            binding.txtFavourite.setTextColor(getResources().getColor(R.color.secondary_text, null));
            binding.layoutIntroCostume.setVisibility(View.GONE);
            binding.layoutInfoCostume.setVisibility(View.VISIBLE);
        }
    }

    //khi bấm vào xem thông tin cá nhân
    private void changeViewInfo(boolean isChange){
        if(isChange){
            binding.layoutMain.setVisibility(View.GONE);
            binding.layoutInfoUser.setVisibility(View.VISIBLE);
        }else{
            binding.layoutMain.setVisibility(View.VISIBLE);
            binding.layoutInfoUser.setVisibility(View.GONE);
        }
    }

    private void setListeners(){
        binding.layoutMain.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    changeLayoutHeader(true);
                }

                if (scrollY < 5) {
                    changeLayoutHeader(false);
                }

                if(scrollY >= 500){
                    changeViewScroll(true);
                }else{
                    changeViewScroll(false);
                }
            }
        });

        binding.txtInfoPersonal.setOnClickListener(v -> {
            binding.layoutMain.setScrollY(0);
        });

        binding.txtFavourite.setOnClickListener(v -> {
            binding.layoutMain.setScrollY(500);
        });

        binding.imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), CartActivity.class);
            startActivity(intent);
        });

        binding.layoutInfo.setOnClickListener(v -> {
            changeViewInfo(true);
        });

        binding.layoutAddress.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), AddressActivity.class);
            startActivity(intent);
        });

        binding.btnClose.setOnClickListener(v -> {
            changeViewInfo(false);
        });

        binding.btnBillCancel.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), BillActivity.class);
            intent.putExtra("code", 6);
            startActivity(intent);
        });
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