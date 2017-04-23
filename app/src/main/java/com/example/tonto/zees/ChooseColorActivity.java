package com.example.tonto.zees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.tonto.zees.Touches.Touch;
import com.example.tonto.zees.Touches.TouchManager;

import java.util.ArrayList;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;

public class ChooseColorActivity extends AppCompatActivity {

    private int color = 1;
    private ArrayList<ImageView> colorList;
    private ImageView back;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_color);
        colorList = new ArrayList<>();
        colorList.add((ImageView) findViewById(R.id.color1));
        colorList.add((ImageView) findViewById(R.id.color2));
        colorList.add((ImageView) findViewById(R.id.color3));
        colorList.add((ImageView) findViewById(R.id.color4));
        colorList.add((ImageView) findViewById(R.id.color5));
        colorList.add((ImageView) findViewById(R.id.color6));
        colorList.add((ImageView) findViewById(R.id.color7));
        back = (ImageView) findViewById(R.id.back1_button);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Touch touch = TouchManager.toTouch(event);
        for (int i = 1; i <= colorList.size(); i++) {
            if (touch.isInside(colorList.get(i - 1))) {
                if (touch.getAction() == ACTION_DOWN || touch.getAction() == ACTION_POINTER_DOWN) {
                    color = i;
                    setPressed(colorList.get(i - 1), true);
                }
                if (touch.getAction() == ACTION_POINTER_UP || touch.getAction() == ACTION_UP)
                    setPressed(colorList.get(i - 1), false);
            }
        }
        if (touch.isInside(back)) {
            if (touch.getAction() == ACTION_DOWN) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("COlOR TO SHOW", color);
                startActivity(intent);
            }
        }
        return super.onTouchEvent(event);

    }

    @Override
    public void onBackPressed() {

    }

    private void setPressed(ImageView view, boolean isPressed) {
        if (isPressed) {
            if (view != colorList.get(3))
                view.setBackgroundResource(R.drawable.button_bg_pressed);
            else
                view.setBackgroundResource(R.drawable.long_pressed);
        } else {
            if (view != colorList.get(3))
                view.setBackgroundResource(R.drawable.button_bg);
            else
                view.setBackgroundResource(R.drawable.long_button);
        }
    }
}
