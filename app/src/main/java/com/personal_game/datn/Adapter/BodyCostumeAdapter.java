package com.personal_game.datn.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Models.Body;
import com.personal_game.datn.Models.PersonalStyle;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ItemStyleBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BodyCostumeAdapter extends RecyclerView.Adapter<BodyCostumeAdapter.ViewHolder>{
    private final List<Body> bodyList;
    private final Context context;

    public BodyCostumeAdapter(List<Body> bodyList, Context context){
        this.bodyList = bodyList;
        this.context = context;
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
        holder.setData(bodyList.get(position));
    }

    @Override
    public int getItemCount() {
        return bodyList != null ? bodyList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemStyleBinding binding;

        public ViewHolder(@NonNull ItemStyleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Body body) {
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    binding.imgMain.setImageResource(R.drawable.logo);
                }
            });
            Picasso pic = builder.build();
            pic.load(body.getImg()).into(binding.imgMain);

            binding.txtName.setText(body.getName());
        }
    }
}
