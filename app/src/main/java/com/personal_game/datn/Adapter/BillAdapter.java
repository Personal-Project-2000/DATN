package com.personal_game.datn.Adapter;

import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Response.BillInfo;
import com.personal_game.datn.Response.CostumeBill;
import com.personal_game.datn.databinding.ItemAddressBinding;
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
            List<CostumeBill> costume = bill.getCostumes();
            setImg(binding, costume);

            binding.txtTotal.setText(intConvertMoney(bill.getBill().getTotal()));

            String str = "(Đã thanh toán)";
            if(!bill.getBill().isPayment()){
                str = "(Chưa thanh toán)";
            }
            binding.txtPayment.setText(str);
            binding.txtState.setText(bill.getBillState().getName());
            binding.txtBillId.setText("Đơn hàng: "+bill.getBill().getId());
            binding.txtCreateAt.setText("Ngày tạo: "+bill.getBill().getDate());

            binding.layoutMain.setOnClickListener(v -> {
                billListeners.onClick(bill);
            });
        }
    }

    public interface BillListeners {
        void onClick(BillInfo bill);
    }

    public void setImg(ItemBillBinding binding, List<CostumeBill> costume){
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < 20; i ++){
            temp.add("Sy");
        }

        billImgAdapter = new BillImgAdapter(costume, context);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);

        binding.rclImg.setLayoutManager(gridLayoutManager);
        binding.rclImg.setAdapter(billImgAdapter);
    }
}
