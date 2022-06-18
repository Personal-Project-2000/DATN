package com.personal_game.datn.Dialog;

import static com.personal_game.datn.Backup.Constant.SwitchCostumeActivity;
import static com.personal_game.datn.ultilities.ConvertMoney.intConvertMoney;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.personal_game.datn.Activity.CoordinateActivity;
import com.personal_game.datn.Adapter.CoordinateAdapter;
import com.personal_game.datn.Adapter.SizeAdapter;
import com.personal_game.datn.Backup.Constant;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Coordinate;
import com.personal_game.datn.Models.CostumeCoordinate;
import com.personal_game.datn.Models.Size;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.LayoutCoordinateBinding;
import com.personal_game.datn.databinding.LayoutSizeBinding;
import com.personal_game.datn.ultilities.RangeTime;
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
    private int total = 0;

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

    private void setView(ImageView imgMain, TextView txtPrice, LinearLayout layoutEvent, TextView txtValueEvent, ImageView iconEvent, CostumeCoordinate costumeCoordinate){
        txtPrice.setVisibility(View.VISIBLE);
        Picasso.Builder builder = new Picasso.Builder(c);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                imgMain.setImageResource(R.drawable.cong);
            }
        });
        Picasso pic = builder.build();
        pic.load(costumeCoordinate.getImage()).into(imgMain);

        if(costumeCoordinate.getCostumeInfo().getPromotion() != null) {
            long millisFutureStartTime = RangeTime.getBetweenDayToNow(costumeCoordinate.getCostumeInfo().getPromotion().getStartTime());

            if (millisFutureStartTime <= 0) {
                long millisFutureEndTime = RangeTime.getBetweenDayToNow(costumeCoordinate.getCostumeInfo().getPromotion().getEndTime());

                if (millisFutureEndTime > 0) {
                    total += costumeCoordinate.getCostumeInfo().getPrice() * (100 - costumeCoordinate.getCostumeInfo().getPromotion().getValue()) / 100;

                    txtPrice.setText(intConvertMoney(costumeCoordinate.getCostumeInfo().getPrice() * (100 - costumeCoordinate.getCostumeInfo().getPromotion().getValue()) / 100));
                    layoutEvent.setVisibility(View.VISIBLE);
                    txtValueEvent.setText("-" + costumeCoordinate.getCostumeInfo().getPromotion().getValue() + "%");

                    Picasso.Builder builder1 = new Picasso.Builder(c);
                    builder1.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            iconEvent.setImageResource(R.drawable.ic_baseline_flash_on_24);
                        }
                    });

                    Picasso pic1 = builder1.build();
                    pic1.load(costumeCoordinate.getCostumeInfo().getPromotion().getIcon()).into(iconEvent);

                    return;
                }
            }
        }

        total += costumeCoordinate.getCostumeInfo().getPrice();

        txtPrice.setText(intConvertMoney(costumeCoordinate.getCostumeInfo().getPrice()));
    }

    private void setData(){
        if(coordinate != null) {
            for (CostumeCoordinate costumeCoordinate : coordinate.getCostumes()) {
                switch (costumeCoordinate.getStyleId()) {
                    case Constant.bagId: {
                        bagId = costumeCoordinate.getCostumeId();

                        setView(layout.imgBag, layout.txtPriceBag, layout.layoutEventBag, layout.txtValueEventBag, layout.imgEventBag, costumeCoordinate);
                    }
                    break;
                    case Constant.hatId: {
                        hatId = costumeCoordinate.getCostumeId();

                        setView(layout.imgHat, layout.txtPriceHat, layout.layoutEventHat, layout.txtValueEventHat, layout.imgEventHat, costumeCoordinate);
                    }
                    break;
                    case Constant.shirtId: {
                        shirtId = costumeCoordinate.getCostumeId();

                        setView(layout.imgShirt, layout.txtPriceShirt, layout.layoutEventShirt, layout.txtValueEventShirt, layout.imgEventShirt, costumeCoordinate);
                    }
                    break;
                    case Constant.trousersId: case Constant.skirtId:{
                        trouserId = costumeCoordinate.getCostumeId();

                        setView(layout.imgTrouser, layout.txtPriceTrouser, layout.layoutEventTrouser, layout.txtValueEventTrouser, layout.imgEventTrouser, costumeCoordinate);
                    }
                    break;
                    case Constant.shoeId: {
                        shoeId = costumeCoordinate.getCostumeId();

                        setView(layout.imgShoe, layout.txtPriceShoe, layout.layoutEventShoe, layout.txtValueEventShoe, layout.imgEventShoe, costumeCoordinate);
                    }
                    break;
                }
            }
        }

        layout.txtPrice.setText(intConvertMoney(total));
    }

    public interface CoordinateDialogListeners{
        void onClick();
    }
}
