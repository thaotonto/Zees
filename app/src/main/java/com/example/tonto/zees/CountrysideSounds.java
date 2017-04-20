package com.example.tonto.zees;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tonto.zees.sounds.SoundManager;

/**
 * Created by Hoang on 4/18/2017.
 */

public class CountrysideSounds extends Fragment {
    private String name = "nature_day";
    private String[] listSounds = {
           "blackbirds",
            "crows",
            "farm",
            "cowbells",
            "sheep",
            "horse",
            "eagle",
            "turtledove",
            "cicadas"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoundManager.loadSoundIntoList(getContext(), name, listSounds);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.countryside_sounds, container, false);
    }
}
