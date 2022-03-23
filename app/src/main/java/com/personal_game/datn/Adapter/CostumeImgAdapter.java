package com.personal_game.datn.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Models.Picture;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ItemCostumeBinding;
import com.personal_game.datn.databinding.ItemCostumeImgBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CostumeImgAdapter extends RecyclerView.Adapter<CostumeImgAdapter.ViewHolder>{
    private final List<Picture> costumeImgList;
    private final Context context;

    public CostumeImgAdapter(List<Picture> costumeImgList, Context context){
        this.costumeImgList = costumeImgList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCostumeImgBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(costumeImgList.get(position));
    }

    @Override
    public int getItemCount() {
        return costumeImgList != null ? costumeImgList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemCostumeImgBinding binding;

        public ViewHolder(@NonNull ItemCostumeImgBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Picture picture) {
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    binding.imgMain.setImageResource(R.drawable.logo);
                }
            });
            Picasso pic = builder.build();
            pic.load(picture.getLink()).into(binding.imgMain);
        }
    }
}
