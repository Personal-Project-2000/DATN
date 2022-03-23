package com.personal_game.datn.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Activity.SuggestionActivity;
import com.personal_game.datn.Models.PersonalStyle;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.Suggestion;
import com.personal_game.datn.databinding.ItemSuggestionBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PersonalStyleAdapter extends RecyclerView.Adapter<PersonalStyleAdapter.ViewHolder>{
    private final List<PersonalStyle> suggestList;
    private final Context context;
    private final SuggestListeners suggestListeners;

    public PersonalStyleAdapter(List<PersonalStyle> suggestList, Context context, SuggestListeners suggestListeners){
        this.suggestList = suggestList;
        this.context = context;
        this.suggestListeners = suggestListeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSuggestionBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(suggestList.get(position));
    }

    @Override
    public int getItemCount() {
        return suggestList != null ? suggestList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemSuggestionBinding binding;

        public ViewHolder(@NonNull ItemSuggestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(PersonalStyle personalStyle) {
            binding.layoutMain.setVisibility(View.VISIBLE);
            if(personalStyle.getSex() == SuggestionActivity.isSexSuggest) {
                binding.txtTitle.setText(personalStyle.getName());
                binding.txtContext.setText(personalStyle.getDescription());

                Picasso.Builder builder = new Picasso.Builder(context);
                builder.listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        binding.imgMain.setImageResource(R.drawable.logo);
                    }
                });
                Picasso pic = builder.build();
                pic.load(personalStyle.getImg()).into(binding.imgMain);

                if (true) {
                    binding.btnSelect.setImageResource(R.drawable.circle_none);
                    binding.layoutMain.setBackgroundColor(context.getResources().getColor(R.color.white));
                }

                binding.layoutMain.setOnClickListener(v -> {
                    binding.btnSelect.setImageResource(R.drawable.circle_check);
                    binding.layoutMain.setBackgroundColor(context.getResources().getColor(R.color.color5));
                    suggestListeners.onClick(personalStyle, getAdapterPosition());
                });
            }else{
                binding.layoutMain.setVisibility(View.GONE);
            }
        }
    }

    public interface SuggestListeners {
        void onClick(PersonalStyle personalStyle, int position);
    }
}
