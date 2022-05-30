package com.personal_game.datn.ultilities;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MyTouchListener implements View.OnTouchListener{
    private float _xDelta, _yDelta;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                ConstraintLayout.LayoutParams lParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                _xDelta = motionEvent.getRawX() - lParams.leftMargin;
                _yDelta = motionEvent.getRawY() - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
                        .getLayoutParams();

                layoutParams.leftMargin = (int)(motionEvent.getRawX() - _xDelta);
                layoutParams.topMargin = (int)(motionEvent.getRawY() - _yDelta);
                view.setLayoutParams(layoutParams);
                break;
        }
        return true;
    }
}
