package com.personal_game.datn.ultilities;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MyTouchListener implements View.OnTouchListener{
    private float _xDelta, _yDelta;
    private int mode = 1;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = 1;
                Log.i("action", "down");
                ConstraintLayout.LayoutParams lParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                _xDelta = motionEvent.getRawX() - lParams.leftMargin;
                _yDelta = motionEvent.getRawY() - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                Log.i("action", "up");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = 2;
                Log.i("action", "pointer down");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i("action", "pointer up");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("action", "move - "+motionEvent+"");
                if(mode == 1) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
                            .getLayoutParams();

                    layoutParams.leftMargin = (int) (motionEvent.getRawX() - _xDelta);
                    layoutParams.topMargin = (int) (motionEvent.getRawY() - _yDelta);
                    view.setLayoutParams(layoutParams);
                }else{
                    Matrix matrix = new Matrix();

                    matrix.setScale(1.5f, 1.5f);

                    ImageView imageView = (ImageView) view;
                    imageView.setImageMatrix(matrix);
                }
                break;
        }
        return true;
    }

    private double spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return Math.sqrt(x * x + y * y);
    }
}
