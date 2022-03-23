package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.User;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Home;
import com.personal_game.datn.Response.Message_Info;
import com.personal_game.datn.Response.UserInfo;
import com.personal_game.datn.databinding.ActivityFavouriteBinding;
import com.personal_game.datn.databinding.ActivityInfoBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {
    private ActivityInfoBinding binding;
    private CostumeAdapter costumeAdapter;
    private Shared_Preferences shared_preferences;

    private List<CostumeHome> costumeFavourites;
    private boolean isSex = true;
    private boolean checkImg = false;
    private String path;
    private User user;

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
        getInfo();
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

    private void changeSex(boolean value){
        if(value){
            binding.btnSexMale.setImageResource(R.drawable.circle_check);
            binding.btnSexFemale.setImageResource(R.drawable.circle_none);
            isSex = true;
        }else{
            binding.btnSexMale.setImageResource(R.drawable.circle_none);
            binding.btnSexFemale.setImageResource(R.drawable.circle_check);
            isSex = false;
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

        binding.btnSelectMale.setOnClickListener(v -> {
            changeSex(true);
        });

        binding.btnSelectFemale.setOnClickListener(v -> {
            changeSex(false);
        });

        binding.btnUp.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            Intent result = Intent.createChooser(intent, getText(R.string.choose_file));
            startActivityForResult(result, 10);
        });

        binding.btnSave.setOnClickListener(v -> {
            if(!user.getFullName().equals(binding.txtNameLayoutInfo.getText()+"") || user.getSex() != isSex){
                User updateInfo = new User(binding.txtNameLayoutInfo.getText()+"", isSex);

                upInfo(updateInfo);
            }

            if(checkImg){
                upImg();
            }
        });
    }

    private void getInfo(){
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
                    isSex = userInfo.getUser().getSex();
                    String sex = "Giới tính: Nam";
                    if(!isSex){
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

                    user = userInfo.getUser();
                    setLayoutInfo();

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

    private void setLayoutInfo(){
        Picasso.Builder builder1 = new Picasso.Builder(getApplicationContext());
        builder1.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                binding.imgMain2.setImageResource(R.drawable.logo);
            }
        });
        Picasso pic1 = builder1.build();
        pic1.load(user.getImg()).into(binding.imgMain2);

        binding.txtPhoneLayoutInfo.setText(user.getPhone());
        binding.txtNameLayoutInfo.setText(user.getFullName());
        if(isSex){
            binding.btnSexMale.setImageResource(R.drawable.circle_check);
        }else{
            binding.btnSexFemale.setImageResource(R.drawable.circle_check);
        }
    }

    private void setCostumeFavourite(){
        costumeAdapter = new CostumeAdapter(costumeFavourites, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclCostumeFavourite.setLayoutManager(gridLayoutManager);
        binding.rclCostumeFavourite.setAdapter(costumeAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(resultCode == RESULT_OK){
                ActivityResult result = new ActivityResult(resultCode, data);

                if (result.getResultCode() == RESULT_OK) {
                    for (int i = 0; i < result.getData().getClipData().getItemCount(); i++) {
                        Uri imageUri = result.getData().getClipData().getItemAt(i).getUri();
                        if(requestCode == 10) {
                            path = getRealPathFromURI(imageUri);
                            binding.imgMain.setImageURI(imageUri);
                            binding.imgMain2.setImageURI(imageUri);
                            checkImg = true;
                        }
                    }
                }
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void upInfo(User updateUser){
        Service service = getRetrofit().create(Service.class);
        Call<Message> updateInfo = service.UpdateInfo("bearer " + shared_preferences.getToken(), updateUser);
        updateInfo.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getApplication(), "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void upImg(){
        String domain = "https://smartfashion.covid21tsp.space/";

        List<MultipartBody.Part> pho = new ArrayList<>();

        File f = new File(path);
        RequestBody photoContext = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", f.getName(), photoContext);

        pho.add(photo);

        Service service = getRetrofit().create(Service.class);
        Call<Message> upImg = service.UpdateImg("bearer " + shared_preferences.getToken(), photo, domain);
        upImg.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getApplication(), "Thay đổi ảnh thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}