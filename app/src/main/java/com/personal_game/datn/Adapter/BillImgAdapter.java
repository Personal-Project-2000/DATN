package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemBillImgBinding;
import com.personal_game.datn.databinding.ItemCostumeImgBinding;

import java.util.List;

public class BillImgAdapter extends RecyclerView.Adapter<BillImgAdapter.ViewHolder>{
    private final List<String> costumeImgList;
    private final Context context;

    public BillImgAdapter(List<String> costumeImgList, Context context){
        this.costumeImgList = costumeImgList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBillImgBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(costumeImgList.get(position));
    }

    @Override
    public int getItemCount() {
        return costumeImgList != null ? costumeImgList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemBillImgBinding binding;

        public ViewHolder(@NonNull ItemBillImgBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String costumeStyle) {

        }
    }
}
