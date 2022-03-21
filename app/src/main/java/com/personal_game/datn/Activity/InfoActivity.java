package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Message_Home;
import com.personal_game.datn.Response.Message_Info;
import com.personal_game.datn.Response.UserInfo;
import com.personal_game.datn.databinding.ActivityFavouriteBinding;
import com.personal_game.datn.databinding.ActivityInfoBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {
    private ActivityInfoBinding binding;
    private CostumeAdapter costumeAdapter;
    private Shared_Preferences shared_preferences;

    private List<CostumeHome> costumeFavourites;

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

        shared_preferences = new Shared_Preferences(getApplicationContext());

        setListeners();
        setInfo();
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

    private void setInfo(){
        Service service = getRetrofit().create(Service.class);
        Call<Message_Info> info = service.Info("bearer "+shared_preferences.getToken());
        info.enqueue(new Callback<Message_Info>() {
            @Override
            public void onResponse(Call<Message_Info> call, Response<Message_Info> response) {
                if(response.body().getStatus() == 1){
                    costumeFavourites = response.body().getUser().getCostumeFavourites();
                    UserInfo userInfo = response.body().getUser();
                    binding.txtName.setText("Họ & Tên: "+userInfo.getUser().getFullName());
                    binding.txtPhone.setText("Số điện thoại: "+userInfo.getUser().getPhone());
                    String sex = "Giới tính: Nam";
                    if(!userInfo.getUser().getSex()){
                        sex = "Giới tính: Nữ";
                    }
                    binding.txtSex.setText(sex);
                    Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgMain.setImageResource(R.drawable.logo);
                        }
                    });
                    Picasso pic = builder.build();
                    pic.load(userInfo.getUser().getImg()).into(binding.imgMain);

                    if(userInfo.getAddressDefault() == null){
                        binding.txtNameAddress.setText("Bạn chưa có địa chỉ giao hàng");
                        binding.txtPhoneAddress.setText("");
                        binding.txtAddress.setText("");
                        binding.txtAddress1.setText("");
                    }else{
                        binding.txtNameAddress.setText(userInfo.getAddressDefault().getName());
                        binding.txtPhoneAddress.setText(userInfo.getAddressDefault().getPhone());
                        binding.txtAddress.setText(userInfo.getAddressDefault().getStreet());
                        binding.txtAddress1.setText(userInfo.getAddressDefault().getWard()+" - "+
                                userInfo.getAddressDefault().getDistrict()+" - "+
                                userInfo.getAddressDefault().getCity());
                    }

                    setCostumeFavourite();
                }

                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message_Info> call, Throwable t) {
                Toast.makeText(getApplication(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCostumeFavourite(){
        costumeAdapter = new CostumeAdapter(costumeFavourites, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclCostumeFavourite.setLayoutManager(gridLayoutManager);
        binding.rclCostumeFavourite.setAdapter(costumeAdapter);
    }
}