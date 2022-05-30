package com.personal_game.datn.ultilities;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

public class MyDragListener implements View.OnDragListener{
    @Override
    public boolean onDrag(View v, DragEvent event) {
        Log.i("checkDrag", event.getAction()+"");

        switch (event.getAction()) {
            //1
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            //5
            case DragEvent.ACTION_DRAG_ENTERED:
                //v.setBackgroundDrawable(enterShape);
                break;
            //6
            case DragEvent.ACTION_DRAG_EXITED:
                //v.setBackgroundDrawable(normalShape);
                break;
            //3
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
//                View view = (View) event.getLocalState();
//                ViewGroup owner = (ViewGroup) view.getParent();
//                owner.removeView(view);
//                LinearLayout container = (LinearLayout) v;
//                container.addView(view);
//                view.setVisibility(View.VISIBLE);
                break;
            //4
            case DragEvent.ACTION_DRAG_ENDED:
                //v.setBackgroundDrawable(normalShape);
            default:
                break;
        }
        return true;
    }
}
