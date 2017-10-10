package com.lshl.ui.me.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityDisclaimerBinding;

public class DisclaimerActivity extends BaseActivity<ActivityDisclaimerBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, DisclaimerActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("免责条款",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.webView.loadUrl(ApiService.AGREMENT+ApiService.DISCLAIMER);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_disclaimer;
    }
}
