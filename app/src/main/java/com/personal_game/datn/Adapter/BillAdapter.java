package com.personal_game.datn.Adapter;

import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;
import static com.personal_game.datn.ultilities.ConvertMoney.longConvertMoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Models.BillDetail;
import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.Models.Promotion;
import com.personal_game.datn.Response.BillInfo;
import com.personal_game.datn.databinding.ItemBillBinding;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{
    private final List<BillInfo> billList;
    private final Context context;
    private final BillListeners billListeners;
    private BillImgAdapter billImgAdapter;

    public BillAdapter(List<BillInfo> billList, Context context, BillListeners billListeners){
        this.billList = billList;
        this.context = context;
        this.billListeners = billListeners;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBillBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(billList.get(position));
    }

    @Override
    public int getItemCount() {
        return billList != null ? billList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemBillBinding binding;

        public ViewHolder(@NonNull ItemBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(BillInfo bill) {
            List<BillDetail> costume = bill.getBill().getBillDetails();
            setImg(binding, costume);

            //binding.txtTotal.setText(intConvertMoney(bill.getBill().getTotal()));

            setMoney(bill.getBill().getTotal(), bill.getBill().getPromotion(), bill.getBill().getFee());

            String str = "(???? thanh to??n)";
            if(!bill.getBill().isPayment()){
                str = "(Ch??a thanh to??n)";
            }
            binding.txtPayment.setText(str);
            binding.txtState.setText(bill.getBillState().getName());
            binding.txtBillId.setText("????n h??ng: "+bill.getBill().getId());
            binding.txtCreateAt.setText("Ng??y t???o: "+bill.getBill().getDate());

            binding.layoutMain.setOnClickListener(v -> {
                billListeners.onClick(bill);
            });
        }

        private void setMoney(int total, Promotion promotion, int fee){
            if(promotion != null) {

                int pricePromotion = total * (promotion.getValue()) / 100;

                binding.txtTotal.setText(longConvertMoney(total - pricePromotion + fee));
            }else{
                binding.txtTotal.setText(intConvertMoney(total + fee));
            }
        }
    }

    public interface BillListeners {
        void onClick(BillInfo bill);
    }

    public void setImg(ItemBillBinding binding, List<BillDetail> costume){
        billImgAdapter = new BillImgAdapter(costume, context);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclImg.setLayoutManager(gridLayoutManager);
        binding.rclImg.setAdapter(billImgAdapter);
    }
}
