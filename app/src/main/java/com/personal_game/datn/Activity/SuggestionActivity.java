package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Adapter.BodyAdapter;
import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeCartAdapter;
import com.personal_game.datn.Adapter.PersonalStyleAdapter;
import com.personal_game.datn.Adapter.PurposeAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Body;
import com.personal_game.datn.Models.PersonalStyle;
import com.personal_game.datn.Models.Purpose;
import com.personal_game.datn.Request.Request_Suggestion;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Message_CostumeWithStyle;
import com.personal_game.datn.Response.Message_Favourite;
import com.personal_game.datn.Response.Message_Suggestion;
import com.personal_game.datn.databinding.ActivityCartBinding;
import com.personal_game.datn.databinding.ActivitySuggestionBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionActivity extends AppCompatActivity {
    private ActivitySuggestionBinding binding;
    private CostumeAdapter costumeAdapter;
    private PersonalStyleAdapter personalStyleAdapter;
    private BodyAdapter bodyAdapter;
    private PurposeAdapter purposeAdapter;

    private Shared_Preferences shared_preferences;
    private List<CostumeHome> costumeSuggest;
    private List<PersonalStyle> personalStyles = new ArrayList<>();
    private List<Body> bodies = new ArrayList<>();
    private List<Purpose> purposes;
    private int prevPositionStyle = -1;
    private int prevBody = -1;
    private int prevPurpose = -1;
    public static boolean isSexSuggest = true;

    private final int layoutMain = 1;
    private final int layoutInfoStyle = 2;
    private final int layoutInfoBody = 3;
    private final int layoutInfoPurpose = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuggestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        shared_preferences = new Shared_Preferences(getApplicationContext());

        getSuggest();
        setListeners();
    }

    private void changeLayout(int code){
        switch (code){
            case layoutMain:
                binding.layoutMain.setVisibility(View.VISIBLE);
                binding.layoutInfoStyle.setVisibility(View.GONE);
                binding.layoutInfoBody.setVisibility(View.GONE);
                binding.layoutInfoPurpose.setVisibility(View.GONE);
                break;
            case layoutInfoStyle:
                binding.layoutMain.setVisibility(View.GONE);
                binding.layoutInfoStyle.setVisibility(View.VISIBLE);
                binding.layoutInfoBody.setVisibility(View.GONE);
                binding.layoutInfoPurpose.setVisibility(View.GONE);
                break;
            case layoutInfoBody:
                binding.layoutMain.setVisibility(View.GONE);
                binding.layoutInfoStyle.setVisibility(View.GONE);
                binding.layoutInfoBody.setVisibility(View.VISIBLE);
                binding.layoutInfoPurpose.setVisibility(View.GONE);
                break;
            case layoutInfoPurpose:
                binding.layoutMain.setVisibility(View.GONE);
                binding.layoutInfoStyle.setVisibility(View.GONE);
                binding.layoutInfoBody.setVisibility(View.GONE);
                binding.layoutInfoPurpose.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void changeSex(){
        if(isSexSuggest){
            binding.txtSelectSex.setText("Giới tính: Nữ");
            binding.txtSelectSexBody.setText("Giới tính: Nữ");
            binding.txtSelectSexStyle.setText("Giới tính: Nữ");
        }else{
            binding.txtSelectSex.setText("Giới tính: Nam");
            binding.txtSelectSexBody.setText("Giới tính: Nam");
            binding.txtSelectSexStyle.setText("Giới tính: Nam");
        }
        isSexSuggest = !isSexSuggest;
//        bodyAdapter.notifyDataSetChanged();
//        personalStyleAdapter.notifyDataSetChanged();

        List<PersonalStyle> personalStyleTemps = new ArrayList<>();
        for(PersonalStyle personalStyle: personalStyles){
            if(personalStyle.getSex() == isSexSuggest){
                personalStyleTemps.add(personalStyle);
            }
        }

        List<Body> bodyTemps = new ArrayList<>();
        for(Body body: bodies){
            if(body.getSex() == isSexSuggest){
                bodyTemps.add(body);
            }
        }

        getBody(bodyTemps);
        getPersonalStyle(personalStyleTemps);

        prevPositionStyle = -1;
        prevBody = -1;

        binding.txtSelectBody.setText("Vóc dáng");
        binding.txtSelectStyle.setText("Phong cách");

        setRequestCostume();
    }

    private void loading(boolean value){
        if(value){
            binding.rclCostume.setVisibility(View.GONE);
            binding.progressBarRcl.setVisibility(View.VISIBLE);
        }else{
            binding.rclCostume.setVisibility(View.VISIBLE);
            binding.progressBarRcl.setVisibility(View.GONE);
        }
    }

    private void setCostume(){
        costumeAdapter = new CostumeAdapter(costumeSuggest, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClickFavourite(CostumeHome costume, int position) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclCostume.setLayoutManager(gridLayoutManager);
        binding.rclCostume.setAdapter(costumeAdapter);
    }

    private void setListeners(){
        binding.layoutStyle.setOnClickListener(v -> {
//            Intent intent = new Intent(getApplication(), StyleActivity.class);
//            intent.putExtra("personalStyles", (Serializable) personalStyles);
//            startActivity(intent);
            changeLayout(layoutInfoStyle);
        });

        binding.layoutSex.setOnClickListener(v -> {
            changeSex();
        });

        binding.layoutBody.setOnClickListener(v -> {
            changeLayout(layoutInfoBody);
        });

        binding.layoutPurpose.setOnClickListener(v -> {
            changeLayout(layoutInfoPurpose );
        });

        binding.layoutStyleSex.setOnClickListener(v -> {
            changeSex();
        });

        binding.layoutBodySex.setOnClickListener(v -> {
            changeSex();
        });

        binding.btnExitStyle.setOnClickListener(v -> {
            changeLayout(layoutMain);
        });

        binding.btnExitBody.setOnClickListener(v -> {
            changeLayout(layoutMain);
        });

        binding.btnExitPurpose.setOnClickListener(v -> {
            changeLayout(layoutMain);
        });

        binding.btnCheck.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), TestActivity.class);
            startActivity(intent);
        });

        binding.btnBackBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void setRequestCostume(){
        String bodyId = "";
        String purposeId = "";
        String personalStyleId = "";

        List<PersonalStyle> personalStyleTemps = new ArrayList<>();
        for(PersonalStyle personalStyle: personalStyles){
            if(personalStyle.getSex() == isSexSuggest){
                personalStyleTemps.add(personalStyle);
            }
        }

        List<Body> bodyTemps = new ArrayList<>();
        for(Body body: bodies){
            if(body.getSex() == isSexSuggest){
                bodyTemps.add(body);
            }
        }

        if(prevBody != -1)
            bodyId = bodyTemps.get(prevBody).getId();
        if(prevPurpose != -1)
            purposeId = purposes.get(prevPurpose).getId();
        if(prevPositionStyle != -1)
            personalStyleId = personalStyleTemps.get(prevPositionStyle).getId();

        getCostumeWithSuggests(new Request_Suggestion(bodyId,
                personalStyleId,
                purposeId,
                isSexSuggest));
    }

    private void getSuggest(){
        Service service = getRetrofit().create(Service.class);
        Call<Message_Suggestion> suggestionCall = service.GetSuggestion("bearer "+shared_preferences.getToken());
        suggestionCall.enqueue(new Callback<Message_Suggestion>() {
            @Override
            public void onResponse(Call<Message_Suggestion> call, Response<Message_Suggestion> response) {
                if(response.body().getStatus() == 1){
                    personalStyles = response.body().getSuggestion().getPersonalStyles();
                    bodies = response.body().getSuggestion().getBodies();
                    purposes = response.body().getSuggestion().getPurposes();

                    List<PersonalStyle> personalStyleTemps = new ArrayList<>();
                    for(PersonalStyle personalStyle: personalStyles){
                        if(personalStyle.getSex() == isSexSuggest){
                            personalStyleTemps.add(personalStyle);
                        }
                    }

                    List<Body> bodyTemps = new ArrayList<>();
                    for(Body body: bodies){
                        if(body.getSex() == isSexSuggest){
                            bodyTemps.add(body);
                        }
                    }

                    setRequestCostume();
                    getPersonalStyle(personalStyleTemps);
                    getBody(bodyTemps);
                    getPurpose();
                }

                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message_Suggestion> call, Throwable t) {
                Toast.makeText(getApplication(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPersonalStyle(List<PersonalStyle> personals){
        personalStyleAdapter = new PersonalStyleAdapter(personals, this, new PersonalStyleAdapter.SuggestListeners() {
            @Override
            public void onClick(PersonalStyle personalStyle, int position) {
                if(prevPositionStyle != position) {
                    if (prevPositionStyle != -1) {
                        personalStyleAdapter.notifyItemChanged(prevPositionStyle);
                    }
                    prevPositionStyle = position;

                    binding.txtSelectStyle.setText("Phong cách: "+personalStyle.getName());

                    setRequestCostume();
                }
                changeLayout(layoutMain);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclStyle.setLayoutManager(gridLayoutManager);
        binding.rclStyle.setAdapter(personalStyleAdapter);
    }

    private void getBody(List<Body> bodyList){
        bodyAdapter = new BodyAdapter(bodyList, this, new BodyAdapter.SuggestListeners() {
            @Override
            public void onClick(Body body, int position) {
                if(prevBody != position) {
                    if (prevBody != -1) {
                        bodyAdapter.notifyItemChanged(prevBody);
                    }
                    prevBody = position;

                    binding.txtSelectBody.setText("Vóc dáng: "+body.getName());

                    setRequestCostume();
                }
                changeLayout(layoutMain);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclBody.setLayoutManager(gridLayoutManager);
        binding.rclBody.setAdapter(bodyAdapter);
    }

    private void getPurpose(){
        purposeAdapter = new PurposeAdapter(purposes, this, new PurposeAdapter.SuggestListeners() {
            @Override
            public void onClick(Purpose purpose, int position) {
                if(prevPurpose != position) {
                    if (prevPurpose != -1) {
                        purposeAdapter.notifyItemChanged(prevPurpose);
                    }
                    prevPurpose = position;

                    binding.txtSelectPurpose.setText("Mục đích: "+purpose.getName());

                    setRequestCostume();
                }
                changeLayout(layoutMain);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclPurpose.setLayoutManager(gridLayoutManager);
        binding.rclPurpose.setAdapter(purposeAdapter);
    }

    private void getCostumeWithSuggests(Request_Suggestion request){
        loading(true);
        Service service = getRetrofit().create(Service.class);
        Call<Message_CostumeWithStyle> costumeWithStyleCall = service.CostumeWithSugs("bearer "+shared_preferences.getToken(), request);
        costumeWithStyleCall.enqueue(new Callback<Message_CostumeWithStyle>() {
            @Override
            public void onResponse(Call<Message_CostumeWithStyle> call, Response<Message_CostumeWithStyle> response) {
                if(response.body().getStatus() == 1){
                    costumeSuggest = response.body().getCostumes();

                    setCostume();
                }

                Toast.makeText(getApplicationContext(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
                loading(false);
            }

            @Override
            public void onFailure(Call<Message_CostumeWithStyle> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                loading(false);
            }
        });
    }
}