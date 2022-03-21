package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Backup.Constant.token_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeStyleAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Models.CostumeStyle;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Data;
import com.personal_game.datn.Response.Message_Home;
import com.personal_game.datn.Response.Message_Login;
import com.personal_game.datn.databinding.ActivityMainBinding;
import com.personal_game.datn.databinding.ActivitySignInBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private CostumeAdapter costumeAdapter;
    private CostumeStyleAdapter costumeStyleAdapter;

    private List<CostumeStyle> costumeStyles ;
    private List<CostumeHome> costumeHots ;
    private List<CostumeHome> costumeNews ;

    private TextView name;
    private ImageView img;
    private Data data;

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
        setInfo();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setListeners();
    }

    private void setInfo(){
        data = (Data) getIntent().getSerializableExtra("info");

        if(data.getImage() != null){
            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    img.setImageResource(R.drawable.logo);
                }
            });
            Picasso pic = builder.build();
            pic.load(data.getImage()).into(img);
        }

        name.setText(data.getName());

        setHome();
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
    }

    private void setHome(){
        Service service = getRetrofit().create(Service.class);
        Call<Message_Home> home = service.GetHome("bearer "+data.getToken());
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
                }

                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message_Home> call, Throwable t) {
                Intent intent = new Intent(getApplication(), SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setCostumeStyle(){
        costumeStyleAdapter = new CostumeStyleAdapter(costumeStyles, this, new CostumeStyleAdapter.CostumeStyleListeners() {
            @Override
            public void onClick(String costumeStyle) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        activityMainBinding.rclCostumeStyle.setLayoutManager(gridLayoutManager);
        activityMainBinding.rclCostumeStyle.setAdapter(costumeStyleAdapter);
    }

    private void setPromotion(){
        costumeAdapter = new CostumeAdapter(costumeNews, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        activityMainBinding.rclPromotion.setLayoutManager(gridLayoutManager);
        activityMainBinding.rclPromotion.setAdapter(costumeAdapter);
    }

    private void setCostume(){
        costumeAdapter = new CostumeAdapter(costumeHots, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        activityMainBinding.rclCostume.setLayoutManager(gridLayoutManager);
        activityMainBinding.rclCostume.setAdapter(costumeAdapter);
    }
}