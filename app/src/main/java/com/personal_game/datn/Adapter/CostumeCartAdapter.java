package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemCostumeBinding;
import com.personal_game.datn.databinding.ItemCostumeCartBinding;

import java.util.List;

public class CostumeCartAdapter extends RecyclerView.Adapter<CostumeCartAdapter.ViewHolder>{
    private final List<String> costumeList;
    private final Context context;
    private final CostumeCartListeners costumeCartListeners;

    public CostumeCartAdapter(List<String> costumeList, Context context, CostumeCartListeners costumeCartListeners){
        this.costumeList = costumeList;
        this.context = context;
        this.costumeCartListeners = costumeCartListeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((ItemCostumeCartBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        )));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(costumeList.get(position));
    }

    @Override
    public int getItemCount() {
        return costumeList != null ? costumeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemCostumeCartBinding binding;

        public ViewHolder(@NonNull ItemCostumeCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String costume) {

        }
    }

    public interface CostumeCartListeners {
        void onClick(String costume);
    }
}
