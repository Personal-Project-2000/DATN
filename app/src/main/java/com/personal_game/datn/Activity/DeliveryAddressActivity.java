package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Api.RetrofitLocation.getRetrofitLocation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.personal_game.datn.Adapter.CostumeImgAdapter;
import com.personal_game.datn.Adapter.LocationAdapter;
import com.personal_game.datn.Api.ModelLocation.DistrictModel;
import com.personal_game.datn.Api.ModelLocation.Location;
import com.personal_game.datn.Api.ModelLocation.ProvinceModel;
import com.personal_game.datn.Api.ModelLocation.WardModel;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Api.ServiceApi.ServiceLocation;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Address;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Info;
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

    private Shared_Preferences shared_preferences;
    private boolean addressDefault = false;
    private Address address;

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

        shared_preferences = new Shared_Preferences(getApplicationContext());

        setAddress();
        setCity();
        setListeners();
    }

    private void setAddress(){
        address = (Address) getIntent().getSerializableExtra("address");

        if(address != null){
            String name[] = address.getName().split(" ");
            String firstName = "";
            for(int i = 0; i < name.length-1; i ++){
                firstName += name[i] +" ";
            }

            binding.txtLastName.setText(name[name.length-1]);
            binding.txtFirstName.setText(firstName);
            binding.txtPhone.setText(address.getPhone());
            binding.txtStreet.setText(address.getStreet());
            binding.txtWard.setText(address.getWard());
            binding.txtDistrict.setText(address.getDistrict());
            binding.txtCity.setText(address.getCity());
            addressDefault = address.getDefault();

            if(addressDefault){
                binding.btnDefault.setImageResource(R.drawable.ic_baseline_toggle_on_24);
            }else{
                binding.btnDefault.setImageResource(R.drawable.ic_baseline_toggle_off_24);
            }
        }
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

        binding.btnDefault.setOnClickListener(v -> {
            if(addressDefault){
                binding.btnDefault.setImageResource(R.drawable.ic_baseline_toggle_off_24);
            }else{
                binding.btnDefault.setImageResource(R.drawable.ic_baseline_toggle_on_24);
            }
            addressDefault = !addressDefault;
        });

        binding.btnSave.setOnClickListener(v -> {
            Address newAddress = new Address(binding.txtFirstName.getText()+" "+binding.txtLastName.getText(),
                    binding.txtWard.getText()+"",
                    binding.txtDistrict.getText()+"",
                    binding.txtCity.getText()+"",
                    binding.txtStreet.getText()+"",
                    binding.txtPhone.getText()+"",
                    addressDefault,
                    shared_preferences.getAccount());

            AddAddress(newAddress);
        });

        binding.btnBackBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void loading(boolean Loading) {
        if (Loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.btnSave.setVisibility(View.GONE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnSave.setVisibility(View.VISIBLE);
        }
    }

    private void AddAddress(Address newAddress){
        loading(true);

        Service service = getRetrofit().create(Service.class);
        Call<Message> addAddress = service.AddAddress("bearer "+shared_preferences.getToken(), newAddress);
        addAddress.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    Intent intent = new Intent(getApplication(), AddressActivity.class);
                    startActivity(intent);
                }
                loading(false);
                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                loading(false);
                Toast.makeText(getApplicationContext(), "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}