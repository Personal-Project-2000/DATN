package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Backup.Constant.billCancel;
import static com.personal_game.datn.Backup.Constant.token_client;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeStyleAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Address;
import com.personal_game.datn.Models.CostumeStyle;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Data;
import com.personal_game.datn.Response.Message_CostumeWithStyle;
import com.personal_game.datn.Response.Message_Home;
import com.personal_game.datn.Response.Message_Login;
import com.personal_game.datn.databinding.ActivityMainBinding;
import com.personal_game.datn.databinding.ActivitySignInBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private CostumeAdapter costumeHotAdapter;
    private CostumeAdapter costumeNewAdapter;
    private CostumeStyleAdapter costumeStyleAdapter;

    private Shared_Preferences shared_preferences;
    private List<CostumeStyle> costumeStyles ;
    private List<CostumeHome> costumeHots ;
    private List<CostumeHome> costumeNews ;

    private Timer timer = new Timer();
    private final long DELAY = 300; // in ms

    private TextView name;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        img = activityMainBinding.navigationView.getHeaderView(0).findViewById(R.id.imgMain);
        name = activityMainBinding.navigationView.getHeaderView(0).findViewById(R.id.txtName);

        init();
    }

    private void init(){
        shared_preferences = new Shared_Preferences(getApplicationContext());

        setInfo();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setListeners();
    }

    private void loading(boolean value){
        if(value){
            activityMainBinding.layoutMain.setVisibility(View.GONE);
            activityMainBinding.progressBarMain.setVisibility(View.VISIBLE);
        }else{
            activityMainBinding.layoutMain.setVisibility(View.VISIBLE);
            activityMainBinding.progressBarMain.setVisibility(View.GONE);
        }
    }

    private void setInfo(){
        if(!shared_preferences.getImg().equals("")){
            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    img.setImageResource(R.drawable.logo);
                }
            });
            Picasso pic = builder.build();
            pic.load(shared_preferences.getImg()).into(img);
        }

        name.setText(shared_preferences.getName());

        getHome();
    }

    private void setListeners(){
        activityMainBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_info: {
                        Intent intent = new Intent(getApplication(), InfoActivity.class);
                        startActivityForResult(intent, 1);
                        break;
                    }
                    case R.id.nav_favourite: {
                        Intent intent = new Intent(getApplication(), FavouriteActivity.class);
                        startActivityForResult(intent, 2);
                        break;
                    }
                    case R.id.nav_notification: {
                        Intent intent = new Intent(getApplication(), NotificationActivity.class);
                        startActivityForResult(intent, 3);
                        break;
                    }
                    case R.id.nav_suggest: {
                        Intent intent = new Intent(getApplication(), SuggestionActivity.class);
                        startActivityForResult(intent, 3);
                        break;
                    }
                    case R.id.nav_signout: {
                        shared_preferences.saveToken1("");
                        shared_preferences.saveAddress(new Address());
                        shared_preferences.saveName("");
                        shared_preferences.saveImg("");

                        Intent intent = new Intent(getApplication(), SignInActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

        activityMainBinding.btnMenu.setOnClickListener(v -> {
            DrawerLayout d =  findViewById(R.id.drawerLayout);
            d.openDrawer(GravityCompat.START);
        });

        activityMainBinding.imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), CartActivity.class);
            startActivity(intent);
        });

        activityMainBinding.imageViewMic.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "hãy nói gì đi");
            try {
                activityResultLauncher.launch(intent);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Error",Toast.LENGTH_SHORT).show();
            }
        });

        activityMainBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getCostumeSearch(s+"");
                        }

                    }, DELAY);
                }else{
                    activityMainBinding.layoutMain1.setVisibility(View.VISIBLE);
                    activityMainBinding.rclCostumeSearch.setVisibility(View.GONE);
                }
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                if (result.getData() != null) {
                    ArrayList<String> data = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    activityMainBinding.inputSearch.setText(Objects.requireNonNull(data).get(0));
                } else {
                    Toast.makeText(getApplication(),"Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

    private void getHome(){
        loading(true);
        Service service = getRetrofit().create(Service.class);
        Call<Message_Home> home = service.GetHome("bearer "+shared_preferences.getToken());
        home.enqueue(new Callback<Message_Home>() {
            @Override
            public void onResponse(Call<Message_Home> call, Response<Message_Home> response) {
                if(response.body().getStatus() == 1){
                    activityMainBinding.imgNumber.setText(response.body().getHome().getQuantityCart()+"");

                    costumeStyles = response.body().getHome().getCostumeStyles();
                    costumeHots = response.body().getHome().getCostumeHots();
                    costumeNews = response.body().getHome().getCostumeNews();

                    setCostumeStyle();
                    setCostume();
                    setPromotion();

                    shared_preferences.saveQuantityFavorite(response.body().getHome().getQuantityFavourite());
                    shared_preferences.saveQuantityCart(response.body().getHome().getQuantityCart());
                }

                loading(false);
            }

            @Override
            public void onFailure(Call<Message_Home> call, Throwable t) {
                loading(false);
                Intent intent = new Intent(getApplication(), SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setCostumeStyle(){
        costumeStyleAdapter = new CostumeStyleAdapter(costumeStyles, this, new CostumeStyleAdapter.CostumeStyleListeners() {
            @Override
            public void onClick(String styleId) {
                Intent intent = new Intent(MainActivity.this, StyleActivity.class);
                intent.putExtra("styleId", styleId);
                startActivity(intent);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        activityMainBinding.rclCostumeStyle.setLayoutManager(gridLayoutManager);
        activityMainBinding.rclCostumeStyle.setAdapter(costumeStyleAdapter);
    }

    private void setPromotion(){
        costumeNewAdapter = new CostumeAdapter(costumeNews, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClickFavourite(CostumeHome costume, int position) {
                for(int i = 0; i < costumeHots.size(); i++){
                    if(costumeHots.get(i).getCostume().getId().equals(costume.getCostume().getId())){
                        costumeHots.get(i).setFavourite(costume.getFavourite());

                        costumeHotAdapter.notifyItemChanged(i);
                        i = costumeHots.size()+1;
                    }
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        activityMainBinding.rclPromotion.setLayoutManager(gridLayoutManager);
        activityMainBinding.rclPromotion.setAdapter(costumeNewAdapter);
    }

    private void setCostume(){
        costumeHotAdapter = new CostumeAdapter(costumeHots, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClickFavourite(CostumeHome costume, int position) {
                for(int i = 0; i < costumeNews.size(); i++){
                    if(costumeNews.get(i).getCostume().getId().equals(costume.getCostume().getId())){
                        costumeNews.get(i).setFavourite(costume.getFavourite());

                        costumeNewAdapter.notifyItemChanged(i);
                        i = costumeNews.size()+1;
                    }
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        activityMainBinding.rclCostume.setLayoutManager(gridLayoutManager);
        activityMainBinding.rclCostume.setAdapter(costumeHotAdapter);
    }

    private void getCostumeSearch(String input){
        Service service = getRetrofit().create(Service.class);
        Call<Message_CostumeWithStyle> call = service.CostumeSearch("bearer "+shared_preferences.getToken(), input);
        call.enqueue(new Callback<Message_CostumeWithStyle>() {
            @Override
            public void onResponse(Call<Message_CostumeWithStyle> call, Response<Message_CostumeWithStyle> response) {
                if(response.body().getStatus() == 1){
                    activityMainBinding.layoutMain1.setVisibility(View.GONE);
                    activityMainBinding.rclCostumeSearch.setVisibility(View.VISIBLE);
                    CostumeAdapter costumeAdapter = new CostumeAdapter(response.body().getCostumes(), getApplication(), new CostumeAdapter.CostumeListeners() {
                        @Override
                        public void onClickFavourite(CostumeHome costume, int position) {

                        }
                    });

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

                    activityMainBinding.rclCostumeSearch.setLayoutManager(gridLayoutManager);
                    activityMainBinding.rclCostumeSearch.setAdapter(costumeAdapter);
                }
            }

            @Override
            public void onFailure(Call<Message_CostumeWithStyle> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!shared_preferences.getImg().equals("")){
            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    img.setImageResource(R.drawable.logo);
                }
            });
            Picasso pic = builder.build();
            pic.load(shared_preferences.getImg()).into(img);
        }

        name.setText(shared_preferences.getName());

        activityMainBinding.imgNumber.setText(shared_preferences.getQuantityCart());
    }
}