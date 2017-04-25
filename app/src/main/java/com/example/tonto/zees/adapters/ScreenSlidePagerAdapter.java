package com.example.tonto.zees.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tonto.zees.MainActivity;
import com.example.tonto.zees.sounds.CitySounds;
import com.example.tonto.zees.sounds.CountrysideSounds;
import com.example.tonto.zees.sounds.HomeSounds;
import com.example.tonto.zees.sounds.NightSounds;
import com.example.tonto.zees.sounds.OceanSounds;
import com.example.tonto.zees.sounds.OrientalSounds;
import com.example.tonto.zees.sounds.RainSounds;
import com.example.tonto.zees.sounds.RelaxingMusic;
import com.example.tonto.zees.sounds.RiverSounds;
import com.example.tonto.zees.sounds.WindFireSounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang on 4/19/2017.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private MainActivity activity;
    private List<Fragment> fragmentList = new ArrayList<>();

    public ScreenSlidePagerAdapter(FragmentManager fm, MainActivity activity) {
        super(fm);
        this.activity = activity;
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
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return MainActivity.NUM_PAGES;
    }

}
