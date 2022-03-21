package com.personal_game.datn.Adapter;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Activity.CostumeActivity;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Data;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Home;
import com.personal_game.datn.databinding.ItemCostumeBinding;
import com.personal_game.datn.databinding.ItemCostumeStyleBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CostumeAdapter extends RecyclerView.Adapter<CostumeAdapter.ViewHolder>{
    private final List<CostumeHome> costumeList;
    private final Context context;
    private final Shared_Preferences shared_preferences;

    public CostumeAdapter(List<CostumeHome> costumeList, Context context){
        this.costumeList = costumeList;
        this.context = context;
        this.shared_preferences = new Shared_Preferences(context);
    }

    @NonNull
    @Override
    public CostumeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CostumeAdapter.ViewHolder(ItemCostumeBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull CostumeAdapter.ViewHolder holder, int position) {
        holder.setData(costumeList.get(position));
    }

    @Override
    public int getItemCount() {
        return costumeList != null ? costumeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemCostumeBinding binding;

        public ViewHolder(@NonNull ItemCostumeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(CostumeHome costume) {
            if(costume != null){
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
                binding.txtPrice.setText(costume.getCostume().getPrice()+"");

                if(costume.getFavourite()){
                    binding.imgFavourite.setImageResource(R.drawable.ic_baseline_favorite_24);
                }else{
                    binding.imgFavourite.setImageResource(R.drawable.ic_favourite_none);
                }
            }

            binding.imgMain.setOnClickListener(v -> {

            });

            binding.imgFavourite.setOnClickListener(v -> {
                if(setFavourite(costume.getCostume().getId())) {
                    if (costume.getFavourite()) {
                        binding.imgFavourite.setImageResource(R.drawable.ic_favourite_none);
                        costume.setFavourite(false);
                    } else {
                        binding.imgFavourite.setImageResource(R.drawable.ic_baseline_favorite_24);
                        costume.setFavourite(true);
                    }
                }
            });
        }
    }

    public boolean setFavourite(String costumeId){
        final boolean[] check = {true};

        Service service = getRetrofit().create(Service.class);
        Call<Message> favourite = service.AddFavourite("bearer "+shared_preferences.getToken(), costumeId);
        favourite.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.body().getStatus() == 1){
                    check[0] = true;
                }else{
                    check[0] = false;
                    Toast.makeText(context, response.body().getNotification(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                check[0] = false;
            }
        });

        return check[0];
    }
}
