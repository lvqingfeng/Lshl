package com.lshl.ui.me.job;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityMyRecruitBinding;
import com.lshl.databinding.ClickPraiseTitleBinding;
import com.lshl.ui.me.job.fragment.DeliveryInfoListFragment;
import com.lshl.ui.me.job.fragment.PublishRecruitFragment;

import java.util.ArrayList;
import java.util.List;

public class MyRecruitActivity extends BaseActivity<ActivityMyRecruitBinding> {

    private ClickPraiseTitleBinding mTitleBinding;
    private List<Fragment> mFragmentList;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyRecruitActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mFragmentList = new ArrayList<>();
    }

    @Override
    protected void setupTitle() {
        View titleView = setTitleCenterViewRes(R.layout.layout_click_praise_title, false);
        mTitleBinding = DataBindingUtil.bind(titleView);
        openTitleLeftView(true);
        mTitleBinding.radioBtnLeft.setText("招聘信息");
        mTitleBinding.radioBtnRight.setText("投递信息");
    }

    @Override
    protected void initViews() {

        mFragmentList.add(PublishRecruitFragment.newInstance());
        mFragmentList.add(DeliveryInfoListFragment.newInstance());

        mTitleBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int groupChildCount = group.getChildCount();
                for (int i = 0; i < groupChildCount; i++) {
                    RadioButton button = (RadioButton) group.getChildAt(i);
                    if (button.isChecked()) {
                        mDataBinding.vpRecruit.setCurrentItem(i);
                    }
                }
            }
        });
        mTitleBinding.radioBtnLeft.setChecked(true);
        mDataBinding.vpRecruit.setAdapter(new RecruitPagerAdapter(getSupportFragmentManager()));
        mDataBinding.vpRecruit.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) mTitleBinding.radioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_recruit;
    }


    private class RecruitPagerAdapter extends FragmentPagerAdapter {

        RecruitPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

}
