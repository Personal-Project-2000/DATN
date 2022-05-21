package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Api.ModelLocation1.Location;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ItemLocationBinding;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{
    private final List<Location> locationList;
    private final Context context;
    private final LocationListeners locationListeners;

    public LocationAdapter(List<Location> locationList, Context context, LocationListeners locationListeners){
        this.locationList = locationList;
        this.context = context;
        this.locationListeners = locationListeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemLocationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(locationList.get(position));
    }

    @Override
    public int getItemCount() {
        return locationList != null ? locationList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemLocationBinding binding;

        public ViewHolder(@NonNull ItemLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Location location) {
            binding.txtName.setText(location.getName());

            binding.layoutMain.setOnClickListener(v -> {
                locationListeners.onClick(location, getAdapterPosition());
                binding.txtName.setTextColor(context.getColor(R.color.color1));
                binding.txtName.setText(binding.txtName.getText()+" ✔");
            });
        }
    }

    public interface LocationListeners {
        void onClick(Location location, int position);
    }
}
