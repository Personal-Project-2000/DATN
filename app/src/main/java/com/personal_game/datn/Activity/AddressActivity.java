package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Adapter.AddressAdapter;
import com.personal_game.datn.Adapter.CostumeCartAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Address;
import com.personal_game.datn.Response.Message_Address;
import com.personal_game.datn.Response.Message_Info;
import com.personal_game.datn.databinding.ActivityAddressBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {
    private ActivityAddressBinding binding;
    private AddressAdapter addressAdapter;

    private Shared_Preferences shared_preferences;
    private List<Address> addressList;

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

        shared_preferences = new Shared_Preferences(getApplicationContext());

        getAddress();
        setListeners();
    }

    private void getAddress(){
        Service service = getRetrofit().create(Service.class);
        Call<Message_Address> address = service.GetAddress("bearer "+shared_preferences.getToken());
        address.enqueue(new Callback<Message_Address>() {
            @Override
            public void onResponse(Call<Message_Address> call, Response<Message_Address> response) {
                if(response.body().getStatus() == 1){
                    addressList = response.body().getAddresses();

                    setAddress();
                }

                Toast.makeText(getApplicationContext(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message_Address> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAddress(){
        addressAdapter = new AddressAdapter(addressList, this, new AddressAdapter.AddressListeners() {
            @Override
            public void onClickDefault(int position, boolean addressDefault) {
                setAddressDefault(position, addressDefault);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclAddress.setLayoutManager(gridLayoutManager);
        binding.rclAddress.setAdapter(addressAdapter);
    }

    private void setAddressDefault(int position, boolean isDefault){
        if(isDefault){
            for (int i = 0; i < addressList.size(); i ++) {
                if(addressList.get(i).getDefault()){
                    addressList.get(i).setDefault(false);
                    addressAdapter.notifyItemChanged(i);
                    break;
                }
            }
        }else{
            addressList.get(0).setDefault(true);
            addressAdapter.notifyItemChanged(0);
        }

        addressList.get(position).setDefault(isDefault);
        addressAdapter.notifyItemChanged(position);
    }

    private void setListeners(){
        binding.btnAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), DeliveryAddressActivity.class);
            startActivity(intent);
        });
    }
}