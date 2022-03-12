package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemNotiBinding;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private final List<String> notificationList;
    private final Context context;
    private final CostumeStyleAdapter.CostumeStyleListeners costumeStyleListeners;

    public NotificationAdapter(List<String> notificationList, Context context, CostumeStyleAdapter.CostumeStyleListeners costumeStyleListeners){
        this.notificationList = notificationList;
        this.context = context;
        this.costumeStyleListeners = costumeStyleListeners;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNotiBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(notificationList.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationList != null ? notificationList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemNotiBinding binding;

        public ViewHolder(@NonNull ItemNotiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String notification) {

        }
    }

    public interface NotificationListeners {
        void onClick(String costumeStyle);
    }
}
