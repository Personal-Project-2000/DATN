package com.personal_game.datn.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeImgAdapter;
import com.personal_game.datn.Adapter.CostumeStyleAdapter;
import com.personal_game.datn.Adapter.StyleAdapter;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ActivityCostumeBinding;
import com.personal_game.datn.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class CostumeActivity extends AppCompatActivity {
    private ActivityCostumeBinding binding;
    private CostumeImgAdapter costumeImgAdapter;
    private CostumeAdapter suitableOutfit;
    private CostumeAdapter costumeAdapter;
    private StyleAdapter styleAdapter;
    private StyleAdapter bodyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCostumeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setImg();
        setSuitableOutfit();
        setRelatedCostumes();
        setStyle();
        setBody();

        setListeners();
    }

    private void setListeners(){
        binding.layoutMain.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    binding.layoutHeader.setVisibility(View.VISIBLE);
                }

                if (scrollY < 5) {
                    binding.layoutHeader.setVisibility(View.GONE);
                }

                if(scrollY >= 850){
                    binding.txtInfoCostume.setTextColor(getResources().getColor(R.color.secondary_text, null));
                    binding.txtIntroCostume.setTextColor(getResources().getColor(R.color.black, null));
                    binding.layoutIntroCostume.setVisibility(View.VISIBLE);
                    binding.layoutInfoCostume.setVisibility(View.GONE);
                }else{
                    binding.txtInfoCostume.setTextColor(getResources().getColor(R.color.black, null));
                    binding.txtIntroCostume.setTextColor(getResources().getColor(R.color.secondary_text, null));
                    binding.layoutIntroCostume.setVisibility(View.GONE);
                    binding.layoutInfoCostume.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.txtInfoCostume.setOnClickListener(v -> {
            binding.layoutMain.setScrollY(0);
        });

        binding.txtIntroCostume.setOnClickListener(v -> {
            binding.layoutMain.setScrollY(850);
        });

        // get current position of recyclerView
        binding.rclImg.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.rclImg.getLayoutManager();
                        binding.txtPosition.setText((linearLayoutManager.findFirstVisibleItemPosition()+1)+"/"+"20");
                        break;
                    default:
                        break;
                }
            }
        });

        binding.btnAddCart.setOnClickListener(v -> {

        });

        binding.layoutDes1.setOnClickListener(v -> {
            binding.layoutDescription.setVisibility(View.VISIBLE);
        });

        binding.btnClose.setOnClickListener(v -> {
            binding.layoutDescription.setVisibility(View.GONE);
        });

        binding.layoutD.setOnClickListener(v -> {
            binding.layoutDescription.setVisibility(View.GONE);
        });

        binding.layoutPolicy.setOnClickListener(v -> {
            binding.layoutPolicy1.setVisibility(View.VISIBLE);
        });

        binding.btnClosePolicy.setOnClickListener(v -> {
            binding.layoutPolicy1.setVisibility(View.GONE);
        });

        binding.layoutSize.setOnClickListener(v -> {
            binding.layoutSize1.setVisibility(View.VISIBLE);
        });

        binding.btnCloseSize.setOnClickListener(v -> {
            binding.layoutSize1.setVisibility(View.GONE);
        });
    }

    private void setImg(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        costumeImgAdapter = new CostumeImgAdapter(temp, this, new CostumeImgAdapter.CostumeImgListeners() {
            @Override
            public void onClick(String costumeStyle) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclImg.setLayoutManager(gridLayoutManager);
        binding.rclImg.setAdapter(costumeImgAdapter);
    }

    private void setSuitableOutfit(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        suitableOutfit = new CostumeAdapter(temp, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClick(String costumeStyle) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclSuitableOutfit.setLayoutManager(gridLayoutManager);
        binding.rclSuitableOutfit.setAdapter(suitableOutfit);
    }

    private void setRelatedCostumes(){
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

        binding.rclRelatedCostumes.setLayoutManager(gridLayoutManager);
        binding.rclRelatedCostumes.setAdapter(costumeAdapter);
    }

    private void setStyle(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        styleAdapter = new StyleAdapter(temp, this, new StyleAdapter.StyleListeners() {
            @Override
            public void onClick(String style) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclStyles.setLayoutManager(gridLayoutManager);
        binding.rclStyles.setAdapter(styleAdapter);
    }

    private void setBody(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        bodyAdapter = new StyleAdapter(temp, this, new StyleAdapter.StyleListeners() {
            @Override
            public void onClick(String style) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclBodys.setLayoutManager(gridLayoutManager);
        binding.rclBodys.setAdapter(bodyAdapter);
    }
}