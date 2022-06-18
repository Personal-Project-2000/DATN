package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Backup.Constant.SwitchCostumeActivity;
import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.personal_game.datn.Adapter.CostumeCoordinateAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Constant;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Dialog.ConfirmDialog;
import com.personal_game.datn.Models.Coordinate;
import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.Models.CostumeCoordinate;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_CostumeWithStyle;
import com.personal_game.datn.databinding.ActivityCoordinateBinding;
import com.personal_game.datn.ultilities.MyTouchListener;
import com.personal_game.datn.ultilities.RangeTime;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoordinateActivity extends AppCompatActivity {

    private ActivityCoordinateBinding binding;
    private Coordinate coordinate;
    private String styleCostumeId = null;
    private List<CostumeHome> costumes;
    private Shared_Preferences shared_preferences;
    private CostumeCoordinateAdapter costumeCoordinateAdapter;
    private int position;
    private boolean sex = true;
    private boolean isStateDetail = false;
    private Costume costume;
    private int priceTotal = 0;

    private CostumeCoordinate shirt = null;
    private CostumeCoordinate shoe = null;
    private CostumeCoordinate hat = null;
    private CostumeCoordinate trouser  = null;
    private CostumeCoordinate bag = null;

    private int mode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoordinateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        shared_preferences = new Shared_Preferences(getApplicationContext());

        Intent intent = getIntent();
        coordinate = (Coordinate) intent.getSerializableExtra("coordinate");
        styleCostumeId = intent.getStringExtra("styleId");
        position = intent.getIntExtra("position", -1);
        costume = (Costume) intent.getSerializableExtra("costume");

        getCoordinate();
        setListeners();
        if(styleCostumeId != null) {
            getCostume(styleCostumeId);
        }
    }

    private void loadingConfirm(boolean value){
        if(value){
            binding.btnConfirm.setVisibility(View.GONE);
            binding.progressBarConfirm.setVisibility(View.VISIBLE);
        }else{
            binding.btnConfirm.setVisibility(View.VISIBLE);
            binding.progressBarConfirm.setVisibility(View.GONE);
        }
    }

    private void loadingDelete(boolean value){
        if(value){
            binding.btnDelete.setVisibility(View.GONE);
            binding.progressBarDelete.setVisibility(View.VISIBLE);
        }else{
            binding.btnDelete.setVisibility(View.VISIBLE);
            binding.progressBarDelete.setVisibility(View.GONE);
        }
    }

    private void changeSex(){
        if(sex){
            sex = false;
            binding.txtSelectSex.setText("Giới tính: Nữ");
        }else{
            sex = true;
            binding.txtSelectSex.setText("Giới tính: Nam");
        }

        ConfirmDialog dialog = new ConfirmDialog(CoordinateActivity.this, "Bạn có muốn xóa những sản phẩm có sẵn?", new ConfirmDialog.ConfirmDialogListeners() {
            @Override
            public void onClickNo() {
                getCostume(styleCostumeId);
            }

            @Override
            public void onClickYes() {
                if(hat != null){
                    stateCostume(Constant.hatId, null, 0);
                }
                if(bag != null){
                    stateCostume(Constant.bagId, null, 0);
                }
                if(trouser != null){
                    stateCostume(Constant.trousersId, null, 0);
                }
                if(shirt != null){
                    stateCostume(Constant.shirtId, null, 0);
                }
                if(shoe != null){
                    stateCostume(Constant.shoeId, null, 0);
                }

                getCostume(styleCostumeId);
            }
        });

        dialog.show();
    }

    private void changeStateDetail(boolean value){
        isStateDetail = value;
        if(value){
            binding.btnSelect.setImageResource(R.drawable.ic_baseline_toggle_on_24);

            if(hat != null){
                binding.imgCloseHat.setVisibility(View.VISIBLE);
                binding.imgViewHat.setVisibility(View.VISIBLE);
                binding.txtPriceHat.setVisibility(View.VISIBLE);
            }
            if(trouser != null){
                binding.imgCloseTrouser.setVisibility(View.VISIBLE);
                binding.imgViewTrouser.setVisibility(View.VISIBLE);
                binding.txtPriceTrouser.setVisibility(View.VISIBLE);
            }
            if(shirt != null){
                binding.imgCloseShirt.setVisibility(View.VISIBLE);
                binding.imgViewShirt.setVisibility(View.VISIBLE);
                binding.txtPriceShirt.setVisibility(View.VISIBLE);
            }
            if(bag != null){
                binding.imgCloseBag.setVisibility(View.VISIBLE);
                binding.imgViewBag.setVisibility(View.VISIBLE);
                binding.txtPriceBag.setVisibility(View.VISIBLE);
            }
            if(shoe != null){
                binding.imgCloseShoe.setVisibility(View.VISIBLE);
                binding.imgViewShoe.setVisibility(View.VISIBLE);
                binding.txtPriceShoe.setVisibility(View.VISIBLE);
            }
        }
        else{
            binding.btnSelect.setImageResource(R.drawable.ic_baseline_toggle_off_24);

            binding.imgCloseHat.setVisibility(View.GONE);
            binding.imgViewHat.setVisibility(View.GONE);
            binding.txtPriceHat.setVisibility(View.GONE);
            binding.imgCloseTrouser.setVisibility(View.GONE);
            binding.imgViewTrouser.setVisibility(View.GONE);
            binding.txtPriceTrouser.setVisibility(View.GONE);
            binding.imgCloseShirt.setVisibility(View.GONE);
            binding.imgViewShirt.setVisibility(View.GONE);
            binding.txtPriceShirt.setVisibility(View.GONE);
            binding.imgCloseBag.setVisibility(View.GONE);
            binding.imgViewBag.setVisibility(View.GONE);
            binding.txtPriceBag.setVisibility(View.GONE);
            binding.imgCloseShoe.setVisibility(View.GONE);
            binding.imgViewShoe.setVisibility(View.GONE);
            binding.txtPriceShoe.setVisibility(View.GONE);
        }
    }

    private void setView(CostumeCoordinate costumeTemp, int price, TextView imgClose, ImageView imgView, TextView txtPrice, ImageView img, String costumeStyleId){
        Log.i("costumeStyleId", price+"");
        if(costumeTemp != null){
            if(isStateDetail) {
                imgClose.setVisibility(View.VISIBLE);
                imgView.setVisibility(View.VISIBLE);
                txtPrice.setVisibility(View.VISIBLE);
            }
            txtPrice.setText(intConvertMoney(price));

            costumeTemp.getCostumeInfo().setPrice(price);
            priceTotal += costumeTemp.getCostumeInfo().getPrice();
            switch (costumeStyleId){
                case Constant.bagId:
                    bag = costumeTemp;
                    break;
                case Constant.hatId:
                    hat = costumeTemp;
                    break;
                case Constant.shirtId:
                    shirt = costumeTemp;
                    break;
                case Constant.trousersId: case Constant.skirtId:
                    trouser = costumeTemp;
                    break;
                case Constant.shoeId:
                    shoe = costumeTemp;
                    break;
            }

            if(costumeTemp.getImage() != null) {
                Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                builder.listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        img.setImageResource(R.drawable.logo);
                    }
                });
                Picasso pic = builder.build();
                pic.load(costumeTemp.getImage()).into(img);
            }

            img.setOnTouchListener(new MyTouchListener());
        }else{;
            switch (costumeStyleId){
                case Constant.bagId:
                    priceTotal -= bag.getCostumeInfo().getPrice();
                    bag = null;
                    break;
                case Constant.hatId:
                    priceTotal -= hat.getCostumeInfo().getPrice();
                    hat = null;
                    break;
                case Constant.shirtId:
                    priceTotal -= shirt.getCostumeInfo().getPrice();
                    shirt = null;
                    break;
                case Constant.trousersId: case Constant.skirtId:
                    priceTotal -= trouser.getCostumeInfo().getPrice();
                    trouser = null;
                    break;
                case Constant.shoeId:
                    priceTotal -= shoe.getCostumeInfo().getPrice();
                    shoe = null;
                    break;
            }
            imgClose.setVisibility(View.GONE);
            imgView.setVisibility(View.GONE);
            txtPrice.setVisibility(View.GONE);
            img.setImageResource(R.drawable.cong);

            img.setOnTouchListener(null);
        }
    }

    //null: xóa costume khổi set đồ, not null: tồn tại costume trong set đồ
    private void stateCostume(String costumeStyleId, CostumeCoordinate costumeTemp, int price){
        switch (costumeStyleId){
            case Constant.bagId:{
                setView(costumeTemp, price, binding.imgCloseBag, binding.imgViewBag, binding.txtPriceBag, binding.imgBag, costumeStyleId);
            }
            break;
            case Constant.hatId:{
                setView(costumeTemp, price, binding.imgCloseHat, binding.imgViewHat, binding.txtPriceHat, binding.imgHat, costumeStyleId);
            }
            break;
            case Constant.trousersId: case Constant.skirtId:{
                setView(costumeTemp, price, binding.imgCloseTrouser, binding.imgViewTrouser, binding.txtPriceTrouser, binding.imgTrouser, costumeStyleId);
            }
            break;
            case Constant.shirtId:{
                setView(costumeTemp, price, binding.imgCloseShirt, binding.imgViewShirt, binding.txtPriceShirt, binding.imgShirt, costumeStyleId);
            }
            break;
            case Constant.shoeId:{
                setView(costumeTemp, price, binding.imgCloseShoe, binding.imgViewShoe, binding.txtPriceShoe, binding.imgShoe, costumeStyleId);
            }
            break;
        }
    }

    private void SwitchCostumeActivity(String costumeId){
        Intent intent = new Intent(CoordinateActivity.this, CostumeActivity.class);
        intent.putExtra("costumeId", costumeId);
        startActivity(intent);
    }

    private void setListeners(){
        binding.layoutSelectPrice.setOnClickListener(v -> {
            changeStateDetail(!isStateDetail);
        });

        binding.imgShirt.setOnClickListener(v -> {
            callApiCostume(shirt, Constant.shirtId);
        });

        binding.imgTrouser.setOnClickListener(v -> {
            callApiCostume(trouser, Constant.trousersId);
        });

        binding.imgBag.setOnClickListener(v -> {
            callApiCostume(bag, Constant.bagId);
        });

        binding.imgHat.setOnClickListener(v -> {
            callApiCostume(hat, Constant.hatId);
        });

        binding.imgShoe.setOnClickListener(v -> {
            callApiCostume(shoe, Constant.shoeId);
        });

        binding.btnConfirm.setOnClickListener(v -> {
            if(coordinate.getId() == null)
                createCoordinate();
            else
                updateCoordinate();
        });

        binding.btnDelete.setOnClickListener(v -> {
            //deleteCoordinate();
            finish();
        });

        binding.imgCloseBag.setOnClickListener(v -> {
            stateCostume(Constant.bagId, null, 0);
            binding.txtPrice.setText(intConvertMoney(priceTotal));
        });

        binding.imgCloseHat.setOnClickListener(v -> {
            stateCostume(Constant.hatId, null, 0);
            binding.txtPrice.setText(intConvertMoney(priceTotal));
        });

        binding.imgCloseShoe.setOnClickListener(v -> {
            stateCostume(Constant.shoeId, null, 0);
            binding.txtPrice.setText(intConvertMoney(priceTotal));
        });

        binding.imgCloseTrouser.setOnClickListener(v -> {
            stateCostume(Constant.trousersId, null, 0);
            binding.txtPrice.setText(intConvertMoney(priceTotal));
        });

        binding.imgCloseShirt.setOnClickListener(v -> {
            stateCostume(Constant.shirtId, null, 0);
            binding.txtPrice.setText(intConvertMoney(priceTotal));
        });

        binding.layoutSex.setOnClickListener(v -> {
            changeSex();
        });

        binding.imgViewHat.setOnClickListener(v -> {
            SwitchCostumeActivity(hat.getCostumeId());
        });

        binding.imgViewBag.setOnClickListener(v -> {
            SwitchCostumeActivity(bag.getCostumeId());
        });

        binding.imgViewShirt.setOnClickListener(v -> {
            SwitchCostumeActivity(shirt.getCostumeId());
        });

        binding.imgViewShoe.setOnClickListener(v -> {
            SwitchCostumeActivity(shoe.getCostumeId());
        });

        binding.imgViewTrouser.setOnClickListener(v -> {
            SwitchCostumeActivity(trouser.getCostumeId());
        });

        if(shirt != null)
            binding.imgShirt.setOnTouchListener(new MyTouchListener());

        if(shoe != null)
            binding.imgShoe.setOnTouchListener(new MyTouchListener());

        if(hat != null)
            binding.imgHat.setOnTouchListener(new MyTouchListener());

        if(trouser != null)
            binding.imgTrouser.setOnTouchListener(new MyTouchListener());

        if(bag != null)
            binding.imgBag.setOnTouchListener(new MyTouchListener());
    }

    private void callApiCostume(CostumeCoordinate costumeCoordinate, String styleId){
        if(costumeCoordinate == null && styleCostumeId != styleId){
            styleCostumeId = styleId;
            getCostume(styleId);
        }
    }

    private void getCoordinate(){
        if(coordinate == null){
            coordinate = new Coordinate();

            List<CostumeCoordinate> coordinateList = new ArrayList<>();
            coordinate.setCostumes(coordinateList);
        }

        if(costume != null) {
            CostumeCoordinate costumeCoordinate = new CostumeCoordinate(costume.getId(),
                    costume.getCostumeStyleId(),
                    costume.getPictures().get(0).getLink(),
                    null,
                    costume);

            if(costume.getColors() != null){
                costumeCoordinate.setColor(costumeCoordinate.getColor());
            }

            coordinate.getCostumes().add(costumeCoordinate);
        }

        if(!coordinate.getCostumes().get(0).getCostumeInfo().getSex()){
            binding.txtSelectSex.setText("Giới tính: Nữ");
            sex = false;
        }

        for (CostumeCoordinate costumeCoordinate : coordinate.getCostumes()) {
            if(costumeCoordinate.getCostumeInfo().getPromotion() != null) {
                long millisFutureStartTime = RangeTime.getBetweenDayToNow(costumeCoordinate.getCostumeInfo().getPromotion().getStartTime());

                if (millisFutureStartTime <= 0) {
                    long millisFutureEndTime = RangeTime.getBetweenDayToNow(costumeCoordinate.getCostumeInfo().getPromotion().getEndTime());

                    if (millisFutureEndTime > 0) {
                        stateCostume(costumeCoordinate.getStyleId(), costumeCoordinate, costumeCoordinate.getCostumeInfo().getPrice() * (100 - costumeCoordinate.getCostumeInfo().getPromotion().getValue()) / 100);
                        continue;
                    }
                }
            }

            stateCostume(costumeCoordinate.getStyleId(), costumeCoordinate, costumeCoordinate.getCostumeInfo().getPrice());
        }

        binding.txtPrice.setText(intConvertMoney(priceTotal));
    }

    private void getRclCostume(){
        costumeCoordinateAdapter = new CostumeCoordinateAdapter(costumes, getApplicationContext(), new CostumeCoordinateAdapter.CostumeCoordinateListeners() {
            @Override
            public void onClick(CostumeHome costumeHome, int position, int price) {
                CostumeCoordinate costumeTemp = new CostumeCoordinate(costumeHome.getCostume().getId(),
                        costumeHome.getCostume().getCostumeStyleId(),
                        null,
                        null,
                        costumeHome.getCostume());

                if(costumeHome.getCostume().getColors() != null)
                    costumeTemp.setColor(costumeHome.getCostume().getColors().get(0));

                if(costumeHome.getCostume().getPicture_Nones() != null)
                    costumeTemp.setImage(costumeHome.getCostume().getPicture_Nones().get(0).getLink());
                else if(costumeHome.getCostume().getPictures() != null)
                    costumeTemp.setImage(costumeHome.getCostume().getPictures().get(0).getLink());

                stateCostume(costumeHome.getCostume().getCostumeStyleId(), costumeTemp, price);
                binding.txtPrice.setText(intConvertMoney(priceTotal));
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclCostume.setLayoutManager(gridLayoutManager);
        binding.rclCostume.setAdapter(costumeCoordinateAdapter);
    }

    private void getCostume(String styleId){
        Service service = getRetrofit().create(Service.class);
        Call<Message_CostumeWithStyle> call = service.StyleAndSex("bearer "+shared_preferences.getToken(), styleId, sex);
        call.enqueue(new Callback<Message_CostumeWithStyle>() {
            @Override
            public void onResponse(Call<Message_CostumeWithStyle> call, Response<Message_CostumeWithStyle> response) {
                if(response.body().getStatus() == 1){
                    costumes = response.body().getCostumes();

                    if(costumes != null) {
                        getRclCostume();
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

    private void updateCoordinate(){
        loadingConfirm(true);

        List<CostumeCoordinate> coordinateList = new ArrayList<>();

        if(shirt != null)
            coordinateList.add(shirt);
        if(shoe != null)
            coordinateList.add(shoe);
        if(hat != null)
            coordinateList.add(hat);
        if(trouser != null)
            coordinateList.add(trouser);
        if(bag != null)
            coordinateList.add(bag);

        coordinate.setCostumes(coordinateList);

        Service service = getRetrofit().create(Service.class);
        Call<Message> call = service.UpdateCoordinate("bearer "+shared_preferences.getToken(), coordinate);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                loadingConfirm(false);

                if(response.body().getStatus() == 1){
                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    intent.putExtra("coordinate", coordinate);
                    setResult(11, intent);
                    finish();
                }else{
                    Toast.makeText(getApplication(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                loadingConfirm(false);
                Toast.makeText(getApplication(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createCoordinate(){
        loadingConfirm(true);

        coordinate = new Coordinate();

        List<CostumeCoordinate> coordinateList = new ArrayList<>();

        if(shirt != null)
            coordinateList.add(shirt);
        if(shoe != null)
            coordinateList.add(shoe);
        if(hat != null)
            coordinateList.add(hat);
        if(trouser != null)
            coordinateList.add(trouser);
        if(bag != null)
            coordinateList.add(bag);

        coordinate.setCostumes(coordinateList);

        Service service = getRetrofit().create(Service.class);
        Call<Message> call = service.CreateCoordinate("bearer "+shared_preferences.getToken(), coordinate);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                loadingConfirm(false);

                if(response.body().getStatus() == 1){
                    coordinate.setId(response.body().getId());

                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    intent.putExtra("coordinate", coordinate);
                    setResult(12, intent);
                    finish();
                }else{
                    Toast.makeText(getApplication(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                loadingConfirm(false);
                Toast.makeText(getApplication(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteCoordinate(){
        loadingDelete(true);

        Service service = getRetrofit().create(Service.class);
        Call<Message> call = service.DeleteCoordinate("bearer "+shared_preferences.getToken(), coordinate.getId());
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                loadingDelete(false);

                if(response.body().getStatus() == 1){
                    coordinate.setId(response.body().getId());

                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    setResult(13, intent);
                    finish();
                }else{
                    Toast.makeText(getApplication(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                loadingDelete(false);
                Toast.makeText(getApplication(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}