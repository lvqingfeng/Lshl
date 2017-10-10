package com.lshl.ui.me.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月14日
 * 时间：10:37
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class PersonalReputationPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentLists;
    private List<String> mTitleLists;

    public PersonalReputationPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentLists = new ArrayList<>();
        mTitleLists = new ArrayList<>();
    }

    public void addFragmentItem(Fragment fragment, String title) {
        mFragmentLists.add(fragment);
        mTitleLists.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentLists.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleLists.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentLists.size();
    }
}
