package com.personal_game.datn.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.personal_game.datn.Backup.Constant;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.ColorObject;
import com.personal_game.datn.Models.Coordinate;
import com.personal_game.datn.Models.CostumeCoordinate;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.ItemColorBinding;
import com.personal_game.datn.databinding.ItemCoordinateBinding;
import com.personal_game.datn.ultilities.MyDragListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CoordinateAdapter extends RecyclerView.Adapter<CoordinateAdapter.ViewHolder>{
    private final List<Coordinate> coordinates;
    private final Context context;
    private final CoordinateListeners coordinateListeners;

    private Shared_Preferences shared_preferences;

    public CoordinateAdapter(List<Coordinate> coordinates, Context context, CoordinateListeners coordinateListeners){
        this.coordinates = coordinates;
        this.context = context;
        this.shared_preferences = new Shared_Preferences(context);
        this.coordinateListeners = coordinateListeners;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCoordinateBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(coordinates.get(position));
    }

    @Override
    public int getItemCount() {
        return coordinates != null ? coordinates.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ItemCoordinateBinding binding;

        public ViewHolder(@NonNull ItemCoordinateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(Coordinate coordinate) {
            if(coordinate != null) {
                for (CostumeCoordinate costumeCoordinate : coordinate.getCostumes()) {
                    switch (costumeCoordinate.getStyleId()) {
                        case Constant.bagId: {
                            binding.imgBag.setVisibility(View.VISIBLE);

                            Picasso.Builder builder = new Picasso.Builder(context);
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    binding.imgBag.setImageResource(R.drawable.cong);
                                }
                            });
                            Picasso pic = builder.build();
                            pic.load(costumeCoordinate.getImage()).into(binding.imgBag);
                        }
                        break;
                        case Constant.hatId: {
                            binding.imgHat.setVisibility(View.VISIBLE);

                            Picasso.Builder builder = new Picasso.Builder(context);
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    binding.imgHat.setImageResource(R.drawable.cong);
                                }
                            });
                            Picasso pic = builder.build();
                            pic.load(costumeCoordinate.getImage()).into(binding.imgHat);
                        }
                        break;
                        case Constant.shirtId: {
                            binding.imgShirt.setVisibility(View.VISIBLE);

                            Picasso.Builder builder = new Picasso.Builder(context);
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    binding.imgShirt.setImageResource(R.drawable.cong);
                                }
                            });
                            Picasso pic = builder.build();
                            pic.load(costumeCoordinate.getImage()).into(binding.imgShirt);
                        }
                        break;
                        case Constant.trousersId: {
                            binding.imgTrouser.setVisibility(View.VISIBLE);

                            Picasso.Builder builder = new Picasso.Builder(context);
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    binding.imgTrouser.setImageResource(R.drawable.cong);
                                }
                            });
                            Picasso pic = builder.build();
                            pic.load(costumeCoordinate.getImage()).into(binding.imgTrouser);
                        }
                        break;
                        case Constant.shoeId: {
                            binding.imgTrouser.setVisibility(View.VISIBLE);

                            Picasso.Builder builder = new Picasso.Builder(context);
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    binding.imgShoe.setImageResource(R.drawable.cong);
                                }
                            });
                            Picasso pic = builder.build();
                            pic.load(costumeCoordinate.getImage()).into(binding.imgShoe);
                        }
                        break;
                    }
                }
            }else{
                binding.layoutMain.setVisibility(View.GONE);
                binding.imgAdd.setVisibility(View.VISIBLE);
            }

            binding.layoutMainClick.setOnClickListener(v -> {
                coordinateListeners.onClick(getAdapterPosition(), coordinate);
            });

            binding.layoutMainClick.setOnDragListener((v, e) ->{
                Log.i("checkDrag1", e.getAction()+"");
                Toast.makeText(context, "Drag"+e.getAction()+"", Toast.LENGTH_SHORT).show();

                return true;
            });
        }
    }

    public interface CoordinateListeners{
        void onClick(int position, Coordinate coordinate);
    }
}
