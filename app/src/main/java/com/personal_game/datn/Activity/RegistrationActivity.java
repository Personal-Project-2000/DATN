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
            Toast.makeText(getApplication(), "M???t kh???u ??t h??n 8 k?? t???", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!(binding.inputPass.getText() + "").equals(binding.inputPass1.getText() + "")) {
            Toast.makeText(getApplication(), "Nh???p l???i m???t kh???u ???? b??? sai", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void sendVerificationCode(String number) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(number) // s??? ??i???n tho???i c???n x??c th???c
                .setTimeout(60L, TimeUnit.SECONDS) //th???i gian timeout
                .setActivity(this) .setCallbacks(mCallbacks) .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback x??c th???c s??t private
    PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            //H??m n??y ???????c g???i trong hai tr?????ng h???p:
            // 1. Trong m???t s??? tr?????ng h???p, ??i???n tho???i di ?????ng ???????c x??c minh t??? ?????ng m?? kh??ng c???n m?? x??c minh.
            // 2. Tr??n m???t s??? thi???t b???, c??c d???ch v??? c???a Google Play ph??t hi???n SMS ?????n v?? th???c hi???n quy tr??nh x??c minh m?? kh??ng c???n ng?????i d??ng th???c hi???n b???t k??? h??nh ?????ng n??o.
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
                Toast.makeText(getApplicationContext(), "Quota kh??ng ?????", Toast.LENGTH_SHORT).show();
            }
            loading(false);
        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            Log.e("onCodeSent", "onCodeSent:" + verificationId);
            Toast.makeText(getApplicationContext(), "???? g???i OTP", Toast.LENGTH_SHORT).show();
            mVerificationId = verificationId;
            mResendToken = token;

            VerifyOTPDialog dialog = new VerifyOTPDialog(RegistrationActivity.this, new VerifyOTPDialog.VerifyOTPListeners() {
                @Override
                public void onClick(String code) {
                    loading(true);
                    registration(binding.inputFullName.getText()+"",
                            binding.inputPass.getText()+"",
                            binding.inputTK.getText()+"");
                }
            }, "+84" + binding.inputTK.getText().toString());

            dialog.show();
            dialog.getWindow().setLayout(700, 430);
        }
    };

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
                Toast.makeText(getApplication(), "????ng k?? t??i kho???n th???t b???i", Toast.LENGTH_SHORT).show();
            }
        });
    }
}