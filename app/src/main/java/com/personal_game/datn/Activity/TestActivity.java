package com.personal_game.datn.Activity;

import static com.personal_game.datn.Api.RetrofitApi.getRetrofit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.personal_game.datn.Adapter.AddressAdapter;
import com.personal_game.datn.Adapter.QuestionAdapter;
import com.personal_game.datn.Adapter.TestAdapter;
import com.personal_game.datn.Api.ServiceApi.Service;
import com.personal_game.datn.Backup.Shared_Preferences;
import com.personal_game.datn.Dialog.TestResultDialog;
import com.personal_game.datn.Dialog.VerifyOTPDialog;
import com.personal_game.datn.Models.Test;
import com.personal_game.datn.Models.TestResult;
import com.personal_game.datn.Response.Message_Suggestion;
import com.personal_game.datn.Response.Message_Test;
import com.personal_game.datn.Response.QuestionInfo;
import com.personal_game.datn.Response.TestInfo;
import com.personal_game.datn.databinding.ActivitySuggestionBinding;
import com.personal_game.datn.databinding.ActivityTestBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {
    private ActivityTestBinding binding;
    private QuestionAdapter questionAdapter;
    private TestAdapter testAdapter;

    private Shared_Preferences shared_preferences;
    private List<TestInfo> tests;
    private TestInfo testInfo;

    private final int layoutTest = 1;
    private final int layoutQuestion = 2;

    private int A = 0;
    private int B = 0;
    private int C = 0;
    private int D = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        shared_preferences = new Shared_Preferences(getApplicationContext());
        testInfo = new TestInfo();

        setListeners();
        getTest();
    }

    private void changeLayout(int layout){
        switch (layout){
            case layoutTest:
                binding.layoutTest.setVisibility(View.VISIBLE);
                binding.layoutQuestion.setVisibility(View.GONE);
                break;
            case layoutQuestion:
                binding.layoutTest.setVisibility(View.GONE);
                binding.layoutQuestion.setVisibility(View.VISIBLE);
                break;
        }
    }

    private int findMax(){
        int max = A;
        int result = 0;

        if(B > max){
            max = B;
            result = 1;
        }

        if(C > max){
            max = C;
            result = 2;
        }

        if(D > max){
            result = 3;
        }

        return result;
    }

    private void setListeners(){
        binding.btnCheck.setOnClickListener(v -> {
            Log.e("result", A +" - "+B+" - "+C+" - "+D);
            TestResultDialog dialog = new TestResultDialog(TestActivity.this, new TestResultDialog.TestResultListeners() {
                @Override
                public void onClick(String code) {

                }
            }, testInfo.getResults().get(findMax()));

            dialog.show();
            dialog.getWindow().setLayout(700, 430);
        });

        binding.btnExitQuestion.setOnClickListener(v -> {
            changeLayout(layoutTest);
        });
    }

    private void setRclTest(){
        testAdapter = new TestAdapter(tests, this, new TestAdapter.TestListeners() {
            @Override
            public void onClick(TestInfo test) {
                testInfo = test;

                binding.txtQuestionTitle.setText(test.getTest().getName());

                setQuestion();
                changeLayout(layoutQuestion);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclTest.setLayoutManager(gridLayoutManager);
        binding.rclTest.setAdapter(testAdapter);
    }

    private void setQuestion(){
        questionAdapter = new QuestionAdapter(testInfo.getQuestions(), this, new QuestionAdapter.QuestionListeners() {
            @Override
            public void onClick(String select, String prevSelect) {
                switch (select){
                    case "A":
                        A ++;
                        break;
                    case "B":
                        B ++;
                        break;
                    case "C":
                        C ++;
                        break;
                    case "D":
                        D ++;
                        break;
                }

                switch (prevSelect){
                    case "A":
                        A --;
                        break;
                    case "B":
                        B --;
                        break;
                    case "C":
                        C --;
                        break;
                    case "D":
                        D --;
                        break;
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rclQuestion.setLayoutManager(gridLayoutManager);
        binding.rclQuestion.setAdapter(questionAdapter);
    }

    private void getTest(){
        Service service = getRetrofit().create(Service.class);
        Call<Message_Test> testCall = service.GetTests("bearer "+shared_preferences.getToken());
        testCall.enqueue(new Callback<Message_Test>() {
            @Override
            public void onResponse(Call<Message_Test> call, Response<Message_Test> response) {
                if(response.body().getStatus() == 1){
                    tests = response.body().getTests();

                    setRclTest();
                }
            }

            @Override
            public void onFailure(Call<Message_Test> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lấy dữ liệu thất", Toast.LENGTH_SHORT).show();
            }
        });
    }
}