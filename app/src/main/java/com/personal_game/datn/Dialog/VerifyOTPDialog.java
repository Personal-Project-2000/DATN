package com.personal_game.datn.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.personal_game.datn.databinding.LayoutOtpBinding;
import com.personal_game.datn.ultilities.GenericKeyEvent;
import com.personal_game.datn.ultilities.GenericTextWatcher;

import java.util.List;

public class VerifyOTPDialog extends Dialog {
    public Activity c;
    private LayoutOtpBinding layout;
    private VerifyOTPListeners verifyOTPListeners;

    public VerifyOTPDialog(Activity a, VerifyOTPListeners verifyOTPListeners) {
        super(a);
        this.c = a;
        this.verifyOTPListeners = verifyOTPListeners;
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
        setListeners();
    }

    private void setListeners(){
        layout.inputCode1.addTextChangedListener(new GenericTextWatcher(layout.inputCode1, layout.inputCode2));
        layout.inputCode2.addTextChangedListener(new GenericTextWatcher(layout.inputCode2, layout.inputCode3));
        layout.inputCode3.addTextChangedListener(new GenericTextWatcher(layout.inputCode3, layout.inputCode4));
        layout.inputCode4.addTextChangedListener(new GenericTextWatcher(layout.inputCode4, layout.inputCode5));
        layout.inputCode5.addTextChangedListener(new GenericTextWatcher(layout.inputCode5, layout.inputCode6));
        layout.inputCode6.addTextChangedListener(new GenericTextWatcher(layout.inputCode6, null));

        layout.inputCode2.setOnKeyListener(new GenericKeyEvent(layout.inputCode2, layout.inputCode1));
        layout.inputCode3.setOnKeyListener(new GenericKeyEvent(layout.inputCode3, layout.inputCode2));
        layout.inputCode4.setOnKeyListener(new GenericKeyEvent(layout.inputCode4, layout.inputCode3));
        layout.inputCode5.setOnKeyListener(new GenericKeyEvent(layout.inputCode5, layout.inputCode4));
        layout.inputCode6.setOnKeyListener(new GenericKeyEvent(layout.inputCode6, layout.inputCode5));

        layout.buttonVerifyOTP.setOnClickListener(v -> {
            String code = layout.inputCode1.getText().toString() +
                    layout.inputCode2.getText().toString() +
                    layout.inputCode3.getText().toString() +
                    layout.inputCode4.getText().toString() +
                    layout.inputCode5.getText().toString() +
                    layout.inputCode6.getText().toString();
            if (code.length() == 6) {
                verifyOTPListeners.onClick(code);
                dismiss();
            }else{
                Toast.makeText(c, "Vui lòng nhập đúng mã", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface VerifyOTPListeners{
        void onClick(String code);
    }
}
