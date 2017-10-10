package com.lshl.ui.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityMembershipBinding;

import java.io.File;

public class MembershipActivity extends BaseActivity<ActivityMembershipBinding> {
    private String mBuid;
    public static void actionStart(Activity activity,String mBuid) {
        Intent intent = new Intent(activity, MembershipActivity.class);
        intent.putExtra("mBuid",mBuid);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("缴纳会费",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mBuid=getIntent().getStringExtra("mBuid");
        clearCacheFolder(mContext.getCacheDir(), System.currentTimeMillis());
        mDataBinding.webView.loadUrl(ApiService.PAY_SH+mBuid);
    }
    private int clearCacheFolder(File dir, long numDays) {
        int deletedFiles = 0;
        if (dir!= null && dir.isDirectory()) {
            try {
                for (File child:dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }
                    if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_membership;
    }
}
