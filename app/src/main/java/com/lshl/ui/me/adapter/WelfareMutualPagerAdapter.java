package com.lshl.ui.me.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lshl.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */

public class WelfareMutualPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] ={"我的公益","我的互助"};
    private List<BaseFragment> mList;

    public WelfareMutualPagerAdapter(FragmentManager fm, List<BaseFragment> mList) {
        super(fm);
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
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
