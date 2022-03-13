package com.personal_game.datn.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.personal_game.datn.Adapter.BillAdapter;
import com.personal_game.datn.Adapter.CostumeAdapter;
import com.personal_game.datn.Adapter.CostumeBillAdapter;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ActivityBillBinding;
import com.personal_game.datn.databinding.ActivityCostumeBinding;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {
    private ActivityBillBinding binding;
    private BillAdapter billAdapter;
    private CostumeBillAdapter costumeBillAdapter;

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
        int code = intent.getIntExtra("code", 1);
        changeSelectStateBill(code);

        setListeners();
        setBill();
    }

    //state => 1: all, 2: wait, 3:handle, 4: transported, 5: complete, 6: cancel
    private void changeSelectStateBill(int state){
        switch (state){
            case 1:
                billAll();
                break;
            case 2:
                billWaiting();
                break;
            case 3:
                billHandle();
                break;
            case 4:
                billTransported();
                break;
            case 5:
                billComplete();
                break;
            case 6:
                billCancel();
                break;
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
        });
    }

    private void setBill(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        billAdapter = new BillAdapter(temp, this, new BillAdapter.BillListeners() {
            @Override
            public void onClick(String bill) {
                binding.layoutBill.setVisibility(View.VISIBLE);

                setBillDetail();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclBill.setLayoutManager(gridLayoutManager);
        binding.rclBill.setAdapter(billAdapter);
    }

    private void setBillDetail(){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        costumeBillAdapter = new CostumeBillAdapter(temp, this, new CostumeBillAdapter.CostumeBillListeners() {
            @Override
            public void onClick(String costume) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclCostume.setLayoutManager(gridLayoutManager);
        binding.rclCostume.setAdapter(costumeBillAdapter);
    }
}