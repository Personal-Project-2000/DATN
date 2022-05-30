package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Adapter.CoordinateAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Dialog.CoordinateDialog;
import com.personal_game.datn.Models.Coordinate;
import com.personal_game.datn.Models.Costume;
import com.personal_game.datn.R;
import com.personal_game.datn.Response.Message_Coordinates;
import com.personal_game.datn.databinding.ActivityCoordinateBinding;
import com.personal_game.datn.databinding.ActivityCoordinatesBinding;
import com.personal_game.datn.ultilities.MyDragListener;
import com.personal_game.datn.ultilities.MyTouchListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoordinatesActivity extends AppCompatActivity {

    private ActivityCoordinatesBinding binding;
    private List<Coordinate> coordinates;
    private CoordinateAdapter coordinateAdapter;
    private Shared_Preferences shared_preferences;
    private Costume costume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoordinatesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        Intent intent = getIntent();
        costume = (Costume) intent.getSerializableExtra("costume");

        if(costume != null){
            binding.imgCostume.setVisibility(View.VISIBLE);

            Picasso.Builder builder = new Picasso.Builder(getApplicationContext());
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    binding.imgCostume.setImageResource(R.drawable.cong);
                }
            });
            Picasso pic = builder.build();
            pic.load(costume.getPictures().get(0).getLink()).into(binding.imgCostume);
        }else{
            binding.imgCostume.setVisibility(View.GONE);
            binding.guidelineHorizontal015.setGuidelinePercent(0);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        coordinates = new ArrayList<>();
        shared_preferences = new Shared_Preferences(getApplicationContext());

        getCoordinate();
        setListeners();
    }

    private void setListeners(){
        binding.imgCostume.setOnTouchListener(new MyTouchListener());

        binding.imgCostume.setOnDragListener(new MyDragListener());

        binding.layoutMain1.setOnDragListener((v, e) -> {
            Log.i("checkDrag", e.getAction()+"");

            return true;
        });
    }

    private void setRclCoordinate(){
        coordinateAdapter = new CoordinateAdapter(coordinates, this, new CoordinateAdapter.CoordinateListeners() {
            @Override
            public void onClick(int position, Coordinate coordinate) {
                if(costume == null) {
                    CoordinateDialog dialog = new CoordinateDialog(CoordinatesActivity.this, coordinate, new CoordinateDialog.CoordinateDialogListeners() {
                        @Override
                        public void onClick() {

                        }
                    }, position);

                    dialog.show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), CoordinateActivity.class);
                    intent.putExtra("coordinate", coordinate);
                    intent.putExtra("costume", costume);
                    intent.putExtra("position", position);
                    startActivityForResult(intent, 10);
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclCoordinate.setLayoutManager(gridLayoutManager);
        binding.rclCoordinate.setAdapter(coordinateAdapter);
    }

    private void getCoordinate(){
        Service service = getRetrofit().create(Service.class);
        Call<Message_Coordinates> call = service.GetCoordinates("bearer "+shared_preferences.getToken());
        call.enqueue(new Callback<Message_Coordinates>() {
            @Override
            public void onResponse(Call<Message_Coordinates> call, Response<Message_Coordinates> response) {
                if(response.body().getStatus() == 1) {
                    if(response.body().getCoordinates() == null){
                        coordinates = new ArrayList<>();
                    }else{
                        coordinates = response.body().getCoordinates();
                    }

                    coordinates.add(null);

                    setRclCoordinate();
                }else{
                    Toast.makeText(getApplicationContext(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message_Coordinates> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10){
            Log.i("resultCode", resultCode+"");
            if(resultCode == 11) {
                Coordinate coordinate = (Coordinate) data.getSerializableExtra("coordinate");
                int position = data.getIntExtra("position", -1);

                coordinates.set(position, coordinate);
                coordinateAdapter.notifyItemChanged(position);
            }else if(resultCode == 12){
                Coordinate coordinate = (Coordinate) data.getSerializableExtra("coordinate");
                int position = data.getIntExtra("position", -1);

                coordinates.set(position, coordinate);
                coordinateAdapter.notifyItemInserted(position);

                coordinates.add(null);
                coordinateAdapter.notifyItemInserted(coordinates.size());
            }else if(resultCode == 13){
                int position = data.getIntExtra("position", -1);

                coordinates.remove(position);
                coordinateAdapter.notifyItemRemoved(position);
            }
        }
    }
}