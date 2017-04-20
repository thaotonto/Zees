package com.example.tonto.zees;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang on 4/19/2017.
 */

public class SplashScreenActivity extends AppCompatActivity{
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fragmentList.add(new RainSounds());
        fragmentList.add(new OceanSounds());
        fragmentList.add(new RiverSounds());
        fragmentList.add(new NightSounds());
        fragmentList.add(new CountrysideSounds());
        fragmentList.add(new WindFireSounds());
        fragmentList.add(new RelaxingMusic());
        fragmentList.add(new OrientalSounds());
        fragmentList.add(new CitySounds());
        fragmentList.add(new HomeSounds());


        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("FragmentList",(ArrayList)fragmentList);
                startActivity(intent);
            }
        };

        countDownTimer.start();
    }
}
