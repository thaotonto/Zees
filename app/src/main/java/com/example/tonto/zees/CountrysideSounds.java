package com.example.tonto.zees;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tonto.zees.sounds.SoundManager;

import java.io.Serializable;

/**
 * Created by Hoang on 4/18/2017.
 */

public class CountrysideSounds extends Fragment {
    private String[] listSounds = {
            "nature_day_blackbirds",
            "nature_day_crows",
            "nature_day_farm",
            "nature_day_cowbells",
            "nature_day_sheep",
            "nature_day_horse",
            "nature_day_eagle",
            "nature_day_turtledove",
            "nature_day_cicadas"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SoundManager.loadSoundIntoList(getContext(), listSounds);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.countryside_sounds, container, false);
    }
}
