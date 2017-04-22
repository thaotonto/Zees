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

public class RelaxingMusic extends Fragment {
    private String[] listSounds = {
            "music_piano",
            "music_guitar",
            "music_violin",
            "music_harp",
            "music_flute"
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SoundManager.loadSoundIntoList(getContext(), listSounds);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.relaxing_music, container, false);
    }
}
