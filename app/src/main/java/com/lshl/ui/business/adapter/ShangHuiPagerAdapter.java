package com.lshl.ui.business.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lshl.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class ShangHuiPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = {"商会", "企业"};
    private List<BaseFragment> fragments;

    public ShangHuiPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public ShangHuiPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
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
