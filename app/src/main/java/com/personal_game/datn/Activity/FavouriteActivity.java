package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Message_Favourite;
import com.personal_game.datn.databinding.ActivityFavouriteBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteActivity extends AppCompatActivity {
    private ActivityFavouriteBinding binding;
    private CostumeAdapter costumeAdapter;
    private List<CostumeHome> costumeFavourites;

    private Shared_Preferences shared_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        shared_preferences = new Shared_Preferences((getApplicationContext()));

        getCostumeFavourite();
        setListeners();
    }

    private void getCostumeFavourite(){
        Service service = getRetrofit().create(Service.class);
        Call<Message_Favourite> favouriteCall = service.GetFavourites("bearer "+shared_preferences.getToken());
        favouriteCall.enqueue(new Callback<Message_Favourite>() {
            @Override
            public void onResponse(Call<Message_Favourite> call, Response<Message_Favourite> response) {
                if(response.body().getStatus() == 1){
                    costumeFavourites = response.body().getCostumes().getCostumes();

                    binding.imgNumber.setText(response.body().getCostumes().getQuantityCart()+"");

                    setCostume();
                }

                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message_Favourite> call, Throwable t) {
                Toast.makeText(getApplication(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCostume(){
        costumeAdapter = new CostumeAdapter(costumeFavourites, this);

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
    }
}