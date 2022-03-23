package com.personal_game.datn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Models.Test;
import com.personal_game.datn.Response.TestInfo;
import com.personal_game.datn.databinding.ItemQuestionBinding;
import com.personal_game.datn.databinding.ItemTestBinding;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder>{
    private final List<TestInfo> testList;
    private final Context context;
    private final TestListeners testListeners;

    public TestAdapter(List<TestInfo> testList, Context context, TestListeners testListeners){
        this.testList = testList;
        this.context = context;
        this.testListeners = testListeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTestBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(testList.get(position));
    }

    @Override
    public int getItemCount() {
        return testList != null ? testList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemTestBinding binding;

        public ViewHolder(@NonNull ItemTestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(TestInfo test) {
            binding.txtTitle.setText(test.getTest().getName());

            binding.layoutMain.setOnClickListener(v -> {
                testListeners.onClick(test);
            });
        }
    }

    public interface TestListeners {
        void onClick(TestInfo test);
    }
}
