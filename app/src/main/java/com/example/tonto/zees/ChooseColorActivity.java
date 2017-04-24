package com.example.tonto.zees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    private TextView back;
    private TextView backward;
    private TextView forward;
    public static final int NUMBER_OF_MODE = 2;
    public static final int INFERNO = 1;
    public static final int WATER = 2;
    private int mode = INFERNO;
    private TextView modeName;
    private int lastMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_color);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            color = bundle.getInt("PREV COLOR");
            lastMode= bundle.getInt("LAST MODE");
        }
        colorList = new ArrayList<>();
        colorList.add((ImageView) findViewById(R.id.color1));
        colorList.add((ImageView) findViewById(R.id.color2));
        colorList.add((ImageView) findViewById(R.id.color3));
        colorList.add((ImageView) findViewById(R.id.color4));
        colorList.add((ImageView) findViewById(R.id.color5));
        colorList.add((ImageView) findViewById(R.id.color6));
        colorList.add((ImageView) findViewById(R.id.color7));
        backward = (TextView) findViewById(R.id.backward);
        forward = (TextView) findViewById(R.id.forward);
        modeName= (TextView) findViewById(R.id.lava_mode_name);
        back = (TextView) findViewById(R.id.back1_button);
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

        if (touch.isInside(backward)) {
            if (touch.getAction() == ACTION_DOWN || touch.getAction() == ACTION_POINTER_DOWN) {
                if (mode - 1 == 0) {
                    mode = NUMBER_OF_MODE;
                } else
                    mode--;
                modeName.setText(setModeName(mode));
                setPressed(backward, true);
            } else if (touch.getAction() == ACTION_POINTER_UP || touch.getAction() == ACTION_UP) {
                setPressed(backward, false);
            }
        }
         else if(touch.isInside(forward))
        {
            if (touch.getAction() == ACTION_DOWN || touch.getAction() == ACTION_POINTER_DOWN) {
                if (mode +1 > NUMBER_OF_MODE) {
                    mode = 1;
                } else
                    mode++;
                modeName.setText(setModeName(mode));
                setPressed(forward, true);

            }
            else if(touch.getAction() == ACTION_POINTER_UP || touch.getAction() == ACTION_UP)
            {
                setPressed(forward,false);
            }
        }
        else if (touch.isInside(back)) {
            if (touch.getAction() == ACTION_DOWN||touch.getAction()==ACTION_POINTER_DOWN) {
                Intent intent = new Intent(this, LampActivity.class);
                intent.putExtra("COlOR TO SHOW", color);
                intent.putExtra("LAVA MODE TO SHOW", mode);
                intent.putExtra("LAST MODE",lastMode);
                startActivity(intent);
            }
        }
        return super.onTouchEvent(event);

    }

    @Override
    public void onBackPressed() {

    }

    private void setPressed(View view, boolean isPressed) {
        if (isPressed) {

            view.setBackgroundResource(R.drawable.button_bg_selected);

        } else {

            view.setBackgroundResource(R.drawable.button_bg_selector);

        }
    }
    private String setModeName(int mode)
    {
        if(mode==INFERNO)
            return "Inferno";
        else if(mode== WATER)
            return "Clouds on water";
        return null;
    }
}
