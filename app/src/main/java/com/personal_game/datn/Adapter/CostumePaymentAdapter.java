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
import com.personal_game.datn.R;
import com.personal_game.datn.Response.Costume_Cart;
import com.personal_game.datn.databinding.ItemCostumeCartBinding;
import com.personal_game.datn.databinding.ItemCostumePaymentBinding;
import com.personal_game.datn.ultilities.RangeTime;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CostumePaymentAdapter extends RecyclerView.Adapter<CostumePaymentAdapter.ViewHolder>{
    private final List<Costume_Cart> costumeList;
    private final Context context;

    private final Shared_Preferences shared_preferences;

    public CostumePaymentAdapter(List<Costume_Cart> costumeList, Context context){
        this.costumeList = costumeList;
        this.context = context;
        this.shared_preferences = new Shared_Preferences(context);
    }

    @NonNull
    @Override
    public CostumePaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CostumePaymentAdapter.ViewHolder((ItemCostumePaymentBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        )));
    }

    @Override
    public void onBindViewHolder(@NonNull CostumePaymentAdapter.ViewHolder holder, int position) {
        holder.setData(costumeList.get(position));
    }

    @Override
    public int getItemCount() {
        return costumeList != null ? costumeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemCostumePaymentBinding binding;

        public ViewHolder(@NonNull ItemCostumePaymentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Costume_Cart costume) {
            int discount = costume.getCostume().getPrice();
            // giúp thông báo bên ngoài biết đang có khuyến mãi
            boolean isDiscount = false;

            if(costume.getCostume().getPromotion() != null){
                if(RangeTime.checkRangeEvent(costume.getCostume().getPromotion().getStartTime(), costume.getCostume().getPromotion().getEndTime())) {
                    binding.layoutDiscount.setVisibility(View.VISIBLE);
                    binding.txtPrice.setVisibility(View.GONE);
                    binding.txtValueEvent.setVisibility(View.VISIBLE);
                    isDiscount = true;

                    if (!costume.getCostume().getPromotion().getIcon().equals("")) {
                        Picasso.Builder builder = new Picasso.Builder(context);
                        builder.listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                binding.imgIConDiscount.setImageResource(R.drawable.ic_baseline_flash_on_24);
                            }
                        });
                        Picasso pic = builder.build();
                        pic.load(costume.getCostume().getPromotion().getIcon()).into(binding.imgIConDiscount);
                    }

                    binding.txtValueEvent.setText("-" + costume.getCostume().getPromotion().getValue() + "%");
                    discount = costume.getCostume().getPrice() * (100 - costume.getCostume().getPromotion().getValue()) / 100;
                    binding.txtPriceDiscount.setText(intConvertMoney(discount));
                    binding.txtDiscount.setText(Html.fromHtml("<strike>" + intConvertMoney(costume.getCostume().getPrice()) + "</strike>"));
                }else {
                    binding.layoutDiscount.setVisibility(View.GONE);
                    binding.txtPrice.setVisibility(View.VISIBLE);
                    binding.txtValueEvent.setVisibility(View.GONE);
                }
            }else{
                binding.layoutDiscount.setVisibility(View.GONE);
                binding.txtPrice.setVisibility(View.VISIBLE);
                binding.txtValueEvent.setVisibility(View.GONE);
            }

            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    binding.imgMain.setImageResource(R.drawable.logo);
                }
            });
            Picasso pic = builder.build();
            pic.load(costume.getCostume().getPictures().get(0).getLink()).into(binding.imgMain);

            binding.txtName.setText(costume.getCostume().getName());
            binding.txtPrice.setText(intConvertMoney(costume.getCostume().getPrice()));
            binding.txtQuantity.setText("x"+costume.getQuantity());
            if(costume.getSize() != null)
                binding.txtSize.setText(context.getString(R.string.size)+" "+costume.getSize());
            else
                binding.txtSize.setVisibility(View.GONE);

            if(costume.getColor() != null)
                binding.color.setBackgroundColor(Color.parseColor(costume.getColor().getCode()));
            else {
                binding.color.setVisibility(View.GONE);
                binding.txtColor.setVisibility(View.GONE);
            }

            binding.imgMain.setOnClickListener(v -> {
                Intent intent = new Intent(context, CostumeActivity.class);
                intent.putExtra("costumeId", costume.getCostume().getId());
                context.startActivity(intent);
            });
        }
    }
}
