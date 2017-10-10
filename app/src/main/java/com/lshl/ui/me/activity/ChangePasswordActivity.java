package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.lshl.databinding.ActivityChangePasswordBinding;
import com.lshl.utils.EditTextUtils;
import com.lshl.utils.LogInUtils;

/**
 * 密码修改
 */
public class ChangePasswordActivity extends BaseActivity<ActivityChangePasswordBinding> {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ChangePasswordActivity.class);
        activity.startActivity(intent);
    }

    public class Presenter {

        public void onClickAlterPassword() {
            String oldPassword = mDataBinding.editOldPassword.getText().toString();
            String newPassword = mDataBinding.editNewPassword.getText().toString();
            String againNewPassword = mDataBinding.editAgainNewPassword.getText().toString();
            if (oldPassword.equals(newPassword)) {
                Toast.makeText(mContext, "新密码不能和原密码一致", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newPassword.equals(againNewPassword)) {
                String status = LogInUtils.checkPassword(newPassword);
                if (status.equals("1")) {
                    RetrofitManager.toSubscribe(
                            ApiClient.getApiService(
                                    ApiService.class, RetrofitManager.RetrofitType.Object
                            ).updatePassword(LoginHelper.getInstance().getUserToken(), oldPassword, newPassword, againNewPassword)
                            , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                                @Override
                                public void onNext(HttpResult<String> result) {
                                    if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                                        Toast.makeText(mContext, "密码修改成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                    );
                } else {
                    showMessage(status);
                }
            } else {
                showMessage("两次密码不一致");
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("密码修改", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        EditTextUtils.checkOnEditInputForButtonState(mContext, mDataBinding.btnAlterPassword, mDataBinding.editAgainNewPassword, mDataBinding.editNewPassword, mDataBinding.editOldPassword);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }
}
