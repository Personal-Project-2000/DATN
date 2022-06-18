package com.personal_game.datn.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.databinding.LayoutConfirmBinding;

public class ConfirmDialog extends Dialog {
    public Activity c;
    private LayoutConfirmBinding layout;
    private final Shared_Preferences shared_preferences;
    private String title;
    private ConfirmDialogListeners confirmDialogListeners;

    public ConfirmDialog(Activity a, String title, ConfirmDialogListeners confirmDialogListeners) {
        super(a);
        this.c = a;
        shared_preferences = new Shared_Preferences(c);
        this.title = title;
        this.confirmDialogListeners = confirmDialogListeners;
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
        layout.txtTitle.setText(title);

        setListeners();
    }

    private void setListeners(){
        layout.btnYes.setOnClickListener(v -> {
            dismiss();
            confirmDialogListeners.onClickYes();
        });

        layout.btnNo.setOnClickListener(v -> {
            dismiss();
            confirmDialogListeners.onClickNo();
        });
    }

    public interface ConfirmDialogListeners{
        void onClickNo();
        void onClickYes();
    }
}
