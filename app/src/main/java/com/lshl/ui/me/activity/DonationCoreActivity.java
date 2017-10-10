package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityDonationCoreBinding;

public class DonationCoreActivity extends BaseActivity<ActivityDonationCoreBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, DonationCoreActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public class Presenter{
        public void chooseWeiXin(){

        }
        public void chooseZhiFuBao(){

        }
        public void chooseBank(){

        }
    }
    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("捐助中心",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_donation_core;
    }
}
