package com.personal_game.datn.Adapter;

import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Activity.CostumeActivity;
import com.personal_game.datn.Backup.Constant;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Costume_Cart;
import com.personal_game.datn.databinding.ItemBillImgBinding;
import com.personal_game.datn.databinding.ItemCostumeCartBinding;
import com.personal_game.datn.ultilities.RangeTime;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CostumeCoordinateAdapter extends RecyclerView.Adapter<CostumeCoordinateAdapter.ViewHolder>{
    private final List<CostumeHome> costumes;
    private final Context context;
    private final CostumeCoordinateListeners costumeCoordinateListeners;

    private final Shared_Preferences shared_preferences;

    public CostumeCoordinateAdapter(List<CostumeHome> costumes, Context context, CostumeCoordinateListeners costumeCoordinateListeners){
        this.costumes = costumes;
        this.context = context;
        this.costumeCoordinateListeners = costumeCoordinateListeners;
        this.shared_preferences = new Shared_Preferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CostumeCoordinateAdapter.ViewHolder((ItemBillImgBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        )));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(costumes.get(position));
    }

    @Override
    public int getItemCount() {
        return costumes != null ? costumes.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemBillImgBinding binding;

        public ViewHolder(@NonNull ItemBillImgBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(CostumeHome costume) {
            if(costume.getCostume().getPicture_Nones() != null)
                binding.imgCheck.setVisibility(View.VISIBLE);

            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    binding.imgMain.setImageResource(R.drawable.logo);
                }
            });
            Picasso pic = builder.build();
            pic.load(costume.getCostume().getPictures().get(0).getLink()).into(binding.imgMain);

            binding.imgMain.setOnClickListener(v -> {
                costumeCoordinateListeners.onClick(costume, getAdapterPosition());
            });
        }
    }

    public interface CostumeCoordinateListeners {
        void onClick(CostumeHome costumeHome, int position);
    }
}
