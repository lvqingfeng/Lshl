package com.lshl.ui.me.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding> {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, AboutUsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("关于我们", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.edition.setText("鲁商互联"+getAppVersionName(mContext));
        mDataBinding.introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompanyIntroductionActivity.actionStart(AboutUsActivity.this);
            }
        });
        mDataBinding.newBanben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAppVersionName(mContext).equals(getVersionName())){
                    Toast.makeText(mContext,"已经是最新版本",Toast.LENGTH_SHORT).show();
                }else {
                    mDataBinding.news.setVisibility(View.GONE);
                    SystemNoticeActivity.actionStart(AboutUsActivity.this,getVersionName());
                }
            }
        });

        mDataBinding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemNoticeActivity.actionStart(AboutUsActivity.this,getVersionName());
            }
        });
        mDataBinding.agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseAgreementActivity.actionStart(AboutUsActivity.this);
            }
        });
        mDataBinding.disclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisclaimerActivity.actionStart(AboutUsActivity.this);
            }
        });
        mDataBinding.technicalSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TechnicalSupportActivity.actionStart(AboutUsActivity.this);
            }
        });
    }
    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return versioncode+"";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    /**
     * 获取当前应用的版本号：
     * */
    private String getVersionName() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = null;
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

}

