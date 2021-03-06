package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Backup.Constant.allBill;
import static com.personal_game.datn.Backup.Constant.billCancel;
import static com.personal_game.datn.Backup.Constant.billComplete;
import static com.personal_game.datn.Backup.Constant.billHandle;
import static com.personal_game.datn.Backup.Constant.billPaid;
import static com.personal_game.datn.Backup.Constant.billTransported;
import static com.personal_game.datn.Backup.Constant.billWait;
import static com.personal_game.datn.Backup.Constant.trousersId;

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
import com.personal_game.datn.Backup.Constant;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Dialog.SizeDialog;
import com.personal_game.datn.Dialog.SizeGuideDialog;
import com.personal_game.datn.Models.Address;
import com.personal_game.datn.Models.CostumeStyle;
import com.personal_game.datn.Models.Size;
import com.personal_game.datn.Models.SizeUser;
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
    private List<CostumeStyle> costumeStyles = new ArrayList<>();
    private boolean isSex = true;
    private boolean checkImg = false;
    private Uri imageUri;
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

    private void loading(boolean value){
        if(value){
            binding.layoutMain1.setVisibility(View.GONE);
            binding.progressBarMain.setVisibility(View.VISIBLE);
        }else{
            binding.layoutMain1.setVisibility(View.VISIBLE);
            binding.progressBarMain.setVisibility(View.GONE);
        }
    }

    private void loadingSave(boolean value){
        if(value){
            binding.btnSave.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.btnSave.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void showSizeDialog(CostumeStyle costumeStyle, String id){
        List<Size> sizes = costumeStyle.getMenSizes();

        if(!isSex){
            sizes = costumeStyle.getWomenSizes();
        }
        SizeDialog dialog = new SizeDialog(InfoActivity.this, sizes, new SizeDialog.SizeDialogListeners() {
            @Override
            public void onClick(Size size) {
                if(id.equals(Constant.shirtId))
                    binding.txtSizeAo.setText(size.getName());
                else if(id.equals(Constant.trousersId))
                    binding.txtSizeQuan.setText(size.getName());
                else if(id.equals(Constant.shoeId))
                    binding.txtSizeGiay.setText(size.getName());
            }
        });

        dialog.show();
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

    //khi scroll s??? thay ?????i giao di???n
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

    //khi b???m v??o xem th??ng tin c?? nh??n
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
            binding.layoutMain.setScrollY(810);
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
            intent.putExtra("code", billCancel);
            startActivity(intent);
        });

        binding.btnBillComplete.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), BillActivity.class);
            intent.putExtra("code", billComplete);
            startActivity(intent);
        });

        binding.btnBillHandle.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), BillActivity.class);
            intent.putExtra("code", billHandle);
            startActivity(intent);
        });

        binding.btnBillTransported.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), BillActivity.class);
            intent.putExtra("code", billTransported);
            startActivity(intent);
        });

        binding.btnBillWaiting.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), BillActivity.class);
            intent.putExtra("code", billWait);
            startActivity(intent);
        });

        binding.btnBillPaid.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), BillActivity.class);
            intent.putExtra("code", billPaid);
            startActivity(intent);
        });

        binding.btnBillAll.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), BillActivity.class);
            intent.putExtra("code", allBill);
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
                List<SizeUser> sizeUsers = new ArrayList<>();
                sizeUsers.add(new SizeUser(Constant.shirtId, binding.txtSizeAo.getText()+""));
                sizeUsers.add(new SizeUser(Constant.trousersId, binding.txtSizeQuan.getText()+""));
                sizeUsers.add(new SizeUser(Constant.shoeId, binding.txtSizeGiay.getText()+""));
                User updateInfo = new User(binding.txtNameLayoutInfo.getText()+"", isSex, sizeUsers);

                upInfo(updateInfo);
            }

            if(checkImg){
                upImg();
            }
        });

        binding.btnBackBack.setOnClickListener(v -> {
            finish();
        });

        binding.layoutSizeAo.setOnClickListener(v -> {
            CostumeStyle costumeStyle = new CostumeStyle();

            for(CostumeStyle style: costumeStyles){
                if(style.getId().equals(Constant.shirtId)){
                    costumeStyle = style;
                }
            }

            showSizeDialog(costumeStyle, Constant.shirtId);
        });

        binding.layoutSizeQuan.setOnClickListener(v -> {
            CostumeStyle costumeStyle = new CostumeStyle();

            for(CostumeStyle style: costumeStyles){
                if(style.getId().equals(Constant.trousersId)){
                    costumeStyle = style;
                }
            }

            showSizeDialog(costumeStyle, trousersId);
        });

        binding.layoutSizeGiay.setOnClickListener(v -> {
            CostumeStyle costumeStyle = new CostumeStyle();

            for(CostumeStyle style: costumeStyles){
                if(style.getId().equals(Constant.shoeId)){
                    costumeStyle = style;
                }
            }

            showSizeDialog(costumeStyle, Constant.shoeId);
        });
    }

    private void getInfo(){
        loading(true);

        Service service = getRetrofit().create(Service.class);
        Call<Message_Info> info = service.Info("bearer "+shared_preferences.getToken());
        info.enqueue(new Callback<Message_Info>() {
            @Override
            public void onResponse(Call<Message_Info> call, Response<Message_Info> response) {
                if(response.body().getStatus() == 1){
                    UserInfo userInfo = response.body().getUser();
                    costumeFavourites = response.body().getUser().getCostumeFavourites();
                    user = userInfo.getUser();
                    costumeStyles = userInfo.getCostumeStyles();

                    setLayoutInfo();
                    setCostumeFavourite();

                    isSex = userInfo.getUser().getSex();
                    String sex = "Gi???i t??nh: Nam";
                    if(!isSex){
                        sex = "Gi???i t??nh: N???";
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
                    pic.load(shared_preferences.getImg()).into(binding.imgMain);

                    Picasso.Builder builder1 = new Picasso.Builder(getApplicationContext());
                    builder1.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgMain1.setImageResource(R.drawable.logo);
                        }
                    });
                    Picasso pic1 = builder1.build();
                    pic1.load(shared_preferences.getImg()).into(binding.imgMain1);

                    binding.txtName.setText("H??? & T??n: "+userInfo.getUser().getFullName());
                    binding.txtPhone.setText("S??? ??i???n tho???i: "+userInfo.getUser().getPhone());
                    binding.imgNumber.setText(response.body().getUser().getQuantityCart()+"");

                    List<SizeUser> sizeUsers = user.getSizes();
                    if(sizeUsers != null){
                        for(SizeUser sizeUser: sizeUsers){
                            if(sizeUser.getCostumeId().equals(Constant.shirtId)){
                                binding.txtSizeAo.setText(sizeUser.getSize());
                            }else if(sizeUser.getCostumeId().equals(Constant.trousersId)){
                                binding.txtSizeQuan.setText(sizeUser.getSize());
                            }else if(sizeUser.getCostumeId().equals(Constant.shoeId)){
                                binding.txtSizeGiay.setText(sizeUser.getSize());
                            }
                        }
                    }

                    if(userInfo.getAddressDefault() == null){
                        binding.txtNameAddress.setText("B???n ch??a c?? ?????a ch??? giao h??ng");
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
                }

                loading(false);
            }

            @Override
            public void onFailure(Call<Message_Info> call, Throwable t) {
                Toast.makeText(getApplication(), "L???y d??? li???u th???t b???i", Toast.LENGTH_SHORT).show();
                loading(false);
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
        pic1.load(shared_preferences.getImg()).into(binding.imgMain2);

        binding.txtPhoneLayoutInfo.setText(user.getPhone());
        binding.txtNameLayoutInfo.setText(user.getFullName());
        if(user.getSex()){
            binding.btnSexMale.setImageResource(R.drawable.circle_check);
        }else{
            binding.btnSexFemale.setImageResource(R.drawable.circle_check);
        }
    }

    private void setCostumeFavourite(){
        costumeAdapter = new CostumeAdapter(costumeFavourites, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClickFavourite(CostumeHome costume, int position) {
                costumeFavourites.remove(position);
                costumeAdapter.notifyItemRemoved(position);
            }
        });

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
                        imageUri = result.getData().getClipData().getItemAt(i).getUri();
                        if(requestCode == 10) {
                            path = getRealPathFromURI(imageUri);
                            binding.imgMain.setImageURI(imageUri);
                            binding.imgMain2.setImageURI(imageUri);
                            binding.imgMain1.setImageURI(imageUri);
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
        loadingSave(true);
        Service service = getRetrofit().create(Service.class);
        Call<Message> updateInfo = service.UpdateInfo("bearer " + shared_preferences.getToken(), updateUser);
        updateInfo.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    binding.txtName.setText("H??? & T??n: "+updateUser.getFullName());

                    isSex = updateUser.getSex();
                    String sex = "Gi???i t??nh: Nam";
                    if(!isSex){
                        sex = "Gi???i t??nh: N???";
                    }
                    binding.txtSex.setText(sex);

                    shared_preferences.saveName(updateUser.getFullName());
                }
                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
                loadingSave(false);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getApplication(), "C???p nh???t d??? li???u th???t b???i", Toast.LENGTH_SHORT).show();
                loadingSave(false);
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
                if(response.body().getStatus() == 1){
                    shared_preferences.saveImg(imageUri+"");
                }
                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getApplication(), "Thay ?????i ???nh th???t b???i", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Address addressTemp = shared_preferences.getAddress();

        binding.txtNameAddress.setText(addressTemp.getName());
        binding.txtPhoneAddress.setText(addressTemp.getPhone());
        binding.txtAddress.setText(addressTemp.getStreet());
        binding.txtAddress1.setText(addressTemp.getWard()+" - "+
                addressTemp.getDistrict()+" - "+
                addressTemp.getCity());
    }
}