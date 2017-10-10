package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityFeedbackBinding;

public class WelfareFeedbackActivity extends BaseActivity<ActivityFeedbackBinding> {
    private String pwid;
    public static void actionStart(Activity activity,String pwid) {
        Intent intent = new Intent(activity, MutualActivity.class);
        intent.putExtra("pwid",pwid);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("回馈信息",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        pwid=getIntent().getStringExtra("pwid");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }
}
