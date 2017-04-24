package com.example.tonto.zees.sounds;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tonto.zees.R;

/**
 * Created by Hoang on 4/18/2017.
 */

public class NightSounds extends Fragment {
    private String[] listSounds = {
            "nature_night_crickets",
            "nature_night_grasshoppers",
            "nature_night_owls",
            "nature_night_wolves",
            "nature_night_loons",
            "nature_night_frogs",
            "nature_night_coyote",
            "nature_night_coqui"
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SoundManager.loadSoundIntoList(getContext(), listSounds);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.night_sounds, container, false);
    }
}
