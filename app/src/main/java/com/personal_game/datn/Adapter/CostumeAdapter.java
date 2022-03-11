package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemCostumeBinding;
import com.personal_game.datn.databinding.ItemCostumeStyleBinding;

import java.util.List;

public class CostumeAdapter extends RecyclerView.Adapter<CostumeAdapter.ViewHolder>{
    private final List<String> costumeList;
    private final Context context;
    private final CostumeListeners costumeListeners;

    public CostumeAdapter(List<String> costumeList, Context context, CostumeListeners costumeListeners){
        this.costumeList = costumeList;
        this.context = context;
        this.costumeListeners = costumeListeners;
    }

    @NonNull
    @Override
    public CostumeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CostumeAdapter.ViewHolder(ItemCostumeBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull CostumeAdapter.ViewHolder holder, int position) {
        holder.setData(costumeList.get(position));
    }

    @Override
    public int getItemCount() {
        return costumeList != null ? costumeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemCostumeBinding binding;

        public ViewHolder(@NonNull ItemCostumeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String costumeStyle) {

        }
    }

    public interface CostumeListeners {
        void onClick(String costumeStyle);
    }
}
