package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityScanningResultBinding;

public class ScanningResultActivity extends BaseActivity<ActivityScanningResultBinding> {
    private String url;
    public static void actionStart(Activity activity,String url) {
        Intent intent = new Intent(activity, ScanningResultActivity.class);
        intent.putExtra("url",url);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("扫描结果",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        url=getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)){
            Log.i("屮艸芔茻",url+"=====");
            mDataBinding.webView.loadUrl(url);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scanning_result;
    }
}
