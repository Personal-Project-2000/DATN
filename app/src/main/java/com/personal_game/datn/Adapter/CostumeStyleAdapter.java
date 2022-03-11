package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemCostumeStyleBinding;

import java.util.List;

public class CostumeStyleAdapter extends RecyclerView.Adapter<CostumeStyleAdapter.ViewHolder>{
    private final List<String> costumeStyleList;
    private final Context context;
    private final CostumeStyleListeners costumeStyleListeners;

    public CostumeStyleAdapter(List<String> costumeStyleList, Context context, CostumeStyleListeners costumeStyleListeners){
        this.costumeStyleList = costumeStyleList;
        this.context = context;
        this.costumeStyleListeners = costumeStyleListeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCostumeStyleBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(costumeStyleList.get(position));
    }

    @Override
    public int getItemCount() {
        return costumeStyleList != null ? costumeStyleList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemCostumeStyleBinding binding;

        public ViewHolder(@NonNull ItemCostumeStyleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String costumeStyle) {

        }
    }

    public interface CostumeStyleListeners {
        void onClick(String costumeStyle);
    }
}
