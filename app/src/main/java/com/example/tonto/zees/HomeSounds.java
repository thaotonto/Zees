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

public class HomeSounds extends Fragment {
    private String[] listSounds = {
            "home_fan",
            "home_air_conditioner",
            "home_hair_dryer",
            "home_vacuum_cleaner",
            "home_cat_purring",
            "home_shower",
            "home_washing_machine",
            "home_jacuzzi",
            "home_fridge"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SoundManager.loadSoundIntoList(getContext(), listSounds);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_sounds, container, false);
    }
}
