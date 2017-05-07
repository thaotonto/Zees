package com.example.tonto.zees;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Hoang on 4/19/2017.
 */

public class SplashScreenActivity extends AppCompatActivity {
    private boolean doneLoad = false;
    private Window window;
    private TextView loadingText;
    private int count = 0;
    private ImageView logo;

    private String[] listSounds = {
            "rain_morning_rain",
            "rain_umbrella",
            "rain_thunders",
            "rain_light",
            "rain_heavy",
            "rain_distant_thunder",
            "rain_tent",
            "rain_window",
            "rain_roof"
    };

    private String[] listSounds2 = {
            "ocean_calm_waves",
            "ocean_waves",
            "ocean_seagulls",
            "ocean_diver",
            "ocean_dolphins",
            "ocean_sailboat",
            "ocean_whale",
            "water_river",
            "water_brook",
            "water_creek",
            "water_waterfall",
            "nature_night_crickets",
            "nature_night_grasshoppers",
            "nature_night_owls",
            "nature_night_wolves",
            "nature_night_loons",
            "nature_night_frogs",
            "nature_night_coyote",
            "nature_night_coqui",
            "nature_day_blackbirds",
            "nature_day_crows",
            "nature_day_farm",
            "nature_day_cowbells",
            "nature_day_sheep",
            "nature_day_horse",
            "nature_day_eagle",
            "nature_day_turtledove",
            "nature_day_cicadas",
            "air_light_wind",
            "air_strong_wind",
            "air_wind_mountain",
            "air_wind_door",
            "fire_campfire",
            "fire_fireplace",
//            "music_piano",
//            "music_guitar",
//            "music_violin",
//            "music_harp",
//            "music_flute",
            "oriental_bowls",
            "oriental_gong",
            "oriental_bells",
            "oriental_om",
            "oriental_flute",
            "oriental_didgeridoo",
            "oriental_chimes",
            "oriental_string",
            "city_musician",
            "city_coffee_shop",
            "city_fountain",
            "city_children",
            "city_traffic",
            "city_subway",
            "city_works",
            "city_train_station",
            "home_fan",
            "home_air_conditioner",
            "home_hairdryer",
            "home_vacuum_cleaner",
            "home_cat_purring",
            "home_shower",
            "home_washing_machine",
            "home_jacuzzi",
            "home_fridge"
    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadingText = (TextView) findViewById(R.id.loading_text);


        window = getWindow();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            window.setStatusBarColor(Color.BLACK);

        logo = (ImageView) findViewById(R.id.logo);

        Animation animation1 = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.abc_fade_in);
        animation1.setInterpolator(new LinearInterpolator());
        animation1.setDuration(2000);

        Animation animation2 = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.abc_fade_in);
        animation2.setInterpolator(new LinearInterpolator());
        animation2.setDuration(2000);

        logo.startAnimation(animation1);
        loadingText.startAnimation(animation2);

        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
//                switch (count) {
//                    case 0: {
//                        loadingText.setText("Loading");
//                        count++;
//                    }
//                    break;
//                    case 1: {
//                        loadingText.setText("Loading.");
//                        count++;
//                    }
//                    break;
//                    case 2: {
//                        loadingText.setText("Loading..");
//                        count++;
//                    }
//                    break;
//                    case 3: {
//                        loadingText.setText("Loading...");
//                        count = 0;
//                    }
//                    break;
//                }
            }

            @Override
            public void onFinish() {
                loadingText.setText("Finished");
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
            }
        };

        countDownTimer.start();

    }
}
