package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityServiceIntroduceBinding;

public class ServiceIntroduceActivity extends BaseActivity<ActivityServiceIntroduceBinding> {
    private String sid;
    public static void actionStart(Activity activity,String sid) {
        Intent intent = new Intent(activity, ServiceIntroduceActivity.class);
        intent.putExtra("sid",sid);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("服务站介绍",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        sid=getIntent().getStringExtra("sid");
        mDataBinding.webView.loadUrl(ApiService.STATION_INTRODUCE+sid);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_introduce;
    }
}
