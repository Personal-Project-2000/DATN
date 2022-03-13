package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemCostumeBillBinding;
import com.personal_game.datn.databinding.ItemCostumeCartBinding;

import java.util.List;

public class CostumeBillAdapter extends RecyclerView.Adapter<CostumeBillAdapter.ViewHolder>{
    private final List<String> costumeList;
    private final Context context;
    private final CostumeBillListeners costumeBillListeners;

    public CostumeBillAdapter(List<String> costumeList, Context context, CostumeBillListeners costumeBillListeners){
        this.costumeList = costumeList;
        this.context = context;
        this.costumeBillListeners = costumeBillListeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((ItemCostumeBillBinding.inflate(
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

        final ItemCostumeBillBinding binding;

        public ViewHolder(@NonNull ItemCostumeBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String costume) {

        }
    }

    public interface CostumeBillListeners {
        void onClick(String costume);
    }
}
