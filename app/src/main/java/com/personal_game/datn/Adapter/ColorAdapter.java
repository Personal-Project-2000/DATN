package com.personal_game.datn.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.ColorObject;
import com.personal_game.datn.databinding.ItemColorBinding;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder>{
    private final List<ColorObject> colors;
    private final Context context;
    private final ColorListeners colorListeners;

    private Shared_Preferences shared_preferences;

    public ColorAdapter(List<ColorObject> colors, Context context, ColorListeners colorListeners){
        this.colors = colors;
        this.context = context;
        this.shared_preferences = new Shared_Preferences(context);
        this.colorListeners = colorListeners;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemColorBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(colors.get(position));
    }

    @Override
    public int getItemCount() {
        return colors != null ? colors.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemColorBinding binding;

        public ViewHolder(@NonNull ItemColorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(ColorObject color) {
            binding.txtSize.setBackgroundColor(Color.parseColor(color.getCode()));
            binding.txtSize1.setBackgroundColor(Color.parseColor(color.getCode()));
            if(!color.isCheck()){
                binding.layoutSelect.setVisibility(View.GONE);
                binding.txtSize1.setVisibility(View.VISIBLE);
            }

            binding.layoutMain.setOnClickListener(v -> {
                if(color.isCheck()){
                    binding.layoutSelect.setVisibility(View.GONE);
                    binding.txtSize1.setVisibility(View.VISIBLE);
                }else{
                    binding.layoutSelect.setVisibility(View.VISIBLE);
                    binding.txtSize1.setVisibility(View.GONE);
                }
                color.setCheck(!color.isCheck());
                colorListeners.onClick(getAdapterPosition(), color);
            });
        }
    }

    public interface ColorListeners{
        void onClick(int position, ColorObject colorObject);
    }
}
