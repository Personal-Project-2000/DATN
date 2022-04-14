package com.personal_game.datn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Activity.DeliveryAddressActivity;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Address;
import com.personal_game.datn.Models.Size;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ItemAddressBinding;
import com.personal_game.datn.databinding.ItemSizeBinding;

import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder>{
    private final List<Size> sizes;
    private final Context context;
    private final SizeListeners sizeListeners;
    //code = 1: Đang xem thông tin trang phuc, = 2: Đang cài đặt kích cỡ của bản thân
    private final int code;

    private Shared_Preferences shared_preferences;

    public SizeAdapter(List<Size> sizes, Context context, SizeListeners sizeListeners, int code){
        this.sizes = sizes;
        this.context = context;
        this.shared_preferences = new Shared_Preferences(context);
        this.sizeListeners = sizeListeners;
        this.code = code;
    }


    @NonNull
    @Override
    public SizeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SizeAdapter.ViewHolder(ItemSizeBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(sizes.get(position));
    }

    @Override
    public int getItemCount() {
        return sizes != null ? sizes.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemSizeBinding binding;

        public ViewHolder(@NonNull ItemSizeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Size size) {
            binding.txtSize.setText(size.getName());
            if(code == 2)
                binding.txtDescription.setText(size.getDescription());
            else
                binding.txtDescription.setVisibility(View.GONE);

            if(!size.isCheck()){
                binding.txtSize.setBackground(context.getDrawable(R.drawable.border_gray));
                binding.txtSize.setTextColor(context.getColor(R.color.black));
            }else{
                binding.txtSize.setBackground(context.getDrawable(R.drawable.border_blue));
                binding.txtSize.setTextColor(context.getColor(R.color.color2));
            }

            binding.layoutSize.setOnClickListener(v -> {
                if(size.isCheck()){
                    binding.txtSize.setBackground(context.getDrawable(R.drawable.border_gray));
                    binding.txtSize.setTextColor(context.getColor(R.color.black));
                }else{
                    binding.txtSize.setBackground(context.getDrawable(R.drawable.border_blue));
                    binding.txtSize.setTextColor(context.getColor(R.color.color2));
                }
                size.setCheck(!size.isCheck());
                sizeListeners.onClick(size, getAdapterPosition());
            });
        }
    }

    public interface SizeListeners{
        void onClick(Size size, int position);
    }
}
