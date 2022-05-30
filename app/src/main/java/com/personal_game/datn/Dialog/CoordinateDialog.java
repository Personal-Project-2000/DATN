package com.personal_game.datn.Dialog;

import static com.personal_game.datn.Backup.Constant.SwitchCostumeActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.recyclerview.widget.GridLayoutManager;

import com.personal_game.datn.Activity.CoordinateActivity;
import com.personal_game.datn.Adapter.SizeAdapter;
import com.personal_game.datn.Backup.Constant;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Coordinate;
import com.personal_game.datn.Models.CostumeCoordinate;
import com.personal_game.datn.Models.Size;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.LayoutCoordinateBinding;
import com.personal_game.datn.databinding.LayoutSizeBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CoordinateDialog extends Dialog {
    public Activity c;
    private LayoutCoordinateBinding layout;
    private final Shared_Preferences shared_preferences;
    private SizeAdapter sizeAdapter;
    private Coordinate coordinate;
    private CoordinateDialogListeners coordinateDialogListeners;
    private int position;

    private String shirtId = null;
    private String shoeId = null;
    private String hatId = null;
    private String trouserId = null;
    private String bagId = null;

    public CoordinateDialog(Activity a, Coordinate coordinate, CoordinateDialogListeners coordinateDialogListeners, int position) {
        super(a);
        this.c = a;
        shared_preferences = new Shared_Preferences(c);
        this.coordinate = coordinate;
        this.coordinateDialogListeners = coordinateDialogListeners;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        layout = layout.inflate(getLayoutInflater());
        View view = layout.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        setData();
        setListeners();
    }

    private void setListeners(){
        layout.imgShirt.setOnClickListener(v -> {
            switchCoordinateActivity(shirtId, Constant.shirtId);
        });

        layout.imgTrouser.setOnClickListener(v -> {
            switchCoordinateActivity(trouserId, Constant.trousersId);
        });

        layout.imgBag.setOnClickListener(v -> {
            switchCoordinateActivity(bagId, Constant.bagId);
        });

        layout.imgHat.setOnClickListener(v -> {
            switchCoordinateActivity(hatId, Constant.hatId);
        });

        layout.imgShoe.setOnClickListener(v -> {
            switchCoordinateActivity(shoeId, Constant.shoeId);
        });

        layout.layoutSetting.setOnClickListener(v -> {
            switchCoordinateActivity(null, Constant.shirtId);
        });
    }

    private void switchCoordinateActivity(String costumeId, String styleId){
        if(costumeId != null){
            SwitchCostumeActivity(c, costumeId);
        }else{
            Intent intent = new Intent(c, CoordinateActivity.class);
            intent.putExtra("coordinate", coordinate);
            intent.putExtra("styleId", styleId);
            intent.putExtra("position", position);
            c.startActivityForResult(intent, 10);
        }
        dismiss();
    }

    private void setData(){
        if(coordinate != null) {
            for (CostumeCoordinate costumeCoordinate : coordinate.getCostumes()) {
                switch (costumeCoordinate.getStyleId()) {
                    case Constant.bagId: {
                        bagId = costumeCoordinate.getCostumeId();

                        Picasso.Builder builder = new Picasso.Builder(c);
                        builder.listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                layout.imgBag.setImageResource(R.drawable.cong);
                            }
                        });
                        Picasso pic = builder.build();
                        pic.load(costumeCoordinate.getImage()).into(layout.imgBag);
                    }
                    break;
                    case Constant.hatId: {
                        hatId = costumeCoordinate.getCostumeId();

                        Picasso.Builder builder = new Picasso.Builder(c);
                        builder.listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                layout.imgHat.setImageResource(R.drawable.cong);
                            }
                        });
                        Picasso pic = builder.build();
                        pic.load(costumeCoordinate.getImage()).into(layout.imgHat);
                    }
                    break;
                    case Constant.shirtId: {
                        shirtId = costumeCoordinate.getCostumeId();

                        Picasso.Builder builder = new Picasso.Builder(c);
                        builder.listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                layout.imgShirt.setImageResource(R.drawable.cong);
                            }
                        });
                        Picasso pic = builder.build();
                        pic.load(costumeCoordinate.getImage()).into(layout.imgShirt);
                    }
                    break;
                    case Constant.trousersId: {
                        trouserId = costumeCoordinate.getCostumeId();

                        Picasso.Builder builder = new Picasso.Builder(c);
                        builder.listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                layout.imgTrouser.setImageResource(R.drawable.cong);
                            }
                        });
                        Picasso pic = builder.build();
                        pic.load(costumeCoordinate.getImage()).into(layout.imgTrouser);
                    }
                    break;
                    case Constant.shoeId: {
                        shoeId = costumeCoordinate.getCostumeId();

                        Picasso.Builder builder = new Picasso.Builder(c);
                        builder.listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                layout.imgShoe.setImageResource(R.drawable.cong);
                            }
                        });
                        Picasso pic = builder.build();
                        pic.load(costumeCoordinate.getImage()).into(layout.imgShoe);
                    }
                    break;
                }
            }
        }
    }

    public interface CoordinateDialogListeners{
        void onClick();
    }
}
