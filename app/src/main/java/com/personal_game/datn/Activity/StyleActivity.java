package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Message_CostumeWithStyle;
import com.personal_game.datn.databinding.ActivityStyleBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StyleActivity extends AppCompatActivity {
    private ActivityStyleBinding binding;
    private CostumeAdapter costumeAdapter;
    private List<CostumeHome> costumes;

    private Shared_Preferences shared_preferences;
    private String styleId = "";

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

        styleId = getIntent().getStringExtra("styleId");
        shared_preferences = new Shared_Preferences((getApplicationContext()));
        binding.imgNumber.setText(shared_preferences.getQuantityCart());

        getCostume();
        setListeners();
    }

    private void getCostume(){
        Service service = getRetrofit().create(Service.class);
        Call<Message_CostumeWithStyle> call = service.CostumeWithStyles("bearer "+shared_preferences.getToken(), styleId);
        call.enqueue(new Callback<Message_CostumeWithStyle>() {
            @Override
            public void onResponse(Call<Message_CostumeWithStyle> call, Response<Message_CostumeWithStyle> response) {
                if(response.body().getStatus() == 1){
                    costumes = response.body().getCostumes();

                    if(costumes != null) {
                        setCostume();
                    }
                }else{
                    Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message_CostumeWithStyle> call, Throwable t) {
                Toast.makeText(getApplication(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCostume(){
        costumeAdapter = new CostumeAdapter(costumes, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClickFavourite(CostumeHome costume, int position) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclCostume.setLayoutManager(gridLayoutManager);
        binding.rclCostume.setAdapter(costumeAdapter);
    }

    private void setListeners(){
        binding.imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), CartActivity.class);
            startActivity(intent);
        });

        binding.btnBackBack.setOnClickListener(v -> {
            finish();
        });

    }
}