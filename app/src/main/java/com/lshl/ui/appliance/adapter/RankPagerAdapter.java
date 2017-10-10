package com.lshl.ui.appliance.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lshl.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 */
public class RankPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] ={"总排行榜","年度排行榜","月份排行榜"};
    private List<BaseFragment> fragments;
    public RankPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public RankPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
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
