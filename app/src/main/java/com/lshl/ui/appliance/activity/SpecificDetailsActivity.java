package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivitySpecificDetailsBinding;
/*****
 *友情链接详情
 * */
public class SpecificDetailsActivity extends BaseActivity<ActivitySpecificDetailsBinding> {
    private String url;
    public static void actionStart(Activity activity,String url) {
        Intent intent = new Intent(activity, SpecificDetailsActivity.class);
        intent.putExtra("url",url);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {

    }

    @Override
    protected void initViews() {
        url=getIntent().getStringExtra("url");
        mDataBinding.webView.loadUrl(url);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_specific_details;
    }
}
