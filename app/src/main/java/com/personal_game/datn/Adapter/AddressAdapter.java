package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemAddressBinding;
import com.personal_game.datn.databinding.ItemNotiBinding;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{
    private final List<String> addressList;
    private final Context context;
    private final AddressListeners addressListeners;

    public AddressAdapter(List<String> addressList, Context context, AddressListeners addressListeners){
        this.addressList = addressList;
        this.context = context;
        this.addressListeners = addressListeners;
    }


    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressAdapter.ViewHolder(ItemAddressBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(addressList.get(position));
    }

    @Override
    public int getItemCount() {
        return addressList != null ? addressList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemAddressBinding binding;

        public ViewHolder(@NonNull ItemAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String address) {

        }
    }

    public interface AddressListeners {
        void onClick(String address);
    }
}
