package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Backup.Constant.token_client;
import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;
import static com.personal_game.datn.ultilities.RangeTime.getBetweenDayToCurrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.personal_game.datn.Adapter.BodyAdapter;
import com.personal_game.datn.Adapter.BodyCostumeAdapter;
import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeImgAdapter;
import com.personal_game.datn.Adapter.CostumeStyleAdapter;
import com.personal_game.datn.Adapter.StyleAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Body;
import com.personal_game.datn.Models.PersonalStyle;
import com.personal_game.datn.Models.Picture;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Costume;
import com.personal_game.datn.Response.Message_Suggestion;
import com.personal_game.datn.databinding.ActivityCostumeBinding;
import com.personal_game.datn.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CostumeActivity extends AppCompatActivity {
    private ActivityCostumeBinding binding;
    private CostumeImgAdapter costumeImgAdapter;
    private CostumeAdapter suitableOutfit;
    private CostumeAdapter costumeAdapter;
    private StyleAdapter styleAdapter;
    private BodyCostumeAdapter bodyAdapter;

    private Shared_Preferences shared_preferences;
    private String costumeId = "";
    private String costumeName = "";
    private List<Picture> pictures;
    private List<PersonalStyle> personalStyles;
    private List<Body> bodyList;
    private List<CostumeHome> relatedCostumes = new ArrayList<>();
    private List<CostumeHome> suitableOutfitCostumes = new ArrayList<>();
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCostumeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        shared_preferences = new Shared_Preferences(getApplicationContext());
        String temp = getIntent().getStringExtra("costumeId");

        if(temp == null){
            Uri uri = getIntent().getData();

            if(uri != null){
                String path = uri.toString();
                String[] parameter = path.split("/");
                costumeId = parameter[5];
            }
        }else{
            costumeId = temp;
        }

        getCostumeInfo();
        setListeners();
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

    private void setListeners(){
        binding.layoutMain.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    binding.layoutHeader.setVisibility(View.VISIBLE);
                }

                if (scrollY < 5) {
                    binding.layoutHeader.setVisibility(View.GONE);
                }

                if(scrollY >= 850){
                    binding.txtInfoCostume.setTextColor(getResources().getColor(R.color.secondary_text, null));
                    binding.txtIntroCostume.setTextColor(getResources().getColor(R.color.black, null));
                    binding.layoutIntroCostume.setVisibility(View.VISIBLE);
                    binding.layoutInfoCostume.setVisibility(View.GONE);
                }else{
                    binding.txtInfoCostume.setTextColor(getResources().getColor(R.color.black, null));
                    binding.txtIntroCostume.setTextColor(getResources().getColor(R.color.secondary_text, null));
                    binding.layoutIntroCostume.setVisibility(View.GONE);
                    binding.layoutInfoCostume.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.txtInfoCostume.setOnClickListener(v -> {
            binding.layoutMain.setScrollY(0);
        });

        binding.txtIntroCostume.setOnClickListener(v -> {
            binding.layoutMain.setScrollY(850);
        });

        // get current position of recyclerView
        binding.rclImg.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.rclImg.getLayoutManager();
                        binding.txtPosition.setText((linearLayoutManager.findFirstVisibleItemPosition()+1)+"/"+pictures.size());
                        break;
                    default:
                        break;
                }
            }
        });

        binding.layoutDes1.setOnClickListener(v -> {
            binding.layoutDescription.setVisibility(View.VISIBLE);
        });

        binding.btnClose.setOnClickListener(v -> {
            binding.layoutDescription.setVisibility(View.GONE);
        });

        binding.layoutD.setOnClickListener(v -> {
            binding.layoutDescription.setVisibility(View.GONE);
        });

        binding.layoutPolicy.setOnClickListener(v -> {
            binding.layoutPolicy1.setVisibility(View.VISIBLE);
        });

        binding.btnClosePolicy.setOnClickListener(v -> {
            binding.layoutPolicy1.setVisibility(View.GONE);
        });

        binding.layoutSize.setOnClickListener(v -> {
            binding.layoutSize1.setVisibility(View.VISIBLE);
        });

        binding.btnCloseSize.setOnClickListener(v -> {
            binding.layoutSize1.setVisibility(View.GONE);
        });

        binding.imgFavourite.setOnClickListener(v -> {
            setFavourite();
        });

        binding.imgShare.setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= costumeName+"\n\n";
                shareMessage = shareMessage + "https://smartfashion.covid21tsp.space/Share/Index/" + costumeId;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        });

        binding.btnAddCart.setOnClickListener(v -> {
            addCart();
        });

        binding.imgFavouriteTotal.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), FavouriteActivity.class);
            startActivity(intent);
        });

        binding.imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), CartActivity.class);
            startActivity(intent);
        });

        binding.btnBackBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void setFavourite(){
        Service service = getRetrofit().create(Service.class);
        Call<Message> favourite = service.AddFavourite("bearer "+shared_preferences.getToken(), costumeId);
        favourite.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    isFavorite = !isFavorite;
                    int quantity = Integer.parseInt(binding.txtNumberFavourite.getText()+"");

                    if(isFavorite) {
                        binding.imgFavourite.setImageResource(R.drawable.ic_baseline_favorite_24);

                        quantity ++;
                    }
                    else {
                        binding.imgFavourite.setImageResource(R.drawable.ic_favourite_none);

                        quantity --;
                    }

                    binding.txtNumberFavourite.setText(quantity +"");
                    shared_preferences.saveQuantityCart(quantity);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }

    public void addCart(){
        Service service = getRetrofit().create(Service.class);
        Call<Message> cart = service.AddCart("bearer "+shared_preferences.getToken(), costumeId);
        cart.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    //0 có nghĩa là sản phẩm đã sẳn trong giỏ hàng
                    if(response.body().getId().equals("0")) {
                        int quantity = Integer.parseInt(binding.txtNumber.getText() + "");

                        quantity++;
                        binding.txtNumber.setText(quantity + "");
                        shared_preferences.saveQuantityCart(quantity);
                    }
                }

                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getApplication(), "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getImg(){
        costumeImgAdapter = new CostumeImgAdapter(pictures, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclImg.setLayoutManager(gridLayoutManager);
        binding.rclImg.setAdapter(costumeImgAdapter);
    }

    private void getSuitableOutfit(){
        suitableOutfit = new CostumeAdapter(suitableOutfitCostumes, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClickFavourite(CostumeHome costume, int position) {
                binding.txtNumberFavourite.setText(shared_preferences.getQuantityFavorite());

                for(int i = 0; i < relatedCostumes.size(); i++){
                    if(relatedCostumes.get(i).getCostume().getId().equals(costume.getCostume().getId())){
                        relatedCostumes.get(i).setFavourite(costume.getFavourite());

                        costumeAdapter.notifyItemChanged(i);
                        i = suitableOutfitCostumes.size()+1;
                    }
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclSuitableOutfit.setLayoutManager(gridLayoutManager);
        binding.rclSuitableOutfit.setAdapter(suitableOutfit);
    }

    private void getRelatedCostumes(){
        costumeAdapter = new CostumeAdapter(relatedCostumes, this, new CostumeAdapter.CostumeListeners() {
            @Override
            public void onClickFavourite(CostumeHome costume, int position) {
                binding.txtNumberFavourite.setText(shared_preferences.getQuantityFavorite());

                for(int i = 0; i < suitableOutfitCostumes.size(); i++){
                    if(suitableOutfitCostumes.get(i).getCostume().getId().equals(costume.getCostume().getId())){
                        suitableOutfitCostumes.get(i).setFavourite(costume.getFavourite());

                        suitableOutfit.notifyItemChanged(i);
                        i = suitableOutfitCostumes.size()+1;
                    }
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclRelatedCostumes.setLayoutManager(gridLayoutManager);
        binding.rclRelatedCostumes.setAdapter(costumeAdapter);
    }

    private void getStyle(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        styleAdapter = new StyleAdapter(personalStyles, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclStyles.setLayoutManager(gridLayoutManager);
        binding.rclStyles.setAdapter(styleAdapter);
    }

    private void getBody(){
       bodyAdapter = new BodyCostumeAdapter(bodyList, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclBodys.setLayoutManager(gridLayoutManager);
        binding.rclBodys.setAdapter(bodyAdapter);
    }

    private void getCostumeInfo(){
        loading(true);

        String token = shared_preferences.getToken();
        if(getBetweenDayToCurrent(shared_preferences.getTime()) > 0){
            token = token_client;
        }

        Service service = getRetrofit().create(Service.class);
        Call<Message_Costume> costumeCall = service.GetCostume("bearer "+token, costumeId);
        costumeCall.enqueue(new Callback<Message_Costume>() {
            @Override
            public void onResponse(Call<Message_Costume> call, Response<Message_Costume> response) {
                if(response.body().getStatus() == 1){
                    pictures = response.body().getCostume().getCostume().getPictures();
                    personalStyles = response.body().getCostume().getPersonalStyles();
                    bodyList = response.body().getCostume().getBodies();
                    relatedCostumes = response.body().getCostume().getRelatedCostumes();
                    suitableOutfitCostumes = response.body().getCostume().getSuitableOutfitCostumes();
                    isFavorite = response.body().getCostume().getFavourite();
                    costumeName = response.body().getCostume().getCostume().getName();

                    getImg();
                    getStyle();
                    getBody();
                    getRelatedCostumes();
                    getSuitableOutfit();

                    Picasso.Builder builder = new Picasso.Builder(getApplication());
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgMain.setImageResource(R.drawable.logo);
                        }
                    });
                    Picasso pic = builder.build();
                    pic.load(pictures.get(0).getLink()).into(binding.imgMain);

                    binding.txtName.setText(costumeName);
                    binding.txtPrice.setText(intConvertMoney(response.body().getCostume().getCostume().getPrice()));
                    binding.txtNumber.setText(response.body().getCostume().getQuantityCart()+"");
                    binding.txtNumberFavourite.setText(response.body().getCostume().getQuantityFavourite()+"");
                    binding.txtPosition.setText("1/"+pictures.size());

                    String descriptionCostume = response.body().getCostume().getCostume().getDescription();
                    String description = " ";
                    String descriptionList[] = descriptionCostume.split(",");
                    for (int i = 0; i< descriptionList.length; i++){
                        description += descriptionList[i] +"\n";
                    }
                    binding.txtDescription.setText(description);

                    if(isFavorite)
                        binding.imgFavourite.setImageResource(R.drawable.ic_baseline_favorite_24);
                    else
                        binding.imgFavourite.setImageResource(R.drawable.ic_favourite_none);
                }
                loading(false);
            }

            @Override
            public void onFailure(Call<Message_Costume> call, Throwable t) {
                Toast.makeText(getApplication(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                loading(false);
            }
        });
    }
}