package com.personal_game.datn.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.recyclerview.widget.GridLayoutManager;

import com.personal_game.datn.Adapter.SizeAdapter;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.Size;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.LayoutSizeBinding;
import com.personal_game.datn.databinding.LayoutSizeGuideBinding;

import java.util.ArrayList;
import java.util.List;

public class SizeDialog extends Dialog {
    public Activity c;
    private LayoutSizeBinding layout;
    private final Shared_Preferences shared_preferences;
    private SizeAdapter sizeAdapter;
    private List<Size> sizeList = new ArrayList<>();
    private SizeDialogListeners sizeDialogListeners;

    public SizeDialog(Activity a, List<Size> sizeList, SizeDialogListeners sizeDialogListeners) {
        super(a);
        this.c = a;
        shared_preferences = new Shared_Preferences(c);
        this.sizeList = sizeList;
        this.sizeDialogListeners = sizeDialogListeners;
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
        setRcl();
        setListeners();
    }

    private void setListeners(){

    }

    private void setRcl(){
        sizeAdapter = new SizeAdapter(sizeList, c, new SizeAdapter.SizeListeners() {
            @Override
            public void onClick(Size size, int position) {
                sizeDialogListeners.onClick(size);
                dismiss();
            }
        }, 2);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(c, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        layout.rclSize.setLayoutManager(gridLayoutManager);
        layout.rclSize.setAdapter(sizeAdapter);
    }

    public interface SizeDialogListeners{
        void onClick(Size size);
    }
}
