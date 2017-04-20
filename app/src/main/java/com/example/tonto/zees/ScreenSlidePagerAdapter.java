package com.example.tonto.zees;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

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
