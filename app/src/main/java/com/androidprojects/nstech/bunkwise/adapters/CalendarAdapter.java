package com.androidprojects.nstech.bunkwise.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends FragmentPagerAdapter {

    List<Fragment> fragList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    public CalendarAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragList.get(i);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    public void addFragments(Fragment fragment, String title){

        fragList.add(fragment);
        titleList.add(title);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
