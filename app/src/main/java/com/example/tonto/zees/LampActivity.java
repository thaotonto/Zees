package com.example.tonto.zees;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.tonto.zees.Touches.TouchManager;
import com.example.tonto.zees.Touches.Touch;

import java.util.ArrayList;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;

public class LampActivity extends AppCompatActivity {
    public static final int COLOR1 = 1;
    public static final int COLOR2 = 2;
    public static final int COLOR3 = 3;
    public static final int COLOR4 = 4;
    public static final int COLOR5 = 5;
    public static final int COLOR6 = 6;
    public static final int COLOR7 = 7;
    private ImageView ivColorChooserButon;
    private ImageView ivGo;
    private ArrayList<ImageView> buttonList;
    private int color = COLOR1;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            color = bundle.getInt("COlOR TO SHOW");
            Log.d("abc", "color:" + color);
        }

        ivColorChooserButon = (ImageView) findViewById(R.id.iv_Color_Chooser);
        ivGo = (ImageView) findViewById(R.id.iv_Go);
        buttonList = new ArrayList<>();
        buttonList.add(ivColorChooserButon);
        buttonList.add(ivGo);
        buttonList.add((ImageView) findViewById((R.id.button1)));
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(255);
        float curBrightnessValue = 0;
        try {
            curBrightnessValue = android.provider.Settings.System.getInt(
                    getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        int screen_brightness = (int) curBrightnessValue;
        seekBar.setProgress(screen_brightness);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                android.provider.Settings.System.putInt(getContentResolver(),
                        android.provider.Settings.System.SCREEN_BRIGHTNESS,
                        progress);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Touch touch = TouchManager.toTouch(event);
        if (touch.getAction() == ACTION_DOWN || touch.getAction() == ACTION_POINTER_DOWN) {
            for (int i = 0; i < buttonList.size(); i++) {
                if (touch.isInside(buttonList.get(i))) {
                    setPressed(buttonList.get(i), true);
                }
            }
        } else if (touch.getAction() == ACTION_UP || touch.getAction() == ACTION_POINTER_UP) {
            for (int i = 0; i < buttonList.size(); i++) {
                if (touch.isInside(buttonList.get(i))) {
                    setPressed(buttonList.get(i), false);
                }
            }
            if (touch.isInside(ivColorChooserButon)) {
                setPressed(ivColorChooserButon, false);
                Intent intent = new Intent(this, ChooseColorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else if (touch.isInside(ivGo)) {
                setPressed(ivGo, false);
                Intent intent = new Intent(this, ShowColorActivity.class);
                intent.putExtra("COlOR TO SHOW", color);
                startActivity(intent);
            }
        }
        return super.onTouchEvent(event);
    }
    private void setPressed(ImageView view, boolean isPressed) {
        if (isPressed) {

            view.setBackgroundResource(R.drawable.button_pressed);

        } else {

            view.setBackgroundResource(R.drawable.button);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
