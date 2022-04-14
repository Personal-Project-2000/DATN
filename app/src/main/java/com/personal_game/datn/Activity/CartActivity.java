package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Adapter.CostumeCartAdapter;
import com.personal_game.datn.Adapter.CostumeImgAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Cart;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.Costume_Cart;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Cart;
import com.personal_game.datn.Response.Message_Costume;
import com.personal_game.datn.databinding.ActivityCartBinding;
import com.personal_game.datn.databinding.ActivityCostumeBinding;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private CostumeCartAdapter costumeCartAdapter;

    private Shared_Preferences shared_preferences;
    private List<Costume_Cart> costumeCarts = new ArrayList<>();
    private boolean isAll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        shared_preferences = new Shared_Preferences(getApplicationContext());

        setListeners();
        getCart();
    }

    private void loading(boolean value){
        if(value){
            binding.layoutMain.setVisibility(View.GONE);
            binding.progressBarMain.setVisibility(View.VISIBLE);
        }else{
            binding.layoutMain.setVisibility(View.VISIBLE);
            binding.progressBarMain.setVisibility(View.GONE);
        }
    }

    private void setListeners(){
        binding.btnPayment.setOnClickListener(v -> {
            List<Costume_Cart> costumeBills = new ArrayList<>();

            for (Costume_Cart costume: costumeCarts) {
                if(costume.getState()){
                    costumeBills.add(costume);
                }
            }

            if(costumeBills.size() > 0){
                Intent intent = new Intent(getApplication(), PaymentActivity.class);
                intent.putExtra("costumeBills", (Serializable) costumeBills);
                startActivity(intent);
            }else{
                Toast.makeText(getApplication(), "Bạn cần trang phục để mua!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnAll.setOnClickListener(v -> {
            if(!isAll){
                updateAllCart();
            }else{
                binding.btnAll.setImageResource(R.drawable.circle_none);
            }
        });

        binding.btnBackBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void getCostume(){
        costumeCartAdapter = new CostumeCartAdapter(costumeCarts, this, new CostumeCartAdapter.CostumeCartListeners() {
            @Override
            public void onClick(Costume_Cart costume, int quantity, boolean state, int position) {
                Cart updateCart = new Cart(costume.getCostume().getId(),
                        quantity,
                        state);

                updateCart(updateCart, position);
            }
        }, 1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclCostume.setLayoutManager(gridLayoutManager);
        binding.rclCostume.setAdapter(costumeCartAdapter);
    }

    private void getCart(){
        loading(true);

        Service service = getRetrofit().create(Service.class);
        Call<Message_Cart> cartCall = service.GetCart("bearer "+shared_preferences.getToken());
        cartCall.enqueue(new Callback<Message_Cart>() {
            @Override
            public void onResponse(Call<Message_Cart> call, Response<Message_Cart> response) {
                if(response.body().getStatus() == 1){
                    costumeCarts = response.body().getCostumes();

                    getCostume();

                    int total = 0;
                    for(int i = 0; i < costumeCarts.size(); i++){
                        if(!costumeCarts.get(i).getState()){
                            isAll = false;
                        }else{
                            total += (costumeCarts.get(i).getQuantity()*costumeCarts.get(i).getCostume().getPrice());
                        }
                    }

                    if(isAll)
                        binding.btnAll.setImageResource(R.drawable.circle_check);
                    else
                        binding.btnAll.setImageResource(R.drawable.circle_none);

                    binding.txtTotal.setText(intConvertMoney(total));
                }

                loading(false);
            }

            @Override
            public void onFailure(Call<Message_Cart> call, Throwable t) {
                Toast.makeText(getApplication(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                loading(false);
            }
        });
    }

    private void updateCart(Cart updateCart, int position){
        Service service = getRetrofit().create(Service.class);
        Call<Message> call = service.UpdateCart("bearer "+shared_preferences.getToken(), updateCart);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    costumeCarts.get(position).setQuantity(updateCart.getQuantity());
                    costumeCarts.get(position).setState(updateCart.getState());

                    costumeCartAdapter.notifyItemChanged(position);

                    int total = 0;
                    isAll = true;
                    for(int i = 0; i < costumeCarts.size(); i++){
                        if(!costumeCarts.get(i).getState()){
                            isAll = false;
                        }else{
                            total += (costumeCarts.get(i).getQuantity()*costumeCarts.get(i).getCostume().getPrice());
                        }
                    }

                    if(isAll)
                        binding.btnAll.setImageResource(R.drawable.circle_check);
                    else
                        binding.btnAll.setImageResource(R.drawable.circle_none);

                    binding.txtTotal.setText(intConvertMoney(total));
                }else{
                    Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getApplication(), "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAllCart(){
        Service service = getRetrofit().create(Service.class);
        Call<Message> call = service.UpdateAllCart("bearer "+shared_preferences.getToken());
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    for (int i = 0; i < costumeCarts.size(); i++) {
                        costumeCarts.get(i).setState(true);
                    }
                    costumeCartAdapter.notifyDataSetChanged();

                    int total = 0;
                    isAll = true;
                    for(int i = 0; i < costumeCarts.size(); i++){
                        if(!costumeCarts.get(i).getState()){
                            isAll = false;
                        }else{
                            total += (costumeCarts.get(i).getQuantity()*costumeCarts.get(i).getCostume().getPrice());
                        }
                    }

                    if(isAll)
                        binding.btnAll.setImageResource(R.drawable.circle_check);
                    else
                        binding.btnAll.setImageResource(R.drawable.circle_none);

                    binding.txtTotal.setText(intConvertMoney(total));
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }
}