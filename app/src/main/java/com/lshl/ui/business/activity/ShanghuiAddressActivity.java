package com.lshl.ui.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityShanghuiAddressBinding;

/**
 * 商会列表
 */
public class ShanghuiAddressActivity extends BaseActivity<ActivityShanghuiAddressBinding> {
    private String bid;
    public static void actionStart(Activity activity, String bid) {
        Intent intent = new Intent(activity, ShanghuiAddressActivity.class);
        intent.putExtra("bid",bid);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("联系我们",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        bid=getIntent().getStringExtra("bid");
        mDataBinding.webView.loadUrl(ApiService.SHANGHUI+bid);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shanghui_address;
    }
}
