package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemStyleBinding;
import com.personal_game.datn.databinding.ItemSuggestionBinding;

import java.util.List;

public class StyleAdapter extends RecyclerView.Adapter<StyleAdapter.ViewHolder>{
    private final List<String> styleList;
    private final Context context;
    private final StyleListeners styleListeners;

    public StyleAdapter(List<String> styleList, Context context, StyleListeners styleListeners){
        this.styleList = styleList;
        this.context = context;
        this.styleListeners = styleListeners;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemStyleBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(styleList.get(position));
    }

    @Override
    public int getItemCount() {
        return styleList != null ? styleList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemStyleBinding binding;

        public ViewHolder(@NonNull ItemStyleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String style) {

        }
    }

    public interface StyleListeners {
        void onClick(String style);
    }
}
