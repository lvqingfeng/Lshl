package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityImageBinding;

public class ImageActivity extends BaseActivity<ActivityImageBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ImageActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("查看图片",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }
}
