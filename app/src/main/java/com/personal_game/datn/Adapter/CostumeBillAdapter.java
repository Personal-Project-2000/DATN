package com.personal_game.datn.Adapter;

import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Models.BillDetail;
import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ItemCostumeBillBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CostumeBillAdapter extends RecyclerView.Adapter<CostumeBillAdapter.ViewHolder>{
    private final List<BillDetail> costumeList;
    private final Context context;
    private final CostumeBillListeners costumeBillListeners;

    public CostumeBillAdapter(List<BillDetail> costumeList, Context context, CostumeBillListeners costumeBillListeners){
        this.costumeList = costumeList;
        this.context = context;
        this.costumeBillListeners = costumeBillListeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((ItemCostumeBillBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        )));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(costumeList.get(position));
    }

    @Override
    public int getItemCount() {
        return costumeList != null ? costumeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemCostumeBillBinding binding;

        public ViewHolder(@NonNull ItemCostumeBillBinding binding) {
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

            binding.txtName.setText(costume.getName());
            binding.txtPrice.setText(intConvertMoney(costume.getPrice()));
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
        }
    }

    public interface CostumeBillListeners {
        void onClick(Costume costume);
    }
}
