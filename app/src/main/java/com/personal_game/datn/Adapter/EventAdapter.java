package com.personal_game.datn.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.CostumeHome;
import com.personal_game.datn.Response.Event;
import com.personal_game.datn.databinding.ItemEventBinding;
import com.personal_game.datn.ultilities.RangeTime;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    private final Context context;
    private final Shared_Preferences shared_preferences;
    private final List<Event> events;

    public EventAdapter(List<Event> events, Context context){
        this.events = events;
        this.context = context;
        this.shared_preferences = new Shared_Preferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemEventBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemEventBinding binding;
        private CostumeAdapter costumeAdapter;
        private CountDownTimer countDownTimer;

        public ViewHolder(@NonNull ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Event event) {
            //xóa countDown củ, trách nhiệm chạy ngoài kiểm soát
            if(countDownTimer != null)
                countDownTimer.cancel();

            if(event.getPromotion() != null) {
                if(event.getPromotion().getColor() != null)
                    if(!event.getPromotion().getColor().equals("")){
                        int[] colors = {Color.parseColor(event.getPromotion().getColor()), Color.parseColor("#ffffff")};
                        GradientDrawable gradientDrawable = new GradientDrawable();
                        gradientDrawable.setColors(colors);
                        //gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                        binding.layoutEvent.setBackground(gradientDrawable);
                    }

                long millislnFutureStartTime = RangeTime.getBetweenDayToNow(event.getPromotion().getStartTime());

                //kiểm tra đã bắt đầu sự kiện chưa: bé hơn 0 là đã bắt đầu
                if(millislnFutureStartTime < 0) {
                    long millislnFutureEndTime = RangeTime.getBetweenDayToNow(event.getPromotion().getEndTime());

                    if(day(millislnFutureEndTime) > 1) {
                        binding.txtCountDown.setText(context.getString(R.string.end_event)+" " + countDownTime(millislnFutureEndTime));

                    }else{
                        countDownTimer = new CountDownTimer(millislnFutureEndTime, 1000) {

                            public void onTick(long millisUntilFinished) {
                                binding.txtCountDown.setText("Kết thúc sau: " + countDownTime(millisUntilFinished));
                            }

                            public void onFinish() {
                                binding.txtCountDown.setText("Sự kiện đã kết thúc");
                            }
                        }.start();
                    }

                    setRclCostume(event.getCostumes());
                }else{
                    countDownTimer = new CountDownTimer(millislnFutureStartTime, 1000) {

                        public void onTick(long millisUntilFinished) {
                            binding.txtCountDown.setText("Bắt đầu sau: "+countDownTime(millisUntilFinished));
                        }

                        public void onFinish() {
                            countDownTimer.cancel();

                            long millislnFutureEndTime = RangeTime.getBetweenDayToNow(event.getPromotion().getEndTime());

                            countDownTimer = new CountDownTimer(millislnFutureEndTime, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    binding.txtCountDown.setText("Kết thúc sau: "+countDownTime(millisUntilFinished));
                                }

                                public void onFinish() {
                                    binding.txtCountDown.setText("Sự kiện đã kết thúc");
                                }
                            }.start();

                            setRclCostume(event.getCostumes());
                        }
                    }.start();
                }

                binding.txtName.setText(event.getPromotion().getName());
            }
        }

        private void setRclCostume(List<CostumeHome> costumes){
            costumeAdapter = new CostumeAdapter(costumes, context, new CostumeAdapter.CostumeListeners() {
                @Override
                public void onClickFavourite(CostumeHome costume, int position) {
                }
            });

            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
            gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

            binding.rclCostume.setLayoutManager(gridLayoutManager);
            binding.rclCostume.setAdapter(costumeAdapter);
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
}
