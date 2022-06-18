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
import com.personal_game.datn.databinding.ItemCostumeCoordinateBinding;
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
        return new CostumeCoordinateAdapter.ViewHolder((ItemCostumeCoordinateBinding.inflate(
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

        final ItemCostumeCoordinateBinding binding;
        int total = 0;

        public ViewHolder(@NonNull ItemCostumeCoordinateBinding binding) {
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
                costumeCoordinateListeners.onClick(costume, getAdapterPosition(), total);
            });

            if(costume.getCostume().getPromotion() != null){
                long millisFutureStartTime = RangeTime.getBetweenDayToNow(costume.getCostume().getPromotion().getStartTime());

                if(millisFutureStartTime <= 0){
                    long millisFutureEndTime = RangeTime.getBetweenDayToNow(costume.getCostume().getPromotion().getEndTime());

                    if(millisFutureEndTime > 0){
                        binding.txtPrice.setText(intConvertMoney(costume.getCostume().getPrice() * (100 - costume.getCostume().getPromotion().getValue()) / 100));
                        binding.layoutEvent.setVisibility(View.VISIBLE);
                        binding.txtValueEvent.setText("-" + costume.getCostume().getPromotion().getValue() + "%");

                        Picasso.Builder builder1 = new Picasso.Builder(context);
                        builder1.listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                binding.imgEvent.setImageResource(R.drawable.ic_baseline_flash_on_24);
                            }
                        });

                        Picasso pic1 = builder1.build();
                        pic1.load(costume.getCostume().getPromotion().getIcon()).into(binding.imgEvent);

                        total = costume.getCostume().getPrice()*(100-costume.getCostume().getPromotion().getValue())/100;

                        binding.txtPrice.setText(intConvertMoney(total));

                        return;
                    }
                }
            }

            total = costume.getCostume().getPrice();
            binding.txtPrice.setText(intConvertMoney(total));
        }
    }

    public interface CostumeCoordinateListeners {
        void onClick(CostumeHome costumeHome, int position, int price);
    }
}
