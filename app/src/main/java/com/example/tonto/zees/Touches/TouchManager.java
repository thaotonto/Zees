package com.example.tonto.zees.Touches;

import android.view.MotionEvent;

/**
 * Created by Inpriron on 4/11/2017.
 */

public class TouchManager {
    public static Touch toTouch(MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();
//        ArrayList<Touch> touches = new ArrayList<>();
        Touch touch;


            int pointerIndex = motionEvent.getActionIndex();
            float pointerX = motionEvent.getX(pointerIndex);
            float pointerY = motionEvent.getY(pointerIndex);
            int pointerId = motionEvent.getPointerId(pointerIndex);
            touch=new Touch(pointerX, pointerY, pointerId, action);

        return touch;
    }
}
