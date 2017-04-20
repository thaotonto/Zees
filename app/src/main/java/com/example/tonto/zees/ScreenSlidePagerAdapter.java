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
    private List<Fragment> fragmentList;

    public ScreenSlidePagerAdapter(FragmentManager fm, MainActivity activity,List<Fragment> fragmentList) {
        super(fm);
        this.activity = activity;
        this.fragmentList = fragmentList;

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
