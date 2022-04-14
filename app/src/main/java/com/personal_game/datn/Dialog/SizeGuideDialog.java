package com.personal_game.datn.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.personal_game.datn.Adapter.SizeAdapter;
import com.personal_game.datn.Backup.Constant;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Body;
import com.personal_game.datn.Models.PersonalStyle;
import com.personal_game.datn.Models.Size;
import com.personal_game.datn.Models.TestResult;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.LayoutSizeGuideBinding;
import com.personal_game.datn.databinding.LayoutTestresultBinding;

import java.util.ArrayList;
import java.util.List;

public class SizeGuideDialog extends Dialog {
    public Activity c;
    private LayoutSizeGuideBinding layout;
    private boolean isSex;
    private final Shared_Preferences shared_preferences;
    private SizeGuideListeners sizeGuideListeners;
    private SizeAdapter sizeAdapter;
    private List<Size> sizes;
    private String code;

    public SizeGuideDialog(Activity a, List<Size> sizes, SizeGuideListeners sizeGuideListeners, String code) {
        super(a);
        this.c = a;
        shared_preferences = new Shared_Preferences(c);
        this.sizeGuideListeners = sizeGuideListeners;
        this.sizes = sizes;
        this.code = code;
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
        isSex = true;

        setLayoutShow();
        setRcl();
        setListeners();
    }

    private void handleResult(float height, float weight){
        String result = "";
        for(int i = 0; i < sizes.size(); i ++){
            String[] value = sizes.get(i).getDescription().split(",");
            for(String item: value){
                item = item.replace("(", ":");
                String[] valueItem = item.split(":");

                if(valueItem[1].equals(Constant.unitHeight)){
                    String[] heights = valueItem[0].split("-");

                    if(height >= Float.parseFloat(heights[0]) && height <= Float.parseFloat(heights[1])){
                        result = sizes.get(i).getName();
                    }else{
                        break;
                    }
                }else{
                    String[] weights = valueItem[0].split("-");

                    if(weight >= Float.parseFloat(weights[0])-1.0 && weight <= Float.parseFloat(weights[1])+1.0)
                        result = sizes.get(i).getName();
                    else{
                        Toast.makeText(c, c.getString(R.string.no_right_size), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if(!result.equals(""))
            {
                sizeGuideListeners.onClick(result);
                dismiss();
                break;
            }
        }

        if(result.isEmpty()){
            Toast.makeText(c, c.getString(R.string.no_right_size), Toast.LENGTH_SHORT).show();
        }

        layout.txtResult.setText(result);
    }

    private void setLayoutShow(){
        switch (code){
            case Constant.shirtId:{
                layout.layoutShirt.setVisibility(View.VISIBLE);
            }break;
            case Constant.shoeId:{
                layout.layoutShoe.setVisibility(View.VISIBLE);
            }
        }
    }

    private void changeSex(){
        if(isSex){
            layout.txtSelectSex.setText("Giới tính: Nữ");
        }else{
            layout.txtSelectSex.setText("Giới tính: Nam");
        }
        isSex = !isSex;
    }

    private void setListeners(){
        layout.layoutSex.setOnClickListener(v -> {
            changeSex();
        });

        layout.btnResult.setOnClickListener(v -> {
            setResult();
        });

        layout.btnResultShoe.setOnClickListener(v -> {
            setResultShoe();
        });
    }

    private void setRcl(){
        sizeAdapter = new SizeAdapter(sizes, c, new SizeAdapter.SizeListeners() {
            @Override
            public void onClick(Size size, int position) {
            }
        }, 2);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(c, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        layout.rclSize.setLayoutManager(gridLayoutManager);
        layout.rclSize.setAdapter(sizeAdapter);
    }

    private void setResult(){
        float weight = (float)0.0;
        float height = (float)0.0;
        if(!(layout.inputWeight.getText() + "").isEmpty())
            weight = Float.parseFloat((layout.inputWeight.getText() + "").replace(",", "."));
        if(!(layout.inputHeight.getText() + "").isEmpty())
            height = Float.parseFloat((layout.inputHeight.getText() + "").replace(",", "."));

        handleResult(height, weight);
    }

    private void setResultShoe(){
        String result = "";
        float height = (float)0.0;
        if(!(layout.inputHeightShoe.getText() + "").isEmpty())
            height = Float.parseFloat((layout.inputHeightShoe.getText() + "").replace(",", "."));

        handleResult(height, 0);
    }

    public interface SizeGuideListeners{
        void onClick(String result);
    }
}
