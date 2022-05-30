package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Backup.Constant.codeMinus;
import static com.personal_game.datn.Backup.Constant.codePlus;
import static com.personal_game.datn.Backup.Constant.codeSelect;
import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;
import static com.personal_game.datn.ultilities.ConvertMoney.longConvertMoney;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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
import com.personal_game.datn.ultilities.RangeTime;
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
    private int sumSelect = 0;
    private long sumPrice = 0;
    private int countSelectPromotionCostume = 0;

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

    private void sumTotal(){
        sumSelect = 0;
        sumPrice = 0;
        countSelectPromotionCostume = 0;
        for(int i = 0; i < costumeCarts.size(); i++){
            if(costumeCarts.get(i).getState()){
                sumSelect ++;
                if(costumeCarts.get(i).getCostume().getPromotion() != null) {
                    if (RangeTime.checkRangeEvent(costumeCarts.get(i).getCostume().getPromotion().getStartTime(), costumeCarts.get(i).getCostume().getPromotion().getEndTime())) {
                        countSelectPromotionCostume ++;
                        sumPrice += costumeCarts.get(i).getQuantity() * (costumeCarts.get(i).getCostume().getPrice() * (100 - costumeCarts.get(i).getCostume().getPromotion().getValue()) / 100);
                        continue;
                    }
                }

                sumPrice += costumeCarts.get(i).getQuantity()*costumeCarts.get(i).getCostume().getPrice();
            }
        }
        Log.i("totalSelect", sumSelect+"");

        setTextTotal();
    }

    private void setTextTotal(){
        if(countSelectPromotionCostume > 0){
            binding.layoutPromotionTotal.setVisibility(View.VISIBLE);
            binding.txtTotal.setVisibility(View.GONE);

            binding.txtPromotionTotal.setText(longConvertMoney(sumPrice));

            long cost = 0;
            for(Costume_Cart item: costumeCarts){
                cost += item.getQuantity()*item.getCostume().getPrice();
            }

            binding.txtPromotionDiscount.setText(Html.fromHtml("<strike>"+longConvertMoney(cost)+"</strike>"));
        }else{
            binding.layoutPromotionTotal.setVisibility(View.GONE);
            binding.txtTotal.setVisibility(View.VISIBLE);

            binding.txtTotal.setText(longConvertMoney(sumPrice));
        }
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
            if(sumSelect != costumeCarts.size()){
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
            public void onClick(Costume_Cart costume, int quantity, boolean state, int position, int discount, boolean isDiscount, int code) {
                Cart updateCart = new Cart(costume.getCostume().getId(),
                        quantity,
                        state);

                updateCart(updateCart, position, discount, isDiscount, code);
            }

            @Override
            public void onClickDel(String costumeId, int position) {
                deleteCart(costumeId, position);
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

                    sumTotal();

                    if(sumSelect == costumeCarts.size())
                        binding.btnAll.setImageResource(R.drawable.circle_check);
                    else
                        binding.btnAll.setImageResource(R.drawable.circle_none);
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

    private void updateCart(Cart updateCart, int position, int discount, boolean isDiscount, int code){
        Service service = getRetrofit().create(Service.class);
        Call<Message> call = service.UpdateCart("bearer "+shared_preferences.getToken(), updateCart);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    costumeCarts.get(position).setQuantity(updateCart.getQuantity());
                    costumeCarts.get(position).setState(updateCart.getState());

                    costumeCartAdapter.notifyItemChanged(position);

                    switch (code){
                        case codeSelect:{
                            if(updateCart.getState()) {
                                if(isDiscount)
                                    countSelectPromotionCostume ++;
                                sumPrice += discount*updateCart.getQuantity();
                                sumSelect++;
                            }else {
                                if (isDiscount)
                                    countSelectPromotionCostume --;
                                sumPrice -= discount*updateCart.getQuantity();
                                sumSelect --;
                            }
                        }break;
                        case codeMinus:{
                            if(updateCart.getState()) {
                                sumPrice -= discount;
                                sumSelect--;
                            }
                        }break;
                        case codePlus:{
                            if(updateCart.getState()) {
                                sumPrice += discount;
                                sumSelect++;
                            }
                        }
                    }
                    setTextTotal();
//                    for(int i = 0; i < costumeCarts.size(); i++){
//                        if(!costumeCarts.get(i).getState()){
//                            isAll = false;
//                        }else{
//
//                            total += (costumeCarts.get(i).getQuantity()*costumeCarts.get(i).getCostume().getPrice());
//                        }
//                    }

                    if(sumSelect == costumeCarts.size())
                        binding.btnAll.setImageResource(R.drawable.circle_check);
                    else
                        binding.btnAll.setImageResource(R.drawable.circle_none);

                    binding.txtTotal.setText(longConvertMoney(sumPrice));
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

                    sumTotal();

                    if(sumSelect == costumeCarts.size())
                        binding.btnAll.setImageResource(R.drawable.circle_check);
                    else
                        binding.btnAll.setImageResource(R.drawable.circle_none);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }

    private void deleteCart(String costumeId, int position){
        Service service = getRetrofit().create(Service.class);
        Call<Message> call = service.DeleteCart("bearer "+shared_preferences.getToken(), costumeId);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    costumeCarts.remove(position);

                    costumeCartAdapter.notifyItemRemoved(position);

                    int cart = Integer.parseInt(shared_preferences.getQuantityCart());
                    shared_preferences.saveQuantityCart(cart-1);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }
}