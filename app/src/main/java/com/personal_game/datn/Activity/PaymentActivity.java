package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeCartAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Address;
import com.personal_game.datn.Models.Cart;
import com.personal_game.datn.R;
import com.personal_game.datn.Request.Request_Bill;
import com.personal_game.datn.Request.Request_CostumeBill;
import com.personal_game.datn.Response.Costume_Cart;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.databinding.ActivityInfoBinding;
import com.personal_game.datn.databinding.ActivityPaymentBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private ActivityPaymentBinding binding;
    private CostumeCartAdapter costumeCartAdapter;

    private Shared_Preferences shared_preferences;
    private List<Costume_Cart> costumes;
    private Address addressDefault;
    private int paymentMethods = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        costumes = (List<Costume_Cart>)getIntent().getSerializableExtra("costumeBills");
        shared_preferences = new Shared_Preferences(getApplicationContext());

        int total = 0;
        for(int i = 0; i < costumes.size(); i ++){
             total += (costumes.get(i).getQuantity()*costumes.get(i).getCostume().getPrice());
        }

        binding.txtTotal.setText(intConvertMoney(total));

        addressDefault = shared_preferences.getAddress();

        binding.txtName.setText(addressDefault.getName());
        binding.txtPhone.setText(addressDefault.getPhone());
        binding.txtAddress.setText(addressDefault.getStreet());
        binding.txtAddress1.setText(addressDefault.getWard() + " - "+addressDefault.getDistrict()+" - "+addressDefault.getCity());

        setCostumeCart();
        setListeners();
    }

    private void loading(boolean value){
        if(value){
            binding.btnCreateBill.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.btnCreateBill.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void changePayment(int code){
        if (code == 1) {
            binding.btnSelect1.setImageResource(R.drawable.circle_check);
            binding.btnSelect2.setImageResource(R.drawable.circle_none);
        }else{
            binding.btnSelect1.setImageResource(R.drawable.circle_none);
            binding.btnSelect2.setImageResource(R.drawable.circle_check);
        }
    }

    private void setCostumeCart(){
        costumeCartAdapter = new CostumeCartAdapter(costumes, this, new CostumeCartAdapter.CostumeCartListeners() {
            @Override
            public void onClick(Costume_Cart costume, int quantity, boolean state, int position) {
                Cart updateCart = new Cart(costume.getCostume().getId(),
                        quantity,
                        state);

                updateCart(updateCart, position);
            }
        }, 2);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclCostumeCart.setLayoutManager(gridLayoutManager);
        binding.rclCostumeCart.setAdapter(costumeCartAdapter);
    }

    private void setListeners(){
        binding.layoutAddress.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), AddressActivity.class);
            startActivity(intent);
        });

        binding.layoutPayment1.setOnClickListener(v -> {
            paymentMethods = 1;

            changePayment(1);
        });

        binding.layoutPayment2.setOnClickListener(v -> {
            paymentMethods = 2;

            changePayment(2);
        });

        binding.btnCreateBill.setOnClickListener(v -> {
            List<Request_CostumeBill> costumeBills = new ArrayList<>();

            for (Costume_Cart costume: costumes) {
                Request_CostumeBill costumeBill = new Request_CostumeBill(costume.getCostume().getId(), costume.getQuantity());

                costumeBills.add(costumeBill);
            }

            Request_Bill bill = new Request_Bill(addressDefault.getId(), costumeBills);

            if(paymentMethods == 1){
                addBill(bill);
            }else{

            }
        });
    }

    private void paymentZaloPay(){

    }

    private void updateCart(Cart updateCart, int position){
        Service service = getRetrofit().create(Service.class);
        Call<Message> call = service.UpdateCart("bearer "+shared_preferences.getToken(), updateCart);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    costumes.get(position).setQuantity(updateCart.getQuantity());
                    costumes.get(position).setState(updateCart.getState());

                    costumeCartAdapter.notifyItemChanged(position);

                    int total = 0;
                    for(int i = 0; i < costumes.size(); i++){
                        if(costumes.get(i).getState()){
                            total += (costumes.get(i).getQuantity()*costumes.get(i).getCostume().getPrice());
                        }
                    }

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

    private void addBill(Request_Bill bill){
        loading(true);
        Service service = getRetrofit().create(Service.class);
        Call<Message> call = service.AddBill("bearer "+shared_preferences.getToken(), bill);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                }
                loading(false);
                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                loading(false);
                Toast.makeText(getApplication(), "Thêm hóa đơn thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}