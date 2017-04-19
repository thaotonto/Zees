package com.example.tonto.zees;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tonto.zees.sounds.SoundManager;

import java.util.List;

/**
 * Created by Hoang on 4/18/2017.
 */

public class RainSounds extends Fragment {
//    private List<PressedKeyInfo> pressedKeyInfoList;
    private String[] listSounds = {
            "morning_rain",
            "umbrella"
//            "thunders",
//            "light",
//            "heavy",
//            "distant_thunder",
//            "tent",
//            "window",
//            "roof"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoundManager.loadSoundIntoList(getContext(), "rain", listSounds);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rain_sounds, container, false);
        return view;
    }

}
