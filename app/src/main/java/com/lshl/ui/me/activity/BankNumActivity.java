package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityBankNumBinding;

import java.io.IOException;

import okhttp3.ResponseBody;

public class BankNumActivity extends BaseActivity<ActivityBankNumBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, BankNumActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("编辑财富账号", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mDataBinding.banknum.getText())) {
                    if (!TextUtils.isEmpty(mDataBinding.bankname.getText())) {
                        if (!TextUtils.isEmpty(mDataBinding.bankaddree.getText())) {
                            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                                    RetrofitManager.RetrofitType.String).
                                    addWealthAccount(LoginHelper.getInstance().getUserToken()
                                            , String.valueOf(3), null, null, mDataBinding.bankname.getText().toString(), mDataBinding.banknum.getText().toString(), mDataBinding.bankaddree.getText().toString()), new ProgressSubscriber<ResponseBody>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                                @Override
                                public void onNext(ResponseBody result) {
                                    try {
                                        String string = result.string();
                                        JSONObject object = JSON.parseObject(string);
                                        String status = object.getString("status");
                                        String message = object.getString("message");
                                        if (ApiService.STATUS_SUC.equals(status)) {
                                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }));
                        } else {
                            showMessage("请输入您的卡号");
                        }
                    } else {
                        showMessage("请输入您的卡号");
                    }
                } else {
                    showMessage("请输入您的卡号");
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bank_num;
    }
}
