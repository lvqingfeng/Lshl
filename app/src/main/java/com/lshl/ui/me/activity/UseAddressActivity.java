package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityUseAddressBinding;

public class UseAddressActivity extends BaseActivity<ActivityUseAddressBinding> {
    private String useShi="济南";
    private String useXian;
    private String useDetails;
    public static final int USE_ADDRESS=0x0000124;
    public static void actionStart(Activity activity,int resultCode) {
        Intent intent = new Intent(activity, UseAddressActivity.class);
        activity.startActivityForResult(intent,resultCode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("老家地址",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.sprinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                useShi = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mDataBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View v) {
                if (!TextUtils.isEmpty(mDataBinding.xian.getText())){
                    useXian=mDataBinding.xian.getText().toString();
                    if (!TextUtils.isEmpty(mDataBinding.details.getText())){
                        useDetails=mDataBinding.details.getText().toString();
                    }else {
                        useDetails="";
                    }
                    Intent intent = new Intent(mContext, BaseDataActivity.class);
                    intent.putExtra("useShi",useShi);
                    intent.putExtra("useXian",useXian);
                    intent.putExtra("useDetails",useDetails);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    showMessage("请完整输入个人资料");
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_use_address;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
