package com.example.tonto.zees;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tonto.zees.sounds.SoundManager;

import java.io.Serializable;

/**
 * Created by Hoang on 4/18/2017.
 */

public class CitySounds extends Fragment  {
    private String[] listSounds = {
            "city_musician",
            "city_coffee_shop",
            "city_fountain",
            "city_children",
            "city_traffic",
            "city_subway",
            "city_works",
            "city_train_station"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SoundManager.loadSoundIntoList(getContext(), listSounds);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_sounds, container, false);
    }
}
