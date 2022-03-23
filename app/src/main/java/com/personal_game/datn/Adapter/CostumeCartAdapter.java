package com.personal_game.datn.Adapter;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Activity.CostumeActivity;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Cart;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.Costume_Cart;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Cart;
import com.personal_game.datn.databinding.ItemCostumeBinding;
import com.personal_game.datn.databinding.ItemCostumeCartBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CostumeCartAdapter extends RecyclerView.Adapter<CostumeCartAdapter.ViewHolder>{
    private final List<Costume_Cart> costumeList;
    private final Context context;
    private final CostumeCartListeners costumeCartListeners;
    //code: 1 -> cart, 2 -> bill
    private int code = 1;

    private final Shared_Preferences shared_preferences;

    public CostumeCartAdapter(List<Costume_Cart> costumeList, Context context, CostumeCartListeners costumeCartListeners, int code){
        this.costumeList = costumeList;
        this.context = context;
        this.costumeCartListeners = costumeCartListeners;
        this.shared_preferences = new Shared_Preferences(context);
        this.code = code;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((ItemCostumeCartBinding.inflate(
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

        final ItemCostumeCartBinding binding;

        public ViewHolder(@NonNull ItemCostumeCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Costume_Cart costume) {
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    binding.imgMain.setImageResource(R.drawable.logo);
                }
            });
            Picasso pic = builder.build();
            pic.load(costume.getImage().getLink()).into(binding.imgMain);

            binding.txtName.setText(costume.getCostume().getName());
            binding.txtPrice.setText(intConvertMoney(costume.getCostume().getPrice()));
            binding.txtQuantity.setText(costume.getQuantity()+"");

            if(costume.getState())
                binding.btnSelect.setImageResource(R.drawable.circle_check);
            else
                binding.btnSelect.setImageResource(R.drawable.circle_none);

            if(code == 2){
                binding.btnSelect.setVisibility(View.GONE);
            }

            binding.btnRemove.setOnClickListener(v -> {
                int quantity = costume.getQuantity() - 1;

                costumeCartListeners.onClick(costume, quantity, costume.getState(), getAdapterPosition());
            });

            binding.btnAdd.setOnClickListener(v -> {
                int quantity = costume.getQuantity() + 1;

                costumeCartListeners.onClick(costume, quantity, costume.getState(), getAdapterPosition());
            });

            binding.btnSelect.setOnClickListener(v -> {
                boolean state = !costume.getState();

                costumeCartListeners.onClick(costume, costume.getQuantity(), state, getAdapterPosition());
            });

            binding.imgMain.setOnClickListener(v -> {
                Intent intent = new Intent(context, CostumeActivity.class);
                intent.putExtra("costumeId", costume.getCostume().getId());
                context.startActivity(intent);
            });
        }
    }

    public interface CostumeCartListeners {
        void onClick(Costume_Cart costume, int quantity, boolean state, int position);
    }
}
