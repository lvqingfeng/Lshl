package com.lshl.ui.me.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityPresidentDetailsBinding;

public class PresidentDetailsActivity extends BaseActivity<ActivityPresidentDetailsBinding> {
    private String aid;
    public static void actionStart(Activity activity,String aid) {
        Intent intent = new Intent(activity, PresidentDetailsActivity.class);
        intent.putExtra("aid",aid);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("详情",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        aid=getIntent().getStringExtra("aid");
        mDataBinding.webView.loadUrl(ApiService.NEWS_INFO+aid);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_president_details;
    }
}
