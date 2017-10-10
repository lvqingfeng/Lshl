package com.lshl.ui.me.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityTechnicalSupportBinding;

public class TechnicalSupportActivity extends BaseActivity<ActivityTechnicalSupportBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, TechnicalSupportActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("技术支持",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.webView.loadUrl(ApiService.AGREMENT+ApiService.TechnicalSupport);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_technical_support;
    }
}
