package com.example.tonto.zees;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.tonto.zees.sounds.SoundManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang on 4/19/2017.
 */

public class SplashScreenActivity extends AppCompatActivity {
    private boolean doneLoad = false;
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

    private String[] listSounds2= {
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
            "music_piano",
            "music_guitar",
            "music_violin",
            "music_harp",
            "music_flute",
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SoundManager.loadSoundIntoList(this, listSounds);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!doneLoad) {
                    SoundManager.loadSoundIntoList(SplashScreenActivity.this, listSounds2);
                    doneLoad = true;
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        countDownTimer.start();

    }
}
