package com.example.tonto.zees;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
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

public class OceanSounds extends Fragment {
    private String[] listSounds = {
            "ocean_calm_waves",
            "ocean_waves",
            "ocean_seagulls",
            "ocean_diver",
            "ocean_dolphins",
            "ocean_sailboat",
            "ocean_whale"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SoundManager.loadSoundIntoList(getContext(), listSounds);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ocean_sounds, container, false);
    }
}
