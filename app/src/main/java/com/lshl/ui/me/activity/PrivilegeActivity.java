package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityPrivilegeBinding;

public class PrivilegeActivity extends BaseActivity<ActivityPrivilegeBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, PrivilegeActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("特权详情",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.webView.loadUrl(ApiService.AGREMENT+ApiService.GUAnLIZHIDU);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_privilege;
    }
}
