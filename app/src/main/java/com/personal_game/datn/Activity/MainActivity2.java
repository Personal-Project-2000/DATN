package com.personal_game.datn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.personal_game.datn.R;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Creating a view for the imageView
        final View txt1 =findViewById(R.id.imageView);
        final View txt2 =findViewById(R.id.image);
        final View myImage =findViewById(R.id.image1);
        final Button btn =findViewById(R.id.btn);

//        TranslateAnimation animation = new TranslateAnimation(txt1.getX(),txt2.getX(),
//                txt1.getY(),txt2.getY());
//
//        animation.setDuration(200);
//
//        animation.start();

        btn.setOnClickListener(v -> {
            AnimatorSet animSetXY = new AnimatorSet();
            myImage.setY(txt2.getY());
            myImage.setX(txt2.getX());
            ObjectAnimator y = ObjectAnimator.ofFloat(myImage, "translationY", txt1.getY()-txt2.getY());
            ObjectAnimator x = ObjectAnimator.ofFloat(myImage, "translationX", txt2.getLeft(), txt1.getLeft());
            ObjectAnimator sy = ObjectAnimator.ofFloat(myImage, "scaleY", 1.5f, 0.1f);
            ObjectAnimator sx = ObjectAnimator.ofFloat(myImage, "scaleX", 1.5f, 0.1f);
            animSetXY.playTogether(x, y, sx, sy);
            animSetXY.setDuration(1000);
            animSetXY.start();
        });
    }
}