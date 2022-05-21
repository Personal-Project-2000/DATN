package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Api.RetrofitService.getRetrofitLocation;
import static com.personal_game.datn.Backup.Constant.codeMinus;
import static com.personal_game.datn.Backup.Constant.codePlus;
import static com.personal_game.datn.Backup.Constant.codeSelect;
import static com.personal_game.datn.Backup.Constant.paymentDefault;
import static com.personal_game.datn.Backup.Constant.paymentZalo;
import static com.personal_game.datn.Backup.Constant.shop_district;
import static com.personal_game.datn.Backup.Constant.shop_id;
import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;
import static com.personal_game.datn.ultilities.ConvertMoney.longConvertMoney;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeCartAdapter;
import com.personal_game.datn.Adapter.CostumePaymentAdapter;
import com.personal_game.datn.Adapter.PromotionAdapter;
import com.personal_game.datn.Api.ModelFee.MessageFee;
import com.personal_game.datn.Api.ModelFee.MessageService;
import com.personal_game.datn.Api.ModelFee.RequestFee;
import com.personal_game.datn.Api.ModelFee.RequestService;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Api.ServiceApi.ServiceLocation1;
import com.personal_game.datn.Api.ServiceApi.ServiceService;
import com.personal_game.datn.Backup.Constant;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Helper.AppInfo;
import com.personal_game.datn.Helper.CreateOrder;
import com.personal_game.datn.Models.Address;
import com.personal_game.datn.Models.Cart;
import com.personal_game.datn.Models.Promotion;
import com.personal_game.datn.R;
import com.personal_game.datn.Request.Request_Bill;
import com.personal_game.datn.Request.Request_CostumeBill;
import com.personal_game.datn.Response.BeforeCreateBill;
import com.personal_game.datn.Response.Costume_Cart;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.databinding.ActivityInfoBinding;
import com.personal_game.datn.databinding.ActivityPaymentBinding;
import com.personal_game.datn.ultilities.ConvertMoney;
import com.personal_game.datn.ultilities.RangeTime;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import vn.zalopay.sdk.Environment;
//import vn.zalopay.sdk.ZaloPayError;
//import vn.zalopay.sdk.ZaloPaySDK;
//import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {
    private ActivityPaymentBinding binding;
    private CostumePaymentAdapter costumeCartAdapter;
    private PromotionAdapter promotionAdapter;

    private Shared_Preferences shared_preferences;
    private List<Costume_Cart> costumes;
    private List<Promotion> promotions;
    private Address addressDefault;
    private int paymentMethods = paymentDefault;
    private int sumSelect = 0;
    private long sumPrice = 0;
    //tiền sau khi khuyến mãi
    private long sumPricePromotion = 0;
    private int prePromotion = -1;
    //Kiểm tra trong các sản phẩm có sản phẩm nào khuyến mãi
    private boolean isCheckPromotion = false;
    private int fee = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        costumes = (List<Costume_Cart>)getIntent().getSerializableExtra("costumeBills");
        shared_preferences = new Shared_Preferences(getApplicationContext());

        binding.txtQuantityProduct.setText(costumes.size()+" sản phẩm");

        getPromotion();

        sumTotal();

        addressDefault = shared_preferences.getAddress();

        if(addressDefault != null) {
            binding.txtName.setText(addressDefault.getName());
            binding.txtPhone.setText(addressDefault.getPhone());
            binding.txtAddress.setText(addressDefault.getStreet());
            binding.txtAddress1.setText(addressDefault.getWard() + " - " + addressDefault.getDistrict() + " - " + addressDefault.getCity());

            if(sumPrice <= 699000)
                getService(Integer.parseInt(sumPrice+""));
            else
                binding.txtTransportMoney.setText("miễn phí");
        }
        else{
            binding.txtName.setText("Bạn chưa có địa chỉ giao hàng");
        }

        //binding.txtTotal.setText(longConvertMoney(sumPrice));

        setCostumeCart();
        setListeners();
    }

    private void sumTotal(){
        sumSelect = 0;
        sumPrice = 0;
        for(int i = 0; i < costumes.size(); i++){
            if(costumes.get(i).getState()){
                if(costumes.get(i).getCostume().getPromotion() != null) {
                    if (RangeTime.checkRangeEvent(costumes.get(i).getCostume().getPromotion().getStartTime(), costumes.get(i).getCostume().getPromotion().getEndTime())) {
                        isCheckPromotion = true;
                        sumPrice += costumes.get(i).getQuantity() * (costumes.get(i).getCostume().getPrice() * (100 - costumes.get(i).getCostume().getPromotion().getValue()) / 100);
                        continue;
                    }
                }

                sumPrice += costumes.get(i).getQuantity()*costumes.get(i).getCostume().getPrice();
                sumSelect ++;
            }
        }

        setMoney();
    }

    private void setMoney(){
        binding.txtTemporaryMoney.setText(longConvertMoney(sumPrice+fee));

        if(isCheckPromotion){
            long cost = 0;
            for(Costume_Cart item: costumes){
                cost += item.getQuantity()*item.getCostume().getPrice();
            }

            binding.txtBuyMoney.setText(Html.fromHtml("<strike>"+longConvertMoney(cost)+"</strike>"));
        }else{
            binding.txtBuyMoney.setText(Html.fromHtml("<strike>"+longConvertMoney(sumPrice+fee)+"</strike>"));
        }

        if(prePromotion != -1){
            binding.txtPromotionMoney.setVisibility(View.VISIBLE);
            binding.txtMoney5.setVisibility(View.VISIBLE);

            long pricePromotion = sumPrice*(promotions.get(prePromotion).getValue())/100;

            binding.txtPromotionMoney.setText(longConvertMoney(pricePromotion+fee));
            sumPricePromotion = sumPrice-pricePromotion+fee;

            binding.txtTotal.setText(longConvertMoney(sumPricePromotion+fee));
        }else{
            binding.txtPromotionMoney.setVisibility(View.GONE);
            binding.txtMoney5.setVisibility(View.GONE);

            binding.txtTotal.setText(longConvertMoney(sumPrice+fee));
        }
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
        if (code == paymentDefault) {
            binding.btnSelect1.setImageResource(R.drawable.circle_check);
            binding.btnSelect2.setImageResource(R.drawable.circle_none);
        }else{
            binding.btnSelect1.setImageResource(R.drawable.circle_none);
            binding.btnSelect2.setImageResource(R.drawable.circle_check);
        }
    }

    private void setCostumeCart(){
        costumeCartAdapter = new CostumePaymentAdapter(costumes, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclCostumeCart.setLayoutManager(gridLayoutManager);
        binding.rclCostumeCart.setAdapter(costumeCartAdapter);
    }

    private void setPromotion(){
        promotionAdapter = new PromotionAdapter(promotions, this, new PromotionAdapter.PromotionListeners() {
            @Override
            public void onClick(Promotion promotion, int position, boolean isSelect) {
                if(isSelect) {
//                    sumPricePromotion = sumPrice*(100-promotion.getValue())/100;
//                    binding.txtTotal.setText(longConvertMoney(sumPricePromotion));
//                    binding.txtTotal.setTextColor(getResources().getColor(R.color.color1));
//                    binding.txtTotalPromotion.setVisibility(View.VISIBLE);
//                    binding.txtTotalPromotion.setText(Html.fromHtml("<strike>"+longConvertMoney(sumPrice)+"</strike>"));

                    //kiểm tra đã chọn khuyến mãi khác chưa
                    if (prePromotion != -1) {
                        promotions.get(prePromotion).setSelect(false);

                        promotionAdapter.notifyItemChanged(prePromotion);
                    }

                    prePromotion = position;

                    setMoney();
                }else{
//                    binding.txtTotal.setTextColor(getResources().getColor(R.color.black));
//                    binding.txtTotalPromotion.setVisibility(View.GONE);
//                    binding.txtTotal.setText(longConvertMoney(sumPrice));
                    prePromotion = -1;

                    setMoney();
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclPromotion.setLayoutManager(gridLayoutManager);
        binding.rclPromotion.setAdapter(promotionAdapter);
    }

    private void setListeners(){
        binding.layoutAddress.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), AddressActivity.class);
            startActivity(intent);
        });

        binding.layoutPayment1.setOnClickListener(v -> {
            paymentMethods = paymentDefault;

            changePayment(paymentDefault);
        });

        binding.layoutPayment2.setOnClickListener(v -> {
            paymentMethods = paymentZalo;

            changePayment(paymentZalo);
        });

        binding.btnCreateBill.setOnClickListener(v -> {
            List<Request_CostumeBill> costumeBills = new ArrayList<>();

            for (Costume_Cart costume: costumes) {
                Request_CostumeBill costumeBill = new Request_CostumeBill(costume.getCostume().getId(), costume.getQuantity(), costume.getColor(), costume.getSize());

                costumeBills.add(costumeBill);
            }

            if(paymentMethods == paymentDefault){
                Request_Bill bill = new Request_Bill(addressDefault.getId(), costumeBills, fee, false);
                if(prePromotion != -1)
                    bill = new Request_Bill(addressDefault.getId(), costumeBills, promotions.get(prePromotion), fee, false);
                loading(true);
                addBill(bill);
            }else if(paymentMethods == paymentZalo){
                Request_Bill bill = new Request_Bill(addressDefault.getId(), costumeBills, fee, true);
                if(prePromotion != -1)
                    bill = new Request_Bill(addressDefault.getId(), costumeBills, promotions.get(prePromotion), fee, true);
                loading(true);
                paymentZaloPay(sumPrice+"", bill);
            }
        });

        binding.btnBackBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void paymentZaloPay(String amount, Request_Bill bill){
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(amount);
            String code = data.getString("returncode");

            Log.e("code", code);
            if (code.equals("1")) {

                String token = data.getString("zptranstoken");

//                ZaloPaySDK.getInstance().payOrder(PaymentActivity.this, token, "demozpdk://app", new PayOrderListener() {
//                    @Override
//                    public void onPaymentSucceeded(final String  transactionId, final String transToken, final String appTransID) {
//                        Toast.makeText(getApplication(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
//                        addBill(bill);
//                    }
//
//                    @Override
//                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
//                        Toast.makeText(getApplication(), "Thanh toán bị hủy", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
//                        Toast.makeText(getApplication(), "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

        } catch (Exception e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        ZaloPaySDK.getInstance().onResult(intent);
    }

    private void addBill(Request_Bill bill){
        Service service = getRetrofit().create(Service.class);
        Call<Message> call = service.AddBill("bearer "+shared_preferences.getToken(), bill);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                loading(false);
                Log.i("errorAddBill", response.body().getId());
                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                loading(false);
                Log.i("errorAddBill", t.toString());
                Toast.makeText(getApplication(), "Thêm hóa đơn thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPromotion(){
        Service service = getRetrofit().create(Service.class);
        Call<BeforeCreateBill> call = service.BeforeCreate("bearer "+shared_preferences.getToken());
        call.enqueue(new Callback<BeforeCreateBill>() {
            @Override
            public void onResponse(Call<BeforeCreateBill> call, Response<BeforeCreateBill> response) {
                if(response.body().getStatus() == 1){
                    if(response.body().getData() != null){
                        promotions = response.body().getData();

                        setPromotion();
                    }
                }else
                    Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BeforeCreateBill> call, Throwable t) {
                Toast.makeText(getApplication(), "Không lấy dữ liệu được", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void getFee(int total, int service_id){
        ServiceService service = getRetrofitLocation().create(ServiceService.class);
        Call<MessageFee> call = service.GetFee(Constant.tokenLocation, new RequestFee(service_id, total, null, shop_district, Integer.parseInt(addressDefault.getDistrictId()), Integer.parseInt(addressDefault.getWardId())));
        call.enqueue(new Callback<MessageFee>() {
            @Override
            public void onResponse(Call<MessageFee> call, Response<MessageFee> response) {
                if(response.body().getCode() == 200){
                    fee = response.body().getData().getService_fee();
                    binding.txtTransportMoney.setText(ConvertMoney.intConvertMoney(response.body().getData().getService_fee()));
                    binding.txtTotal.setText(ConvertMoney.intConvertMoney(Integer.parseInt(sumPrice+"")+fee));
                }
            }

            @Override
            public void onFailure(Call<MessageFee> call, Throwable t) {

            }
        });
    }

    private void getService(int total){
        ServiceService service = getRetrofitLocation().create(ServiceService.class);
        Call<MessageService> call = service.GetServices(Constant.tokenLocation, new RequestService(shop_id, shop_district, Integer.parseInt(addressDefault.getDistrictId())));
        call.enqueue(new Callback<MessageService>() {
            @Override
            public void onResponse(Call<MessageService> call, Response<MessageService> response) {
                if(response.body().getCode() == 200){
                    List<com.personal_game.datn.Api.ModelFee.Service> services = response.body().getData();

                    for(com.personal_game.datn.Api.ModelFee.Service service1: services){
                        if(service1.getShort_name().equals("Đi bộ")){
                            getFee(total, service1.getService_id());

                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageService> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        addressDefault = shared_preferences.getAddress();

        binding.txtName.setText(addressDefault.getName());
        binding.txtPhone.setText(addressDefault.getPhone());
        binding.txtAddress.setText(addressDefault.getStreet());
        binding.txtAddress1.setText(addressDefault.getWard() + " - "+addressDefault.getDistrict()+" - "+addressDefault.getCity());

        if(sumPrice <= 699000)
            getService(Integer.parseInt(sumPrice+""));
        else
            binding.txtTransportMoney.setText("miễn phí");
    }
}