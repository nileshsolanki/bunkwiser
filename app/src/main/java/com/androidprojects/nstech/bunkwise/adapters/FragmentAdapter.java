package com.androidprojects.nstech.bunkwise.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.androidprojects.nstech.bunkwise.DayFragment;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    String [] daysOfWeek = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};


    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return daysOfWeek[position];
    }

    @Override
    public Fragment getItem(int i) {

        Fragment frag = new DayFragment();
        return frag;
    }

    @Override
    public int getCount() {
        return daysOfWeek.length;
    }
}
