package com.lshl.ui.me.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityUseAgreementBinding;
public class UseAgreementActivity extends BaseActivity<ActivityUseAgreementBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, UseAgreementActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("用户协议",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.webView.loadUrl(ApiService.AGREMENT+ApiService.USEAGREMENT);
    }

    @Override
    protected int getLayoutId() {
        return com.lshl.R.layout.activity_use_agreement;
    }
}
