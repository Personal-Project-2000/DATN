package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitLocation.getRetrofitLocation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.personal_game.datn.Adapter.CostumeImgAdapter;
import com.personal_game.datn.Adapter.LocationAdapter;
import com.personal_game.datn.Api.ModelLocation.DistrictModel;
import com.personal_game.datn.Api.ModelLocation.Location;
import com.personal_game.datn.Api.ModelLocation.ProvinceModel;
import com.personal_game.datn.Api.ModelLocation.WardModel;
import com.personal_game.datn.Api.ServiceApi.ServiceLocation;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ActivityDeliveryAddressBinding;
import com.personal_game.datn.databinding.ActivityInfoBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryAddressActivity extends AppCompatActivity {
    private ActivityDeliveryAddressBinding binding;
    private ArrayList<ProvinceModel> provinceModels;
    private ArrayList<DistrictModel> districtModels;
    private ArrayList<WardModel> wardModels;
    private int positionProvince = -1;
    private int positionDistrict = -1;
    private int positionWard = -1;
    private LocationAdapter locationProvince;
    private LocationAdapter locationDistrict;
    private LocationAdapter locationWard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryAddressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setCity();
        setListeners();
    }

    private void setCity(){
        provinceModels = new ArrayList<>();
        ServiceLocation serviceAPI = getRetrofitLocation().create(ServiceLocation.class);
        Call<ArrayList<ProvinceModel>> call = serviceAPI.GetProvinces();
        call.enqueue(new Callback<ArrayList<ProvinceModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProvinceModel>> call, Response<ArrayList<ProvinceModel>> response) {
                provinceModels = response.body();

                ArrayList<Location> locations = new ArrayList<>();
                for (ProvinceModel province: provinceModels) {
                    locations.add(new Location(province.getCode(), province.getName()));
                }

                setRcl(locations, 1);
            }

            @Override
            public void onFailure(Call<ArrayList<ProvinceModel>> call, Throwable t) {

            }
        });
    }

    private void setDistrict(String code){
        districtModels = new ArrayList<>();
        ServiceLocation serviceAPI = getRetrofitLocation().create(ServiceLocation.class);
        Call<ProvinceModel> call = serviceAPI.GetDistricts(code);
        call.enqueue(new Callback<ProvinceModel>() {
            @Override
            public void onResponse(Call<ProvinceModel> call, Response<ProvinceModel> response) {
                districtModels.add(new DistrictModel("-1", "huyen", "-1", "Quận / Huyện", "-1"));
                if (response.body() != null && response.body().getDistricts().size() > 0) {
                    districtModels = response.body().getDistricts();
                }

                ArrayList<Location> locations = new ArrayList<>();
                for (DistrictModel districtModel: districtModels) {
                    locations.add(new Location(districtModel.getCode(), districtModel.getName()));
                }

                binding.rclCity.setVisibility(View.GONE);
                binding.SelectCity.setTextColor(getResources().getColor(R.color.black));
                binding.rclDistrict.setVisibility(View.VISIBLE);

                setRcl(locations, 2);
            }

            @Override
            public void onFailure(Call<ProvinceModel> call, Throwable t) {
                Log.d("log:", t.getMessage());
            }
        });
    }

    private void setWard(String code){
        wardModels = new ArrayList<>();
        ServiceLocation serviceAPI = getRetrofitLocation().create(ServiceLocation.class);
        Call<DistrictModel> call = serviceAPI.GetWards(code);
        call.enqueue(new Callback<DistrictModel>() {
            @Override
            public void onResponse(Call<DistrictModel> call, Response<DistrictModel> response) {
                wardModels.add(new WardModel("-1", "phuong", "-1", "-1", "Phường / Xã"));
                if (response.body() != null && response.body().getWards().size() > 0) {
                    wardModels = response.body().getWards();
                }

                ArrayList<Location> locations = new ArrayList<>();
                for (WardModel wardModel: wardModels) {
                    locations.add(new Location(wardModel.getCode(), wardModel.getName()));
                }

                binding.rclDistrict.setVisibility(View.GONE);
                binding.SelectDistrict.setTextColor(getResources().getColor(R.color.black));
                binding.rclWard.setVisibility(View.VISIBLE);

                setRcl(locations, 3);
            }

            @Override
            public void onFailure(Call<DistrictModel> call, Throwable t) {
                Log.d("log:", t.getMessage());
            }
        });
    }

    //code => 1: city, 2: district, 3: ward
    private void setRcl(ArrayList<Location> locations, int code){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        switch (code){
            case 1:
                locationProvince = new LocationAdapter(locations, getApplication(), new LocationAdapter.LocationListeners() {
                    @Override
                    public void onClick(Location location, int position) {
                        binding.SelectCity.setText(location.getName());
                        setDistrict(location.getCode());
                        binding.SelectDistrict.setVisibility(View.VISIBLE);

                        if(positionProvince != -1) {
                            locationProvince.notifyItemChanged(positionProvince);
                            positionDistrict = -1;
                            positionWard = -1;
                            binding.SelectDistrict.setText("Chọn huyện");
                            binding.SelectDistrict.setTextColor(getResources().getColor(R.color.color4));
                            binding.SelectWard.setVisibility(View.GONE);
                        }
                        positionProvince = position;
                    }
                });

                binding.rclCity.setLayoutManager(gridLayoutManager);
                binding.rclCity.setAdapter(locationProvince);
                break;
            case 2:
                locationDistrict = new LocationAdapter(locations, getApplication(), new LocationAdapter.LocationListeners() {
                    @Override
                    public void onClick(Location location, int position) {
                        binding.SelectDistrict.setText(location.getName());
                        setWard(location.getCode());
                        binding.SelectWard.setVisibility(View.VISIBLE);

                        if(positionDistrict != -1) {
                            locationDistrict.notifyItemChanged(positionDistrict);
                            positionWard = -1;
                            binding.SelectWard.setText("Chọn khu vực");
                            binding.SelectWard.setTextColor(getResources().getColor(R.color.color4));
                        }
                        positionDistrict = position;
                    }
                });

                binding.rclDistrict.setLayoutManager(gridLayoutManager);
                binding.rclDistrict.setAdapter(locationDistrict);
                break;
            case 3:
                locationWard = new LocationAdapter(locations, getApplication(), new LocationAdapter.LocationListeners() {
                    @Override
                    public void onClick(Location location, int position) {
                        binding.SelectWard.setTextColor(getResources().getColor(R.color.color4));
                        binding.SelectWard.setText(location.getName());
                        binding.txtCity.setText(binding.SelectCity.getText()+"");
                        binding.txtDistrict.setText(binding.SelectDistrict.getText()+"");
                        binding.txtWard.setText(binding.SelectWard.getText()+"");
                        binding.layoutAddress1.setVisibility(View.GONE);

                        if(positionWard != -1){
                            locationWard.notifyItemChanged(positionWard);
                        }
                        positionWard = position;
                    }
                });

                binding.rclWard.setLayoutManager(gridLayoutManager);
                binding.rclWard.setAdapter(locationWard);
                break;
        }
    }

    private void changeLocation(int code){
        switch (code){
            case 1:
                binding.rclCity.setVisibility(View.VISIBLE);
                binding.rclDistrict.setVisibility(View.GONE);
                binding.rclWard.setVisibility(View.GONE);

                binding.SelectCity.setTextColor(getResources().getColor(R.color.color4));
                binding.SelectDistrict.setTextColor(getResources().getColor(R.color.black));
                binding.SelectWard.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                binding.rclCity.setVisibility(View.GONE);
                binding.rclDistrict.setVisibility(View.VISIBLE);
                binding.rclWard.setVisibility(View.GONE);

                binding.SelectCity.setTextColor(getResources().getColor(R.color.black));
                binding.SelectDistrict.setTextColor(getResources().getColor(R.color.color4));
                binding.SelectWard.setTextColor(getResources().getColor(R.color.black));
                break;
            case 3:
                binding.rclCity.setVisibility(View.GONE);
                binding.rclDistrict.setVisibility(View.GONE);
                binding.rclWard.setVisibility(View.VISIBLE);

                binding.SelectCity.setTextColor(getResources().getColor(R.color.black));
                binding.SelectDistrict.setTextColor(getResources().getColor(R.color.black));
                binding.SelectWard.setTextColor(getResources().getColor(R.color.color4));
                break;
        }
    }

    private void setListeners(){
        binding.SelectCity.setOnClickListener(v -> {
            changeLocation(1);
        });

        binding.SelectDistrict.setOnClickListener(v -> {
            changeLocation(2);
        });

        binding.SelectWard.setOnClickListener(v -> {
            changeLocation(3);
        });

        binding.btnAddress.setOnClickListener(v -> {
            binding.layoutAddress1.setVisibility(View.VISIBLE);
        });

        binding.btnClose.setOnClickListener(v -> {
            binding.layoutAddress1.setVisibility(View.GONE);
            if(positionProvince != -1){
                binding.txtCity.setText(binding.SelectCity.getText()+"");
            }else{
                binding.txtCity.setText("");
            }

            if(positionDistrict != -1){
                binding.txtDistrict.setText(binding.SelectDistrict.getText()+"");
            }else{
                binding.txtDistrict.setText("");
            }

            if(positionWard != -1){
                binding.txtWard.setText(binding.SelectWard.getText()+"");
            }else{
                binding.txtWard.setText("");
            }
        });
    }
}