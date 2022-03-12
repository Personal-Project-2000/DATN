package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.databinding.ItemQuestionBinding;
import com.personal_game.datn.databinding.ItemSuggestionBinding;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{
    private final List<String> questionList;
    private final Context context;
    private final QuestionListeners questionListeners;

    public QuestionAdapter(List<String> questionList, Context context, QuestionListeners questionListeners){
        this.questionList = questionList;
        this.context = context;
        this.questionListeners = questionListeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemQuestionBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(questionList.get(position));
    }

    @Override
    public int getItemCount() {
        return questionList != null ? questionList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemQuestionBinding binding;

        public ViewHolder(@NonNull ItemQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(String suggest) {

        }
    }

    public interface QuestionListeners {
        void onClick(String suggest);
    }
}
