package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemSuggestionBinding;

import java.util.List;

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.ViewHolder>{
    private final List<String> suggestList;
    private final Context context;
    private final SuggestListeners suggestListeners;

    public SuggestAdapter(List<String> suggestList, Context context, SuggestListeners suggestListeners){
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

        public void setData(String suggest) {

        }
    }

    public interface SuggestListeners {
        void onClick(String suggest);
    }
}
