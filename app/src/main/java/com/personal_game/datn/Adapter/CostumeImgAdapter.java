package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemCostumeBinding;
import com.personal_game.datn.databinding.ItemCostumeImgBinding;

import java.util.List;

public class CostumeImgAdapter extends RecyclerView.Adapter<CostumeImgAdapter.ViewHolder>{
    private final List<String> costumeImgList;
    private final Context context;
    private final CostumeImgListeners costumeImgListeners;

    public CostumeImgAdapter(List<String> costumeImgList, Context context, CostumeImgListeners costumeImgListeners){
        this.costumeImgList = costumeImgList;
        this.context = context;
        this.costumeImgListeners = costumeImgListeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCostumeImgBinding.inflate(
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

        final ItemCostumeImgBinding binding;

        public ViewHolder(@NonNull ItemCostumeImgBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String costumeStyle) {

        }
    }

    public interface CostumeImgListeners {
        void onClick(String costumeStyle);
    }
}
