package com.lshl.ui.me.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivitySystemNoticeBinding;
import com.lshl.utils.DateUtils;

public class SystemNoticeActivity extends BaseActivity<ActivitySystemNoticeBinding> {
    private String banben;
    public static void actionStart(Activity activity,String banben) {
        Intent intent = new Intent(activity, SystemNoticeActivity.class);
        intent.putExtra("banben",banben);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("系统通知",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        banben=getIntent().getStringExtra("banben");
        mDataBinding.banben.setText(banben);
        mDataBinding.time.setText(DateUtils.getDateToString2(System.currentTimeMillis()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_notice;
    }
}
