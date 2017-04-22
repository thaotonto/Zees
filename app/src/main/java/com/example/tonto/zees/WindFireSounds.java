package com.example.tonto.zees;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hoang on 4/18/2017.
 */

public class WindFireSounds extends Fragment{
    private String[] listSounds = {
            "air_light_wind",
            "air_strong_wind",
            "air_wind_mountain",
            "air_wind_door",
            "fire_campfire",
            "fire_fireplace"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SoundManager.loadSoundIntoList(getContext(), listSounds);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.windfire_sounds, container, false);
    }
}
