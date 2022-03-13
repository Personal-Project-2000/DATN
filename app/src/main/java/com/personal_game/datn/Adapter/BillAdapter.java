package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemAddressBinding;
import com.personal_game.datn.databinding.ItemBillBinding;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{
    private final List<String> billList;
    private final Context context;
    private final BillListeners billListeners;
    private BillImgAdapter billImgAdapter;

    public BillAdapter(List<String> billList, Context context, BillListeners billListeners){
        this.billList = billList;
        this.context = context;
        this.billListeners = billListeners;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBillBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(billList.get(position));
    }

    @Override
    public int getItemCount() {
        return billList != null ? billList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemBillBinding binding;

        public ViewHolder(@NonNull ItemBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String bill) {
            setImg(binding);

            binding.layoutMain.setOnClickListener(v -> {
                billListeners.onClick(bill);
            });
        }
    }

    public interface BillListeners {
        void onClick(String bill);
    }

    public void setImg(ItemBillBinding binding){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        billImgAdapter = new BillImgAdapter(temp, context);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclImg.setLayoutManager(gridLayoutManager);
        binding.rclImg.setAdapter(billImgAdapter);
    }
}
