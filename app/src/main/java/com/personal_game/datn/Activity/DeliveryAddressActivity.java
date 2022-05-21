package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Api.RetrofitLocation1.getRetrofitLocation;

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
import com.personal_game.datn.Api.ModelLocation1.DistrictModel;
import com.personal_game.datn.Api.ModelLocation1.Location;
import com.personal_game.datn.Api.ModelLocation1.MessageWard;
import com.personal_game.datn.Api.ModelLocation1.RequestWard;
import com.personal_game.datn.Api.ModelLocation1.WardModel;
import com.personal_game.datn.Api.ModelLocation1.MessageDistrict;
import com.personal_game.datn.Api.ModelLocation1.MessageProvince;
import com.personal_game.datn.Api.ModelLocation1.ProvinceModel;
import com.personal_game.datn.Api.ModelLocation1.RequestDistrict;
import com.personal_game.datn.Api.RetrofitLocation1;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Api.ServiceApi.ServiceLocation;
import com.personal_game.datn.Api.ServiceApi.ServiceLocation1;
import com.personal_game.datn.Backup.Constant;
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
    private List<ProvinceModel> provinceModels;
    private List<DistrictModel> districtModels;
    private List<WardModel> wardModels;
    private int positionProvince = -1;
    private int positionDistrict = -1;
    private int positionWard = -1;
    private String cityId;
    private String districtId;
    private String wardId;
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
        ServiceLocation1 serviceAPI = RetrofitLocation1.getRetrofitLocation().create(ServiceLocation1.class);
        Call<MessageProvince> call = serviceAPI.GetProvinces(Constant.tokenLocation);
        call.enqueue(new Callback<MessageProvince>() {
            @Override
            public void onResponse(Call<MessageProvince> call, Response<MessageProvince> response) {
                provinceModels = response.body().getData();

                ArrayList<Location> locations = new ArrayList<>();
                for (ProvinceModel province: provinceModels) {
                    locations.add(new Location(province.getProvinceID()+"", province.getProvinceName()));
                }

                setRcl(locations, 1);
            }

            @Override
            public void onFailure(Call<MessageProvince> call, Throwable t) {

            }
        });
    }

    private void setDistrict(int code){
        //Toast.makeText(getApplicationContext(), code+"", Toast.LENGTH_SHORT).show();
        districtModels = new ArrayList<>();
        ServiceLocation1 serviceAPI = getRetrofitLocation().create(ServiceLocation1.class);
        Call<MessageDistrict> call = serviceAPI.GetDistricts(Constant.tokenLocation, new RequestDistrict(code));
        call.enqueue(new Callback<MessageDistrict>() {
            @Override
            public void onResponse(Call<MessageDistrict> call, Response<MessageDistrict> response) {
                districtModels.add(new DistrictModel(1, "huyen"));
                if (response.body() != null && response.body().getData().size() > 0) {
                    districtModels = response.body().getData();
                }

                ArrayList<Location> locations = new ArrayList<>();
                for (DistrictModel districtModel: districtModels) {
                    locations.add(new Location(districtModel.getDistrictID()+"", districtModel.getDistrictName()));
                }

                binding.rclCity.setVisibility(View.GONE);
                binding.SelectCity.setTextColor(getResources().getColor(R.color.black));
                binding.rclDistrict.setVisibility(View.VISIBLE);

                setRcl(locations, 2);
            }

            @Override
            public void onFailure(Call<MessageDistrict> call, Throwable t) {
                Log.d("log:", t.getMessage());
            }
        });
    }

    private void setWard(int code){
        wardModels = new ArrayList<>();
        ServiceLocation1 serviceAPI = getRetrofitLocation().create(ServiceLocation1.class);
        Call<MessageWard> call = serviceAPI.GetWards(Constant.tokenLocation, new RequestWard(code));
        call.enqueue(new Callback<MessageWard>() {
            @Override
            public void onResponse(Call<MessageWard> call, Response<MessageWard> response) {
                wardModels.add(new WardModel("-1", "phuong"));
                if (response.body() != null && response.body().getData().size() > 0) {
                    wardModels = response.body().getData();
                }

                ArrayList<Location> locations = new ArrayList<>();
                for (WardModel wardModel: wardModels) {
                    locations.add(new Location(wardModel.getWardCode(), wardModel.getWardName()));
                }

                binding.rclDistrict.setVisibility(View.GONE);
                binding.SelectDistrict.setTextColor(getResources().getColor(R.color.black));
                binding.rclWard.setVisibility(View.VISIBLE);

                setRcl(locations, 3);
            }

            @Override
            public void onFailure(Call<MessageWard> call, Throwable t) {
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
                        cityId = location.getCode();
                        setDistrict(Integer.parseInt(location.getCode()));
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
                        districtId = location.getCode();
                        setWard(Integer.parseInt(location.getCode()));
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
                        wardId = location.getCode();
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
                    cityId,
                    districtId,
                    wardId,
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
                    newAddress.setId(response.body().getId());
                    shared_preferences.saveAddress(newAddress);

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