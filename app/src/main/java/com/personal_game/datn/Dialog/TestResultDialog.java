package com.personal_game.datn.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.personal_game.datn.Models.TestResult;
import com.personal_game.datn.databinding.LayoutTestresultBinding;

public class TestResultDialog extends Dialog {
    public Activity c;
    private LayoutTestresultBinding layout;
    private TestResultListeners testResultListeners;
    private TestResult result;

    public TestResultDialog(Activity a, TestResultListeners testResultListeners, TestResult result) {
        super(a);
        this.c = a;
        this.testResultListeners = testResultListeners;
        this.result = result;
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
        layout.txtTitle.setText(result.getDescription());
    }

    public interface TestResultListeners{
        void onClick(String code);
    }
}
