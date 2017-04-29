package com.example.tonto.zees;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tonto.zees.touches.TouchManager;
import com.example.tonto.zees.touches.Touch;

import java.util.ArrayList;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;
import static com.example.tonto.zees.ChooseColorActivity.INFERNO;

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
    private int lava_mode = INFERNO;
    private int mode = 1;
    private ImageView simple;
    private ImageView mood;
    private ImageView lava;
    private TextView text_mode;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);

        window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.darkRain));
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ivColorChooserButon = (ImageView) findViewById(R.id.iv_Color_Chooser);
        ivGo = (ImageView) findViewById(R.id.iv_Go);
        simple = (ImageView) findViewById((R.id.button1));
        mood = (ImageView) findViewById((R.id.button2));
        lava = (ImageView) findViewById((R.id.button3));
        text_mode = (TextView) findViewById(R.id.text_mode);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            color = bundle.getInt("COlOR TO SHOW");
            lava_mode = bundle.getInt("LAVA MODE TO SHOW");
            mode = bundle.getInt("LAST MODE");
            text_mode.setText(setText(mode));

        }
        buttonList = new ArrayList<>();
        buttonList.add(ivColorChooserButon);
        buttonList.add(ivGo);
        buttonList.add(simple);
        buttonList.add(mood);
        buttonList.add(lava);
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

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                float BackLightValue = (float) arg1 / 255;
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.screenBrightness = BackLightValue;
                getWindow().setAttributes(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Touch touch = TouchManager.toTouch(event);
        Touch touch= TouchManager.toTouch(event);
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
            if (touch.isInside(simple)) {
                text_mode.setText("NIGHT LIGHT");
                mode = 1;
            } else if (touch.isInside(mood)) {
                text_mode.setText("MOOD LIGHT");
                mode = 2;
            } else if (touch.isInside(lava)) {
                text_mode.setText("LAVA LIGHT");
                mode = 3;
            } else if (touch.isInside(ivColorChooserButon)) {
                Intent intent = new Intent(this, ChooseColorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("PREV COLOR", color);
                intent.putExtra("LAST MODE", mode);
                intent.putExtra("LAST LAVA MODE",lava_mode);
                startActivity(intent);
                setPressed(ivColorChooserButon, false);

            } else if (touch.isInside(ivGo)) {
                setPressed(ivGo, false);
                Intent intent = new Intent(this, ShowColorActivity.class);
                intent.putExtra("COlOR TO SHOW", color);
                intent.putExtra("MODE TO SHOW", mode);
                intent.putExtra("LAVA MODE TO SHOW", lava_mode);
                startActivity(intent);
            }
        }
        return super.onTouchEvent(event);
    }

    private void setPressed(ImageView view, boolean isPressed) {
        if (isPressed) {

            view.setBackgroundResource(R.drawable.button_bg_selected);

        } else {

            view.setBackgroundResource(R.drawable.button_bg_selector);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private String setText(int mode) {
        if (mode == 1)
            return "NIGHT LIGHT";
        else if (mode == 2)
            return "MOOD LIGHT";
        else
            return "LAVA LIGHT";


    }
}
