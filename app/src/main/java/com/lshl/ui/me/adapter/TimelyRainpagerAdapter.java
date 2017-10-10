package com.lshl.ui.me.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lshl.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */

public class TimelyRainpagerAdapter extends FragmentPagerAdapter{
    private String tabTitles[] ={"求救信息","施救信息"};
    private List<BaseFragment> fragments;
    public TimelyRainpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public TimelyRainpagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
