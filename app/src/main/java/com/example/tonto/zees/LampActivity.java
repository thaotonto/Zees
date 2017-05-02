package com.example.tonto.zees;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class LampActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private ImageView simple;
    private ImageView mood;
    private ImageView lava;
    private TextView text_mode;
    private Window window;
    private Toolbar toolbar;
    private ImageView background;
    private int currentPosition;
    private ImageView top;
    private int[] images = {
            R.drawable.top_rain,
            R.drawable.top_ocean,
            R.drawable.top_water,
            R.drawable.top_nature_night,
            R.drawable.top_nature_day,
            R.drawable.top_air_fire,
            R.drawable.top_music,
            R.drawable.top_oriental,
            R.drawable.top_city,
            R.drawable.top_home
    };
    private int[] backgrounds = {
            R.drawable.background_rain,
            R.drawable.background_ocean,
            R.drawable.background_water,
            R.drawable.background_nature_night,
            R.drawable.background_nature_day,
            R.drawable.background_air_fire,
            R.drawable.background_music,
            R.drawable.background_oriental,
            R.drawable.background_city,
            R.drawable.background_home
    };

    private ColorDrawable[] actionBarColorCodes = {
            new ColorDrawable(Color.parseColor("#ff597f9c")),
            new ColorDrawable(Color.parseColor("#ff749daf")),
            new ColorDrawable(Color.parseColor("#ff899bbf")),
            new ColorDrawable(Color.parseColor("#ff1d3856")),
            new ColorDrawable(Color.parseColor("#ff357829")),
            new ColorDrawable(Color.parseColor("#ffc7b098")),
            new ColorDrawable(Color.parseColor("#ffdc8686")),
            new ColorDrawable(Color.parseColor("#ff8e91d4")),
            new ColorDrawable(Color.parseColor("#ff01579b")),
            new ColorDrawable(Color.parseColor("#ffaca08e"))
    };

    private String[] statusBarColorCodes = {
            "#ff233a5a",
            "#ff437b92",
            "#ff6680a8",
            "#ff0d1f37",
            "#ff194c23",
            "#ff6a594a",
            "#ff573636",
            "#ff3a3589",
            "#ff263238",
            "#ff7e7362"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
         currentPosition=bundle.getInt("Position");
        }

        window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.darkRain));
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        toolbar = (Toolbar) findViewById(R.id.toolbar_lamp);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        background = (ImageView) findViewById(R.id.lamp_background);
        top= (ImageView) findViewById(R.id.lamp_top);
        setTop(currentPosition);
        setBackground(currentPosition);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusbarColor(currentPosition);
        }

        simple = (ImageView) findViewById((R.id.button1));
        mood = (ImageView) findViewById((R.id.button2));
        lava = (ImageView) findViewById((R.id.button3));
        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LampActivity.this, ShowSimpleColorActivity.class);
                startActivity(intent);
            }
        });
        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LampActivity.this, ShowMoodLightActivity.class);
                startActivity(intent);
            }
        });
        lava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LampActivity.this, ShowLavaLightActivity.class);
                if(ShowLavaLightActivity.currentMode==4)
                {
                    ShowLavaLightActivity.isBack=false;
                }
                startActivity(intent);
            }
        });
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
////        Touch touch = TouchManager.toTouch(event);
//        Touch touch= TouchManager.toTouch(event);
//        if (touch.getAction() == ACTION_DOWN || touch.getAction() == ACTION_POINTER_DOWN) {
//            for (int i = 0; i < buttonList.size(); i++) {
//                if (touch.isInside(buttonList.get(i))) {
//                    setPressed(buttonList.get(i), true);
//                }
//            }
//        } else if (touch.getAction() == ACTION_UP || touch.getAction() == ACTION_POINTER_UP) {
//            for (int i = 0; i < buttonList.size(); i++) {
//                if (touch.isInside(buttonList.get(i))) {
//                    setPressed(buttonList.get(i), false);
//                }
//            }
//            if (touch.isInside(simple)) {
//                text_mode.setText("NIGHT LIGHT");
//                mode = 1;
//            } else if (touch.isInside(mood)) {
//                text_mode.setText("MOOD LIGHT");
//                mode = 2;
//            } else if (touch.isInside(lava)) {
//                text_mode.setText("LAVA LIGHT");
//                mode = 3;
//            } else if (touch.isInside(ivColorChooserButon)) {
//                Intent intent = new Intent(this, ChooseColorActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("PREV COLOR", color);
//                intent.putExtra("LAST MODE", mode);
//                intent.putExtra("LAST LAVA MODE",lava_mode);
//                startActivity(intent);
//                setPressed(ivColorChooserButon, false);
//
//            } else if (touch.isInside(ivGo)) {
//                setPressed(ivGo, false);
//                Intent intent = new Intent(this, ShowColorActivity.class);
//                intent.putExtra("COlOR TO SHOW", color);
//                intent.putExtra("MODE TO SHOW", mode);
//                intent.putExtra("LAVA MODE TO SHOW", lava_mode);
//                startActivity(intent);
//            }
//        }
//        return super.onTouchEvent(event);
//    }

//    private void setPressed(ImageView view, boolean isPressed) {
//        if (isPressed) {
//
//            view.setBackgroundResource(R.drawable.button_bg_selected);
//
//        } else {
//
//            view.setBackgroundResource(R.drawable.button_bg_selector);
//
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
    }

    public void setBackground(int position) {
        background.setImageResource(backgrounds[position]);
        toolbar.setBackground(actionBarColorCodes[position]);
    }
    public void setTop(int position)
    {
        top.setImageResource(images[position]);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setStatusbarColor(int position) {
        window.setStatusBarColor(Color.parseColor(statusBarColorCodes[position]));
    }
}
