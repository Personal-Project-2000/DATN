package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.personal_game.datn.Adapter.CostumeCoordinateAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Constant;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Coordinate;
import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.Models.CostumeCoordinate;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_CostumeWithStyle;
import com.personal_game.datn.databinding.ActivityCoordinateBinding;
import com.personal_game.datn.ultilities.MyTouchListener;
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
    private Costume costume;

    private CostumeCoordinate shirt = null;
    private CostumeCoordinate shoe = null;
    private CostumeCoordinate hat = null;
    private CostumeCoordinate trouser  = null;
    private CostumeCoordinate bag = null;
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
    }

    private void setListeners(){
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
            deleteCoordinate();
        });

        binding.imgCloseBag.setOnClickListener(v -> {
            bag = null;
            binding.imgCloseBag.setVisibility(View.GONE);
            binding.imgBag.setImageResource(R.drawable.cong);

            binding.imgBag.setOnTouchListener(null);
        });

        binding.imgCloseHat.setOnClickListener(v -> {
            hat = null;
            binding.imgCloseHat.setVisibility(View.GONE);
            binding.imgHat.setImageResource(R.drawable.cong);

            binding.imgHat.setOnTouchListener(null);
        });

        binding.imgCloseShoe.setOnClickListener(v -> {
            shoe = null;
            binding.imgCloseShoe.setVisibility(View.GONE);
            binding.imgShoe.setImageResource(R.drawable.cong);

            binding.imgShoe.setOnTouchListener(null);
        });

        binding.imgCloseTrouser.setOnClickListener(v -> {
            trouser = null;
            binding.imgCloseTrouser.setVisibility(View.GONE);
            binding.imgTrouser.setImageResource(R.drawable.cong);

            binding.imgTrouser.setOnTouchListener(null);
        });

        binding.imgCloseShirt.setOnClickListener(v -> {
            shirt = null;
            binding.imgCloseShirt.setVisibility(View.GONE);
            binding.imgShirt.setImageResource(R.drawable.cong);

            binding.imgShirt.setOnTouchListener(null);
        });

        binding.layoutSex.setOnClickListener(v -> {
            changeSex();
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
                    null);

            if(costume.getColors() != null){
                costumeCoordinate.setColor(costumeCoordinate.getColor());
            }

            coordinate.getCostumes().add(costumeCoordinate);
        }

        for (CostumeCoordinate costumeCoordinate : coordinate.getCostumes()) {
            switch (costumeCoordinate.getStyleId()) {
                case Constant.bagId: {
                    binding.imgCloseBag.setVisibility(View.VISIBLE);
                    bag = costumeCoordinate;

                    Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgBag.setImageResource(R.drawable.cong);
                        }
                    });
                    Picasso pic = builder.build();
                    pic.load(costumeCoordinate.getImage()).into(binding.imgBag);
                }
                break;
                case Constant.hatId: {
                    binding.imgCloseHat.setVisibility(View.VISIBLE);
                    hat = costumeCoordinate;

                    Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgHat.setImageResource(R.drawable.cong);
                        }
                    });
                    Picasso pic = builder.build();
                    pic.load(costumeCoordinate.getImage()).into(binding.imgHat);
                }
                break;
                case Constant.shirtId: {
                    binding.imgCloseShirt.setVisibility(View.VISIBLE);
                    shirt = costumeCoordinate;

                    Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgShirt.setImageResource(R.drawable.cong);
                        }
                    });
                    Picasso pic = builder.build();
                    pic.load(costumeCoordinate.getImage()).into(binding.imgShirt);
                }
                break;
                case Constant.trousersId: {
                    binding.imgCloseTrouser.setVisibility(View.VISIBLE);
                    trouser = costumeCoordinate;

                    Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgTrouser.setImageResource(R.drawable.cong);
                        }
                    });
                    Picasso pic = builder.build();
                    pic.load(costumeCoordinate.getImage()).into(binding.imgTrouser);
                }
                break;
                case Constant.shoeId: {
                    binding.imgCloseShoe.setVisibility(View.VISIBLE);
                    shoe = costumeCoordinate;

                    Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgShoe.setImageResource(R.drawable.cong);
                        }
                    });
                    Picasso pic = builder.build();
                    pic.load(costumeCoordinate.getImage()).into(binding.imgShoe);
                }
                break;
            }
        }
    }

    private void getRclCostume(){
        costumeCoordinateAdapter = new CostumeCoordinateAdapter(costumes, getApplicationContext(), new CostumeCoordinateAdapter.CostumeCoordinateListeners() {
            @Override
            public void onClick(CostumeHome costumeHome, int position) {
                CostumeCoordinate costumeTemp = new CostumeCoordinate(costumeHome.getCostume().getId(),
                        costumeHome.getCostume().getCostumeStyleId(),
                        null,
                        null);

                if(costumeHome.getCostume().getColors() != null)
                    costumeTemp.setColor(costumeHome.getCostume().getColors().get(0));

                if(costumeHome.getCostume().getPicture_Nones() != null)
                    costumeTemp.setImage(costumeHome.getCostume().getPicture_Nones().get(0).getLink());
                else if(costumeHome.getCostume().getPictures() != null)
                    costumeTemp.setImage(costumeHome.getCostume().getPictures().get(0).getLink());

                switch (costumeHome.getCostume().getCostumeStyleId()){
                    case Constant.hatId:{
                        binding.imgCloseHat.setVisibility(View.VISIBLE);
                        hat = costumeTemp;

                        if(costumeTemp.getImage() != null) {
                            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    binding.imgHat.setImageResource(R.drawable.logo);
                                }
                            });
                            Picasso pic = builder.build();
                            pic.load(costumeTemp.getImage()).into(binding.imgHat);
                        }

                        binding.imgHat.setOnTouchListener(new MyTouchListener());
                    }break;
                    case Constant.shirtId:{
                        binding.imgCloseShirt.setVisibility(View.VISIBLE);
                        shirt = costumeTemp;

                        if(costumeTemp.getImage() != null) {
                            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    binding.imgShirt.setImageResource(R.drawable.logo);
                                }
                            });
                            Picasso pic = builder.build();
                            pic.load(costumeTemp.getImage()).into(binding.imgShirt);
                        }

                        binding.imgShirt.setOnTouchListener(new MyTouchListener());
                    }break;
                    case Constant.trousersId:{
                        binding.imgCloseTrouser.setVisibility(View.VISIBLE);
                        trouser = costumeTemp;

                        if(costumeTemp.getImage() != null) {
                            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    binding.imgTrouser.setImageResource(R.drawable.logo);
                                }
                            });
                            Picasso pic = builder.build();
                            pic.load(costumeTemp.getImage()).into(binding.imgTrouser);
                        }

                        binding.imgTrouser.setOnTouchListener(new MyTouchListener());
                    }break;
                    case Constant.bagId:{
                        binding.imgCloseBag.setVisibility(View.VISIBLE);
                        bag = costumeTemp;
                        if(costumeHome.getCostume().getColors() != null)
                            bag.setColor(costumeHome.getCostume().getColors().get(0));

                        if(costumeTemp.getImage() != null) {
                            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    binding.imgBag.setImageResource(R.drawable.logo);
                                }
                            });
                            Picasso pic = builder.build();
                            pic.load(costumeTemp.getImage()).into(binding.imgBag);
                        }

                        binding.imgBag.setOnTouchListener(new MyTouchListener());
                    }break;
                    case Constant.shoeId:{
                        binding.imgCloseShoe.setVisibility(View.VISIBLE);
                        shoe = costumeTemp;

                        if(costumeTemp.getImage() != null) {
                            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    binding.imgShoe.setImageResource(R.drawable.logo);
                                }
                            });
                            Picasso pic = builder.build();
                            pic.load(costumeTemp.getImage()).into(binding.imgShoe);
                        }

                        binding.imgShoe.setOnTouchListener(new MyTouchListener());
                    }break;
                }
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