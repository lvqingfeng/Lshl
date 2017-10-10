package com.lshl.ui.me.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.databinding.ActivitySecurityBinding;
import com.lshl.ui.login.ForgetPasswordActivity;
import com.lshl.ui.me.activity.ChangePasswordActivity;
import com.lshl.ui.me.activity.ChangePhoneActivity;
import com.lshl.utils.UserUtils;
import com.lshl.widget.SwitchView;

public class SecurityActivity extends BaseActivity<ActivitySecurityBinding> {
    private String status;

    public static void actionStart(Activity activity, String status) {
        Intent intent = new Intent(activity, SecurityActivity.class);
        intent.putExtra("status", status);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class Presenter {
        //修改密码
        public void onClickChangePassword() {
            ChangePasswordActivity.actionStart(SecurityActivity.this);
        }

        //忘记密码
        public void onClickForgetPassword() {
            ForgetPasswordActivity.actionStart(SecurityActivity.this);
        }

        //更换手机号或账号
        public void onClickChangePhone() {
            ChangePhoneActivity.actionStart(SecurityActivity.this);
        }

    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("账号与安全", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        status = getIntent().getStringExtra("status");
        if (!TextUtils.isEmpty(status)) {
            if (status.equals("2")) {
                mDataBinding.hidePhone.setOpened(true);
            } else {
                mDataBinding.hidePhone.setOpened(false);
            }
        }
        mDataBinding.hidePhone.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                mDataBinding.hidePhone.setOpened(true);
                hidePhone("2");
            }

            @Override
            public void toggleToOff(View view) {
                mDataBinding.hidePhone.setOpened(false);
                hidePhone("1");
            }
        });
        mDataBinding.rememberSv.setOpened(UserUtils.isSavePassword(mContext));
        //记住密码
        mDataBinding.rememberSv.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                mDataBinding.rememberSv.toggleSwitch(true);
                UserUtils.setSavePassword(mContext, true);
            }

            @Override
            public void toggleToOff(View view) {
                mDataBinding.rememberSv.toggleSwitch(false);
                UserUtils.setSavePassword(mContext, false);
            }
        });
    }

    private void hidePhone(String status) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updatehidePhone(LoginHelper.getInstance().getUserToken(), status)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        if (result.getStatus().equals("1")) {
                            Toast.makeText(SecurityActivity.this, result.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_security;
    }
}
