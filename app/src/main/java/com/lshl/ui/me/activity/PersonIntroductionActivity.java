package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityPersonIntroductionBinding;

public class PersonIntroductionActivity extends BaseActivity<ActivityPersonIntroductionBinding> {
    public static final int INTRODUCE=0x0000123;
    public static void actionStart(Activity activity,int resultCode) {
        Intent intent = new Intent(activity, PersonIntroductionActivity.class);
        activity.startActivityForResult(intent,resultCode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("个人简介",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {

        mDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mDataBinding.editText.getText())){
                    if (mDataBinding.editText.getText().toString().length()<200){
                        Intent intent = new Intent();
                        intent.putExtra("profile",mDataBinding.editText.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }else {
                       showMessage("不能超过二百字");
                    }
                }else {
                    showMessage("请输入您的个人简介");
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_introduction;
    }
}
