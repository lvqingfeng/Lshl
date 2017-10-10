package com.lshl.ui.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityCommerceIntroductionBinding;

public class CommerceIntroductionActivity extends BaseActivity<ActivityCommerceIntroductionBinding> {
    private String id;
    public static void actionStart(Activity activity, String id) {
        Intent intent = new Intent(activity, CommerceIntroductionActivity.class);
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
        setTextTitleView("商会简介",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        id=getIntent().getStringExtra("id");
        mDataBinding.webView.loadUrl(ApiService.CommerceIntroduce+id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commerce_introduction;
    }
}
