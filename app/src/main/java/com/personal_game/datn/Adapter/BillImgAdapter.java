package com.personal_game.datn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Activity.CostumeActivity;
import com.personal_game.datn.Models.BillDetail;
import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.BillInfo;
import com.personal_game.datn.databinding.ItemBillImgBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BillImgAdapter extends RecyclerView.Adapter<BillImgAdapter.ViewHolder>{
    private final List<BillDetail> costumeImgList;
    private final Context context;

    public BillImgAdapter(List<BillDetail> costumeImgList, Context context){
        this.costumeImgList = costumeImgList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBillImgBinding.inflate(
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

        final ItemBillImgBinding binding;

        public ViewHolder(@NonNull ItemBillImgBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(BillDetail costume) {
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    binding.imgMain.setImageResource(R.drawable.logo);
                }
            });
            Picasso pic = builder.build();
            pic.load(costume.getImage()).into(binding.imgMain);

            binding.imgMain.setOnClickListener(v -> {
                Intent intent = new Intent(context, CostumeActivity.class);
                intent.putExtra("costumeId", costume.getCostumeId() );
                context.startActivity(intent);
            });
        }
    }
}
