package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Backup.Constant.allBill;
import static com.personal_game.datn.Backup.Constant.billCancel;
import static com.personal_game.datn.Backup.Constant.billCancelId;
import static com.personal_game.datn.Backup.Constant.billComplete;
import static com.personal_game.datn.Backup.Constant.billCompleteId;
import static com.personal_game.datn.Backup.Constant.billHandle;
import static com.personal_game.datn.Backup.Constant.billHandleId;
import static com.personal_game.datn.Backup.Constant.billPaid;
import static com.personal_game.datn.Backup.Constant.billTransported;
import static com.personal_game.datn.Backup.Constant.billTransportedId;
import static com.personal_game.datn.Backup.Constant.billWait;
import static com.personal_game.datn.Backup.Constant.billWaitId;
import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;
import static com.personal_game.datn.ultilities.ConvertMoney.longConvertMoney;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Adapter.BillAdapter;
import com.personal_game.datn.Adapter.CostumeBillAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.BillDetail;
import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.R;
import com.personal_game.datn.Request.Request_UpdateBill;
import com.personal_game.datn.Response.BillInfo;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Bill;
import com.personal_game.datn.databinding.ActivityBillBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillActivity extends AppCompatActivity {
    private ActivityBillBinding binding;
    private BillAdapter billAdapter;
    private CostumeBillAdapter costumeBillAdapter;

    private Shared_Preferences shared_preferences;
    private List<BillInfo> billInfos = new ArrayList<>();
    private List<BillInfo> temp = new ArrayList<>();
    private int showBillAsState = 1;
    private String stateId = billCancelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        showBillAsState = intent.getIntExtra("code", allBill);
        changeSelectStateBill(showBillAsState);

        shared_preferences = new Shared_Preferences(getApplicationContext());
        billInfos = new ArrayList<>();

        setListeners();
        getBill();
    }

    private void setMoney(int total, int valuePromotion){
        binding.txtBuyMoney.setText(longConvertMoney(total));

        if(valuePromotion != -1) {
            binding.txtMoney5.setVisibility(View.VISIBLE);
            binding.txtPromotionMoney.setVisibility(View.VISIBLE);

            int pricePromotion = total * (valuePromotion) / 100;

            binding.txtTotal.setText(longConvertMoney(total - pricePromotion));
            binding.txtPromotionMoney.setText(longConvertMoney(total * (valuePromotion) / 100));
        }else{
            binding.txtPromotionMoney.setVisibility(View.GONE);
            binding.txtMoney5.setVisibility(View.GONE);

            binding.txtTotal.setText(intConvertMoney(total));
        }
    }

    private void changeSelectStateBill(int state){
        switch (state){
            case allBill:
                billAll();
                break;
            case billWait:
                billWaiting();
                break;
            case billHandle:
                billHandle();
                break;
            case billTransported:
                billTransported();
                break;
            case billComplete:
                billComplete();
                break;
            case billCancel:
                billCancel();
                break;
            case billPaid:
                billPaid();
                break;
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

    private void loadingUpdate(boolean value){
        if(value){
            binding.btnCancel.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.btnCancel.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void billWaiting(){
        binding.txt12.setTextColor(getResources().getColor(R.color.black, null));
        binding.taskbarBillWaiting.setVisibility(View.VISIBLE);
        binding.txt13.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillHandle.setVisibility(View.GONE);
        binding.txt14.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillTransported.setVisibility(View.GONE);
        binding.txt15.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillComplete.setVisibility(View.GONE);
        binding.txt16.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillCancel.setVisibility(View.GONE);
        binding.txt17.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillAll.setVisibility(View.GONE);
        binding.txt24.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillPaid.setVisibility(View.GONE);

        selectRclBill(billWait);
    }

    private void billHandle(){
        binding.txt12.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillWaiting.setVisibility(View.GONE);
        binding.txt13.setTextColor(getResources().getColor(R.color.black, null));
        binding.taskbarBillHandle.setVisibility(View.VISIBLE);
        binding.txt14.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillTransported.setVisibility(View.GONE);
        binding.txt15.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillComplete.setVisibility(View.GONE);
        binding.txt16.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillCancel.setVisibility(View.GONE);
        binding.txt17.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillAll.setVisibility(View.GONE);
        binding.txt24.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillPaid.setVisibility(View.GONE);

        selectRclBill(billHandle);
    }

    private void billTransported(){
        binding.txt12.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillWaiting.setVisibility(View.GONE);
        binding.txt13.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillHandle.setVisibility(View.GONE);
        binding.txt14.setTextColor(getResources().getColor(R.color.black, null));
        binding.taskbarBillTransported.setVisibility(View.VISIBLE);
        binding.txt15.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillComplete.setVisibility(View.GONE);
        binding.txt16.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillCancel.setVisibility(View.GONE);
        binding.txt17.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillAll.setVisibility(View.GONE);
        binding.txt24.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillPaid.setVisibility(View.GONE);

        selectRclBill(billTransported);
    }

    private void billComplete(){
        binding.txt12.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillWaiting.setVisibility(View.GONE);
        binding.txt13.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillHandle.setVisibility(View.GONE);
        binding.txt14.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillTransported.setVisibility(View.GONE);
        binding.txt15.setTextColor(getResources().getColor(R.color.black, null));
        binding.taskbarBillComplete.setVisibility(View.VISIBLE);
        binding.txt16.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillCancel.setVisibility(View.GONE);
        binding.txt17.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillAll.setVisibility(View.GONE);
        binding.txt24.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillPaid.setVisibility(View.GONE);

        selectRclBill(billComplete);
    }

    private void billCancel(){
        binding.txt12.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillWaiting.setVisibility(View.GONE);
        binding.txt13.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillHandle.setVisibility(View.GONE);
        binding.txt14.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillTransported.setVisibility(View.GONE);
        binding.txt15.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillComplete.setVisibility(View.GONE);
        binding.txt16.setTextColor(getResources().getColor(R.color.black, null));
        binding.taskbarBillCancel.setVisibility(View.VISIBLE);
        binding.txt17.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillAll.setVisibility(View.GONE);
        binding.txt24.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillPaid.setVisibility(View.GONE);

        selectRclBill(billCancel);
    }

    private void billAll(){
        binding.txt12.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillWaiting.setVisibility(View.GONE);
        binding.txt13.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillHandle.setVisibility(View.GONE);
        binding.txt14.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillTransported.setVisibility(View.GONE);
        binding.txt15.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillComplete.setVisibility(View.GONE);
        binding.txt16.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillCancel.setVisibility(View.GONE);
        binding.txt17.setTextColor(getResources().getColor(R.color.black, null));
        binding.taskbarBillAll.setVisibility(View.VISIBLE);
        binding.txt24.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillPaid.setVisibility(View.GONE);

        selectRclBill(allBill);
    }

    private void billPaid(){
        binding.txt12.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillWaiting.setVisibility(View.GONE);
        binding.txt13.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillHandle.setVisibility(View.GONE);
        binding.txt14.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillTransported.setVisibility(View.GONE);
        binding.txt15.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillComplete.setVisibility(View.GONE);
        binding.txt16.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillCancel.setVisibility(View.GONE);
        binding.txt17.setTextColor(getResources().getColor(R.color.secondary_text, null));
        binding.taskbarBillAll.setVisibility(View.GONE);
        binding.txt24.setTextColor(getResources().getColor(R.color.black, null));
        binding.taskbarBillPaid.setVisibility(View.VISIBLE);

        selectRclBill(billPaid);
    }

    private void setListeners(){
        binding.layoutBillAll.setOnClickListener(v -> {
            changeSelectStateBill(1);
        });

        binding.layoutBillWaiting.setOnClickListener(v -> {
            changeSelectStateBill(2);
        });

        binding.layoutBillHandle.setOnClickListener(v -> {
            changeSelectStateBill(3);
        });

        binding.layoutBillTransported.setOnClickListener(v -> {
            changeSelectStateBill(4);
        });

        binding.layoutBillComplete.setOnClickListener(v -> {
            changeSelectStateBill(5);
        });

        binding.layoutBillCancel.setOnClickListener(v -> {
            changeSelectStateBill(6);
        });

        binding.btnClose.setOnClickListener(v -> {
            binding.layoutBill.setVisibility(View.GONE);
            binding.layoutMoney.setVisibility(View.GONE);
        });

        binding.layoutBillPaid.setOnClickListener(v -> {
            changeSelectStateBill(7);
        });

        binding.layoutD.setOnClickListener(v ->{
            binding.layoutBill.setVisibility(View.GONE);
        });

        binding.btnBackBack.setOnClickListener(v -> {
            finish();
        });

        binding.layoutD.setOnClickListener(v -> {
            binding.layoutBill.setVisibility(View.GONE);
        });

        binding.btnPayDetail.setOnClickListener(v -> {
            binding.layoutMoney.setVisibility(View.VISIBLE);
        });
    }

    private void selectRclBill(int code){
        String state = "";

        switch (code){
            case billWait:
                state = billWaitId;
                break;
            case billCancel:
                state = billCancelId;
                break;
            case billComplete:
                state = billCompleteId;
                break;
            case billHandle:
                state = billHandleId;
                break;
            case billTransported:
                state = billTransportedId;
                break;
            default:
                state = "";
                break;
        }

        Log.e("notification", code +" "+state);

        if(code == allBill){
            temp = billInfos;
            setRclBill();
        }else{
            temp = new ArrayList<>();
            if(billInfos != null) {
                for (int i = 0; i < billInfos.size(); i ++) {
                    if(code != billPaid) {
                        if (billInfos.get(i).getBillState().getId().equals(state)) {
                            temp.add(billInfos.get(i));
                        }
                    }else{
                        if (billInfos.get(i).getBill().isPayment()) {
                            temp.add(billInfos.get(i));
                        }
                    }
                }

                setRclBill();
            }
        }
    }

    private void setRclBill(){
        billAdapter = new BillAdapter(temp, this, new BillAdapter.BillListeners() {
            @Override
            public void onClick(BillInfo bill) {
                binding.layoutBill.setVisibility(View.VISIBLE);
                binding.layoutMoney.setVisibility(View.GONE);
                binding.txtName.setText(bill.getBill().getName());
                binding.txtPhone.setText(bill.getBill().getPhone());
                binding.txtAddress.setText(bill.getBill().getStreet());
                binding.txtAddress1.setText(bill.getBill().getAddress());
                binding.txtTotal.setText(intConvertMoney(bill.getBill().getTotal()));
                binding.txtTitleBill.setText("Chi tiết đơn hàng: "+bill.getBill().getId());

                setMoney(bill.getBill().getTotal(), bill.getBill().getPromotion() != null ? bill.getBill().getPromotion().getValue() : -1);

                binding.btnCancel.setOnClickListener(v -> {
                    if(!bill.getBillState().getId().equals(billWaitId)){
                        Toast.makeText(getApplication(), "Hóa đơn này không thể hủy!", Toast.LENGTH_SHORT).show();
                    }else{
                        Request_UpdateBill updateBill = new Request_UpdateBill(bill.getBill().getId(), billCancelId);
                        updateBill(updateBill);
                    }
                });

                setRclBillDetail(bill.getBill().getBillDetails());
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclBill.setLayoutManager(gridLayoutManager);
        binding.rclBill.setAdapter(billAdapter);
    }

    private void setRclBillDetail(List<BillDetail> costumes){
        costumeBillAdapter = new CostumeBillAdapter(costumes, this, new CostumeBillAdapter.CostumeBillListeners() {
            @Override
            public void onClick(Costume costume) {
                Intent intent = new Intent(getApplication(), CostumeActivity.class);
                intent.putExtra("costumeId", costume.getId());
                startActivity(intent);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclCostume.setLayoutManager(gridLayoutManager);
        binding.rclCostume.setAdapter(costumeBillAdapter);
    }

    private void getBill(){
        loading(true);

        Service service = getRetrofit().create(Service.class);
        Call<Message_Bill> billCall = service.BillsWithUser("bearer "+shared_preferences.getToken());
        billCall.enqueue(new Callback<Message_Bill>() {
            @Override
            public void onResponse(Call<Message_Bill> call, Response<Message_Bill> response) {
                if(response.body().getStatus() == 1){
                    billInfos = response.body().getBills();

                    selectRclBill(showBillAsState);
                }

                loading(false);
            }

            @Override
            public void onFailure(Call<Message_Bill> call, Throwable t) {
                Toast.makeText(getApplication(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                loading(false);
            }
        });
    }

    private void updateBill(Request_UpdateBill updateBill){
        loadingUpdate(true);

        Service service = getRetrofit().create(Service.class);
        Call<Message> billCall = service.UpdateBill("bearer "+shared_preferences.getToken(), updateBill);
        billCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    for(int i = 0; i < temp.size(); i ++){
                        if(temp.get(i).getBill().getId().equals(updateBill.getBillId())){
                            temp.get(i).getBillState().setId(billCancelId);
                            temp.get(i).getBillState().setName("Đã hủy");

                            billAdapter.notifyItemChanged(i);
                            i = temp.size()+1;
                        }
                    }

                    for (int i = 0; i < billInfos.size(); i ++){
                        if(billInfos.get(i).getBill().getId().equals(updateBill.getBillId())){
                            billInfos.get(i).getBillState().setId(billCancelId);
                            billInfos.get(i).getBillState().setName("Đã hủy");

                            i = temp.size()+1;
                        }
                    }

                    binding.layoutBill.setVisibility(View.GONE);
                }

                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();

                loadingUpdate(false);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getApplication(), "Cập nhật hóa đơn thất bại", Toast.LENGTH_SHORT).show();

                loadingUpdate(false);
            }
        });
    }
}