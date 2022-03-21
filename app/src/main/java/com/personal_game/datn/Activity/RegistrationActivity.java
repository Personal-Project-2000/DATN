package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Backup.Constant.token_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Dialog.VerifyOTPDialog;
import com.personal_game.datn.Models.User;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.databinding.ActivityRegistrationBinding;
import com.personal_game.datn.databinding.ActivitySignInBinding;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    private ActivityRegistrationBinding binding;

    public static FirebaseAuth mAuth;
    public static String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FirebaseApp.initializeApp(this);

        init();
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setListeners();
    }

    private void setListeners() {
        binding.btnRegistration.setOnClickListener(v -> {
            if(check()){
                loading(true);
                sendVerificationCode("+84" + binding.inputTK.getText().toString());
            }
        });
    }

    private void loading(boolean Loading) {
        if (Loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.btnRegistration.setVisibility(View.GONE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnRegistration.setVisibility(View.VISIBLE);
        }
    }

    private boolean check() {
        if (binding.inputPass.getText().length() < 8) {
            Toast.makeText(getApplication(), "Mật khẩu ít hơn 8 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!(binding.inputPass.getText() + "").equals(binding.inputPass1.getText() + "")) {
            Toast.makeText(getApplication(), "Nhập lại mật khẩu đã bị sai", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void sendVerificationCode(String number) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(number) // số điện thoại cần xác thực
                .setTimeout(60L, TimeUnit.SECONDS) //thời gian timeout
                .setActivity(this) .setCallbacks(mCallbacks) .build();
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
                Toast.makeText(getApplicationContext(), "Request fail", Toast.LENGTH_SHORT).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                Toast.makeText(getApplicationContext(), "Quota không đủ", Toast.LENGTH_SHORT).show();
            }
            loading(false);
        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            Log.e("onCodeSent", "onCodeSent:" + verificationId);
            Toast.makeText(getApplicationContext(), "Đã gửi OTP", Toast.LENGTH_SHORT).show();
            mVerificationId = verificationId;
            mResendToken = token;
            loading(false);

            VerifyOTPDialog dialog = new VerifyOTPDialog(RegistrationActivity.this, new VerifyOTPDialog.VerifyOTPListeners() {
                @Override
                public void onClick(String code) {
                    verifyCode(code);
                }
            });

            dialog.show();
            dialog.getWindow().setLayout(700, 430);
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
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Confirm", "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            registration(binding.inputFullName.getText()+"",
                                    binding.inputPass.getText()+"",
                                    binding.inputTK.getText()+"");
                        } else {
                            Log.w("Confirm", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                        loading(false);
                    }
                });
    }

    private void registration(String fullName, String pass, String phone) {
        loading(true);

        Service service = getRetrofit().create(Service.class);
        Call<Message> regis = service.Registration(token_client, new User(fullName, pass, phone));
        regis.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                loading(false);
                Toast.makeText(getApplication(), response.body().getNotification(), Toast.LENGTH_SHORT).show();

                if (response.body().getStatus() == 1) {
                    Intent intent = new Intent(getApplication(), SignInActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                loading(false);
                Toast.makeText(getApplication(), "Đăng ký tài khoản thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}