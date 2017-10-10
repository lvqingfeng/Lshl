package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.base.BaseFragment;
import com.lshl.databinding.ActivityTimelyRainBinding;
import com.lshl.ui.me.adapter.TimelyRainpagerAdapter;
import com.lshl.ui.me.fragment.QiujiuFragment;
import com.lshl.ui.me.fragment.ShiJiuFragment;

import java.util.ArrayList;
import java.util.List;

/***
 * 及时雨
 * */
public class TimelyRainActivity extends BaseActivity<ActivityTimelyRainBinding>{
    private List<BaseFragment> fragments;
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TimelyRainActivity.class);
        activity.startActivity(intent);
    }
    public class Presenter {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("及时雨",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new TimelyRainActivity.Presenter());
        fragments=new ArrayList<>();
        fragments.add(QiujiuFragment.newInstance());
        fragments.add(ShiJiuFragment.newInstance());
        TimelyRainpagerAdapter adapter = new TimelyRainpagerAdapter(getSupportFragmentManager(), fragments);
        mDataBinding.vpTimelyList.setAdapter(adapter);
        mDataBinding.tabTimely.setupWithViewPager(mDataBinding.vpTimelyList);
        mDataBinding.tabTimely.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_timely_rain;
    }
}
