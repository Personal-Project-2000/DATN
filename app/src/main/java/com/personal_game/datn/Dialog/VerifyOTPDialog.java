package com.personal_game.datn.Dialog;

import static com.personal_game.datn.Activity.RegistrationActivity.mAuth;
import static com.personal_game.datn.Activity.RegistrationActivity.mVerificationId;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.personal_game.datn.R;
import com.personal_game.datn.databinding.LayoutOtpBinding;
import com.personal_game.datn.ultilities.CountDown;
import com.personal_game.datn.ultilities.GenericKeyEvent;
import com.personal_game.datn.ultilities.GenericTextWatcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VerifyOTPDialog extends Dialog {
    public Activity c;
    private LayoutOtpBinding layout;
    private VerifyOTPListeners verifyOTPListeners;

    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String phone;

    private CountDownTimer countDownTimer;

    public VerifyOTPDialog(Activity a, VerifyOTPListeners verifyOTPListeners, String phone) {
        super(a);
        this.c = a;
        this.verifyOTPListeners = verifyOTPListeners;
        this.phone = phone;
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
        createCountDown();

        setListeners();
    }

    private void createCountDown(){
        long countDown = 2*60*1000 - 1000;
        countDownTimer = new CountDownTimer(countDown, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                layout.txtNotiOTP.setText(c.getString(R.string.count_down_OTP)+" "+ CountDown.countDownTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                layout.textviewReSendOTP.setVisibility(View.VISIBLE);
                layout.txtNotiOTP.setText("Mã đã hết hạn");
            }
        }.start();
    }

    private void loading(boolean value){
        if(value){
            layout.buttonVerifyOTP.setVisibility(View.GONE);
            layout.progressBar.setVisibility(View.VISIBLE);
        }else{
            layout.buttonVerifyOTP.setVisibility(View.VISIBLE);
            layout.progressBar.setVisibility(View.GONE);
        }
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
                loading(true);
                verifyCode(code);
            }else{
                Toast.makeText(c, "Vui lòng nhập đúng mã", Toast.LENGTH_SHORT).show();
            }
        });
        
        layout.textviewReSendOTP.setOnClickListener(v -> {
           sendVerificationCode(phone);
        });
    }

    private void sendVerificationCode(String number) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(number) // số điện thoại cần xác thực
                .setTimeout(120L, TimeUnit.SECONDS) //thời gian timeout
                .setActivity(c) .setCallbacks(mCallbacks) .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback xác thực sđt private
    PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            //Hàm này được gọi trong hai trường hợp:
            // 1. Trong một số trường hợp, điện thoại di động được xác minh tự động mà không cần mã xác minh.
            // 2. Trên một số thiết bị, các dịch vụ của Google Play phát hiện SMS đến và thực hiện quy trình xác minh mà không cần người dùng thực hiện bất kỳ hành động nào.
            Log.e("onVerificationCompleted", "onVerificationCompleted:" + credential);
            //verifyCode(credential.getSmsCode());
        }

        //fail
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("onVerificationFailed", "onVerificationFailed", e);
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(c, "Không thể gửi OTP", Toast.LENGTH_SHORT).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                Toast.makeText(c, "Quota không đủ", Toast.LENGTH_SHORT).show();
            }
            loading(false);
        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            Log.e("onCodeSent", "onCodeSent:" + verificationId);
            Toast.makeText(c, "Đã gửi OTP", Toast.LENGTH_SHORT).show();
            mVerificationId = verificationId;
            mResendToken = token;

            layout.textviewReSendOTP.setVisibility(View.GONE);

            if(countDownTimer != null)
                countDownTimer.cancel();

            createCountDown();
        }
    };

    //code xác thực OTP
    private void verifyCode(String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(c, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Confirm", "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            if(countDownTimer != null)
                                countDownTimer.cancel();
                            verifyOTPListeners.onClick("success");
                            dismiss();
                        } else {
                            Log.w("Confirm", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            }
                            Toast.makeText(c, "Bạn đã nhập sai OTP", Toast.LENGTH_SHORT).show();
                            loading(false);
                        }
                    }
                });
    }

    public interface VerifyOTPListeners{
        void onClick(String code);
    }
}
