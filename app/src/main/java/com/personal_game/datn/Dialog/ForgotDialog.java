package com.personal_game.datn.Dialog;

import static com.personal_game.datn.Activity.RegistrationActivity.mAuth;
import static com.personal_game.datn.Activity.RegistrationActivity.mVerificationId;
import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Backup.Constant.token_client;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.personal_game.datn.Activity.MainActivity;
import com.personal_game.datn.Activity.RegistrationActivity;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.TestResult;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Login;
import com.personal_game.datn.databinding.LayoutForgetpassBinding;
import com.personal_game.datn.databinding.LayoutTestresultBinding;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotDialog extends Dialog {
    public Activity c;
    private LayoutForgetpassBinding layout;
    private ForgotListeners forgotListeners;

    private PhoneAuthProvider.ForceResendingToken mResendToken;

    public ForgotDialog(Activity a, ForgotListeners forgotListeners) {
        super(a);
        this.c = a;
        this.forgotListeners = forgotListeners;
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
        mAuth = FirebaseAuth.getInstance();

        setListeners();
    }

    private void loading(boolean value){
        if(value){
            layout.btnUpdatePass.setVisibility(View.GONE);
            layout.progressBar.setVisibility(View.VISIBLE);
        }else{
            layout.btnUpdatePass.setVisibility(View.VISIBLE);
            layout.progressBar.setVisibility(View.GONE);
        }
    }

    private void setListeners(){
        layout.btnUpdatePass.setOnClickListener(v -> {
            loading(true);

            sendVerificationCode("+84" + layout.inputTK.getText().toString());
        });
    }

    private void forgot(String phone, String pass){
        loading(true);

        Service service = getRetrofit().create(Service.class);
        Call<Message> call = service.ForgetPass(token_client, phone, pass);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Toast.makeText(getContext(), response.body().getNotification(), Toast.LENGTH_SHORT).show();
                if(response.body().getStatus() == 1){
                    dismiss();
                }
                loading(false);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getContext(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                loading(false);
            }
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
            VerifyOTPDialog dialog = new VerifyOTPDialog(c, new VerifyOTPDialog.VerifyOTPListeners() {
                @Override
                public void onClick(String code) {
                    loading(true);
                    forgot(layout.inputTK.getText()+"",
                            layout.inputPass.getText()+"");
                }
            },"+84" + layout.inputTK.getText().toString());

            dialog.show();
            dialog.getWindow().setLayout(700, 450);
            dialog.setCancelable(false);
        }
    };

    public interface ForgotListeners{
        void onClick(String code);
    }
}
