package com.personal_game.datn.Adapter;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Activity.DeliveryAddressActivity;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Address;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.databinding.ItemAddressBinding;
import com.personal_game.datn.databinding.ItemNotiBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{
    private final List<Address> addressList;
    private final Context context;
    private AddressListeners addressListeners;

    private Shared_Preferences shared_preferences;

    public AddressAdapter(List<Address> addressList, Context context, AddressListeners addressListeners){
        this.addressList = addressList;
        this.context = context;
        this.shared_preferences = new Shared_Preferences(context);
        this.addressListeners = addressListeners;
    }


    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressAdapter.ViewHolder(ItemAddressBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(addressList.get(position));
    }

    @Override
    public int getItemCount() {
        return addressList != null ? addressList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemAddressBinding binding;
        boolean checkDefault = false;

        public ViewHolder(@NonNull ItemAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Address address) {
            binding.txtName.setText(address.getName());
            binding.txtPhone.setText(address.getPhone());
            binding.txtAddress.setText(address.getStreet());
            binding.txtAddress1.setText(address.getWard()+" - "+address.getDistrict()+" - "+address.getCity());
            checkDefault = address.getDefault();
            if(checkDefault){
                binding.btnDefault.setImageResource(R.drawable.circle_check);
            }else{
                binding.btnDefault.setImageResource(R.drawable.circle_none);
            }

            binding.layoutEdit.setOnClickListener(v -> {
                Intent intent = new Intent(context, DeliveryAddressActivity.class);
                intent.putExtra("address", address);
                context.startActivity(intent);
            });

            binding.btnDefault.setOnClickListener(v -> {
                Address address1 = new Address(address.getId(),
                        address.getName(),
                        address.getWard(),
                        address.getDistrict(),
                        address.getCity(),
                        address.getStreet(),
                        address.getCityId(),
                        address.getDistrictId(),
                        address.getWardId(),
                        address.getPhone(),
                        !checkDefault);

                addressListeners.onClickDefault(getAdapterPosition(), address1);
            });
        }
    }

    public interface AddressListeners{
        void onClickDefault(int position, Address updateAddress);
    }
}
