package com.personal_game.datn.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Promotion;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Event;
import com.personal_game.datn.databinding.ItemEventBinding;
import com.personal_game.datn.databinding.ItemPromotionBinding;
import com.personal_game.datn.ultilities.RangeTime;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder>{
    private final Context context;
    private final Shared_Preferences shared_preferences;
    private final List<Promotion> promotions;
    private final PromotionListeners promotionListeners;

    public PromotionAdapter(List<Promotion> promotions, Context context, PromotionListeners promotionListeners){
        this.promotions = promotions;
        this.context = context;
        this.shared_preferences = new Shared_Preferences(context);
        this.promotionListeners = promotionListeners;
    }

    @NonNull
    @Override
    public PromotionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PromotionAdapter.ViewHolder(ItemPromotionBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(promotions.get(position));
    }

    @Override
    public int getItemCount() {
        return promotions != null ? promotions.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemPromotionBinding binding;

        public ViewHolder(@NonNull ItemPromotionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Promotion promotion) {
            if(promotion.isSelect())
                binding.btnSelect.setImageResource(R.drawable.circle_check);
            else
                binding.btnSelect.setImageResource(R.drawable.circle_none);

            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    binding.imgPromotion.setImageResource(R.drawable.ic_baseline_flash_on_24);
                }
            });
            Picasso pic = builder.build();
            pic.load(promotion.getIcon()).into(binding.imgPromotion);

            int[] colors = {Color.parseColor("#ffffff"), Color.parseColor(promotion.getColor()), Color.parseColor("#ffffff")};
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColors(colors);
            gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            binding.backgroundTitle.setBackground(gradientDrawable);

            binding.txtTitle.setText(promotion.getName());
            binding.txtValue.setText("Khuyến mãi: "+promotion.getValue()+"%");
            binding.txtCountDown.setText("Còn lại: "+"12:00:00");

            binding.layoutMain.setOnClickListener(v -> {
                if(!promotion.isSelect()){
                    promotion.setSelect(true);
                    binding.btnSelect.setImageResource(R.drawable.circle_check);
                }else{
                    promotion.setSelect(false);
                    binding.btnSelect.setImageResource(R.drawable.circle_none);
                }
                promotionListeners.onClick(promotion, getAdapterPosition(), promotion.isSelect());
            });
        }

        private String countDownTime(long milis){
            int seconds = (int) (milis / 1000) % 60;
            int minutes = (int) ((milis / (1000 * 60)) % 60);
            int hours = (int) ((milis / (1000 * 60 * 60)) % 24);
            int days = (int) ((milis / (1000 * 60 * 60)) / 24);

            if (days > 0)
                return days+" ngày";
            else
                return hours + ":" + minutes + ":" + seconds;
        }

        private int day(long milis){
            return (int) ((milis / (1000 * 60 * 60)) / 24);
        }
    }

    public interface PromotionListeners{
        void onClick(Promotion promotion, int position, boolean isSelect);
    }
}
