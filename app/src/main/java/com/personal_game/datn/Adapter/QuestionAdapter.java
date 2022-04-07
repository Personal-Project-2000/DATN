package com.personal_game.datn.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Models.Answer;
import com.personal_game.datn.Models.Question;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ItemQuestionBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{
    private final List<Question> questionList;
    private final Context context;
    private final QuestionListeners questionListeners;

    public QuestionAdapter(List<Question> questionList, Context context, QuestionListeners questionListeners){
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

        final int selectA = 1;
        final int selectB = 2;
        final int selectC = 3;
        final int selectD = 4;

        String prevSelect = "";

        public ViewHolder(@NonNull ItemQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Question question) {
            binding.txtTitle.setText(question.getTopic());

            if(question != null){
                List<Answer> answers = question.getAnswers();

                if(!answers.get(0).getImg().equals("")){
                    binding.layoutImg.setVisibility(View.VISIBLE);
                    binding.layoutText.setVisibility(View.GONE);

                    Picasso.Builder builder = new Picasso.Builder(context);
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgA.setImageResource(R.drawable.logo);
                        }
                    });
                    Picasso pic = builder.build();
                    pic.load(answers.get(0).getImg()).into(binding.imgA);

                    Picasso.Builder builderB = new Picasso.Builder(context);
                    builderB.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgB.setImageResource(R.drawable.logo);
                        }
                    });
                    Picasso picB = builderB.build();
                    picB.load(answers.get(1).getImg()).into(binding.imgB);

                    Picasso.Builder builderC = new Picasso.Builder(context);
                    builderC.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgC.setImageResource(R.drawable.logo);
                        }
                    });
                    Picasso picC = builderC.build();
                    picC.load(answers.get(2).getImg()).into(binding.imgC);

                    Picasso.Builder builderD = new Picasso.Builder(context);
                    builderD.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            binding.imgD.setImageResource(R.drawable.logo);
                        }
                    });
                    Picasso picD = builderD.build();
                    picD.load(answers.get(3).getImg()).into(binding.imgD);
                }else{
                    binding.layoutImg.setVisibility(View.GONE);
                    binding.layoutText.setVisibility(View.VISIBLE);

                    binding.txtContextA.setText(answers.get(0).getName()+"/ "+answers.get(0).getContent());
                    binding.txtContextB.setText(answers.get(1).getName()+"/ "+answers.get(1).getContent());
                    binding.txtContextC.setText(answers.get(2).getName()+"/ "+answers.get(2).getContent());
                    binding.txtContextD.setText(answers.get(3).getName()+"/ "+answers.get(3).getContent());
                }
            }

            binding.layoutA.setOnClickListener(v -> {
                changeSelect(selectA);
            });

            binding.layoutA1.setOnClickListener(v -> {
                changeSelect(selectA);
            });

            binding.layoutB.setOnClickListener(v -> {
                changeSelect(selectB);
            });

            binding.layoutB1.setOnClickListener(v -> {
                changeSelect(selectB);
            });

            binding.layoutC.setOnClickListener(v -> {
                changeSelect(selectC);
            });

            binding.layoutC1.setOnClickListener(v -> {
                changeSelect(selectC);
            });

            binding.layoutD.setOnClickListener(v -> {
                changeSelect(selectD);
            });

            binding.layoutD1.setOnClickListener(v -> {
                changeSelect(selectD);
            });
        }

        public void changeSelect(int value){
            switch (value){
                case selectA:
                    questionListeners.onClick("A", prevSelect);
                    prevSelect = "A";
                    binding.btnSelectA.setImageResource(R.drawable.circle_check);
                    binding.btnSelectA1.setImageResource(R.drawable.circle_check);
                    binding.btnSelectB.setImageResource(R.drawable.circle_none);
                    binding.btnSelectB1.setImageResource(R.drawable.circle_none);
                    binding.btnSelectC.setImageResource(R.drawable.circle_none);
                    binding.btnSelectC1.setImageResource(R.drawable.circle_none);
                    binding.btnSelectD.setImageResource(R.drawable.circle_none);
                    binding.btnSelectD1.setImageResource(R.drawable.circle_none);
                    break;
                case selectB:
                    questionListeners.onClick("B", prevSelect);
                    prevSelect = "B";
                    binding.btnSelectA.setImageResource(R.drawable.circle_none);
                    binding.btnSelectA1.setImageResource(R.drawable.circle_none);
                    binding.btnSelectB.setImageResource(R.drawable.circle_check);
                    binding.btnSelectB1.setImageResource(R.drawable.circle_check);
                    binding.btnSelectC.setImageResource(R.drawable.circle_none);
                    binding.btnSelectC1.setImageResource(R.drawable.circle_none);
                    binding.btnSelectD.setImageResource(R.drawable.circle_none);
                    binding.btnSelectD1.setImageResource(R.drawable.circle_none);
                    break;
                case selectC:
                    questionListeners.onClick("C", prevSelect);
                    prevSelect = "C";
                    binding.btnSelectA.setImageResource(R.drawable.circle_none);
                    binding.btnSelectA1.setImageResource(R.drawable.circle_none);
                    binding.btnSelectB.setImageResource(R.drawable.circle_none);
                    binding.btnSelectB1.setImageResource(R.drawable.circle_none);
                    binding.btnSelectC.setImageResource(R.drawable.circle_check);
                    binding.btnSelectC1.setImageResource(R.drawable.circle_check);
                    binding.btnSelectD.setImageResource(R.drawable.circle_none);
                    binding.btnSelectD1.setImageResource(R.drawable.circle_none);
                    break;
                case selectD:
                    questionListeners.onClick("D", prevSelect);
                    prevSelect = "D";
                    binding.btnSelectA.setImageResource(R.drawable.circle_none);
                    binding.btnSelectA1.setImageResource(R.drawable.circle_none);
                    binding.btnSelectB.setImageResource(R.drawable.circle_none);
                    binding.btnSelectB1.setImageResource(R.drawable.circle_none);
                    binding.btnSelectC.setImageResource(R.drawable.circle_none);
                    binding.btnSelectC1.setImageResource(R.drawable.circle_none);
                    binding.btnSelectD.setImageResource(R.drawable.circle_check);
                    binding.btnSelectD1.setImageResource(R.drawable.circle_check);
                    break;
            }
        }
    }

    public interface QuestionListeners {
        void onClick(String select, String prevSelect);
    }
}
