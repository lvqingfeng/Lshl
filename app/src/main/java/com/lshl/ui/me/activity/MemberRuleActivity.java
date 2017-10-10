package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityMemberRuleBinding;
import com.lshl.ui.pay.SelectPayActivity;
import com.lshl.utils.CacheActivity;

public class MemberRuleActivity extends BaseActivity<ActivityMemberRuleBinding> {

    private String mVipPrice;

    public static void actionStart(Activity activity, String vipPrice) {
        Intent intent = new Intent(activity, MemberRuleActivity.class);
        intent.putExtra("vip_price", vipPrice);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CacheActivity.activityList.contains(MemberRuleActivity.this)) {
            CacheActivity.addActivity(MemberRuleActivity.this);
        }
    }

    @Override
    protected void initFieldBeforeMethods() {
        mVipPrice = getIntent().getStringExtra("vip_price");
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("会员制度", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.webView.loadUrl(ApiService.AGREMENT + ApiService.GUAnLIZHIDU);
        mDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPayActivity.actionStart(MemberRuleActivity.this, true, mVipPrice);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_rule;
    }
}
