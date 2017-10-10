package com.lshl.ui.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityQiYeDetailsBinding;

public class QiYeDetailsActivity extends BaseActivity<ActivityQiYeDetailsBinding> {
    private String id;
    public static void actionStart(Activity activity,String id) {
        Intent intent = new Intent(activity, QiYeDetailsActivity.class);
        intent.putExtra("id",id);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("详细相信",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        id=getIntent().getStringExtra("id");
        String url= ApiService.QIYEDETAILS+id;
        mDataBinding.webView.loadUrl(url);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qi_ye_details;
    }
}
