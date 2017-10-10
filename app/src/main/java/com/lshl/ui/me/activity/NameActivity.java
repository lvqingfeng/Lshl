package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityNameBinding;

public class NameActivity extends BaseActivity<ActivityNameBinding> {
    private String name;
    private SharedPreferences.Editor edit;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, NameActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("真实姓名",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        SharedPreferences preferences = getSharedPreferences("baseData",Activity.MODE_PRIVATE);
        edit = preferences.edit();
        mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mDataBinding.name.getText())){
                    Toast.makeText(mContext,"请输入您的姓名",Toast.LENGTH_SHORT).show();
                }else {
                    edit.putString("name",mDataBinding.name.getText().toString());
                    edit.commit();
                    finish();
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_name;
    }
}
