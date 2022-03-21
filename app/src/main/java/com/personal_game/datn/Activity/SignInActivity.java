package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;
import static com.personal_game.datn.Backup.Constant.token_client;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Models.User;
import com.personal_game.datn.Response.Message;
import com.personal_game.datn.Response.Message_Login;
import com.personal_game.datn.databinding.ActivitySignInBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setAccount();
        setListeners();
    }

    private boolean check(){
        if(binding.inputTK.getText() == null || binding.inputPass.getText() == null){
            Toast.makeText(getApplication(), "vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }

    private void loading(boolean Loading) {
        if (Loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.btnSignIn.setVisibility(View.GONE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.btnSignIn.setVisibility(View.VISIBLE);
        }
    }

    private void setListeners(){
        binding.checkSignIn.setOnClickListener(v -> {
            saveAccount();
        });

        binding.btnSignIn.setOnClickListener(v -> {
            if(check()) {
                saveAccount();
                SignIn(binding.inputTK.getText() + "",
                        binding.inputPass.getText() + "");
            }
        });

        binding.btnRegistration.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), RegistrationActivity.class);
            startActivity(intent);
        });
    }

    private void saveAccount(){
        Shared_Preferences shared_preferences = new Shared_Preferences(getApplication());
        if(binding.checkSignIn.isChecked()){
            shared_preferences.saveAccount(binding.inputTK.getText()+"", binding.inputPass.getText()+"");
        }else{
            shared_preferences.saveAccount("", "");
        }
    }

    private void setAccount(){
        Shared_Preferences shared_preferences = new Shared_Preferences(getApplication());
        if(!shared_preferences.getAccount().equals("")){
            binding.checkSignIn.setChecked(true);
            binding.inputTK.setText(shared_preferences.getAccount());
            binding.inputPass.setText(shared_preferences.getPass());
        }
    }

    private void SignIn(String phone, String pass){
        loading(true);

        Service service = getRetrofit().create(Service.class);
        Call<Message_Login> login = service.Login(token_client, phone, pass);
        login.enqueue(new Callback<Message_Login>() {
            @Override
            public void onResponse(Call<Message_Login> call, Response<Message_Login> response) {
                loading(false);
                String notification = response.body().getNotification();

                if(response.body().getStatus() == 1){
                    Shared_Preferences shared_preferences = new Shared_Preferences(getApplicationContext());
                    shared_preferences.saveToken(response.body().getData().getToken());

                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.putExtra("info", response.body().getData());
                    startActivity(intent);

                    notification = "Đăng nhập thành công";
                }

                Toast.makeText(getApplication(), notification, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message_Login> call, Throwable t) {
                loading(false);
                Toast.makeText(getApplication(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}