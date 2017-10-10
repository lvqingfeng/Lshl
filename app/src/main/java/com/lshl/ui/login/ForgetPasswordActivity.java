package com.lshl.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.databinding.ActivityForgetPasswordBinding;
import com.lshl.service.RegisterCodeTimer;
import com.lshl.service.RegisterCodeTimerService;
import com.lshl.utils.CodeUtils;
import com.lshl.utils.EditTextUtils;
import com.lshl.utils.LogInUtils;

import rx.Subscriber;

/**
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity<ActivityForgetPasswordBinding> {

    private CodeUtils mCodeUtils;
    private String mCodeStr;
    private InputMethodManager mInputManager;

    private RegisterCodeTimer mRegisterCodeTimer;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ForgetPasswordActivity.class);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void onClickGetPictureCode(View view) {
            ((ImageView) view).setImageBitmap(mCodeUtils.createBitmap());
            mCodeStr = mCodeUtils.getCode();
        }

        public void onClickGetPhoneCode() {
            mInputManager.hideSoftInputFromWindow(mDataBinding.getRoot().getWindowToken(), 0);

            String phone = mDataBinding.editPhone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                showMessage("请填写您的手机号");
                return;
            }
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).getPasswordPhonecode(phone)
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                        @Override
                        public void onNext(HttpResult<String> result) {
                            RegisterCodeTimerService.startService(ForgetPasswordActivity.this, R.drawable.bg_gray_btn);
                            showMessage("验证码发送成功，请耐心等待");
                        }
                    })
            );
            if (mRegisterCodeTimer != null) {
                long millisUntilFinished = mRegisterCodeTimer.mMillisUntilFinished;

                if (millisUntilFinished >= 0) {
                    showMessage("您的验证码获取过于频繁，请稍后重试");
                    return;
                }
            }
//            String pictureCode = mDataBinding.editPictureCode.getText().toString();
//            if (pictureCode.equalsIgnoreCase(mCodeStr)) {
//
//            } else {
//                showMessage("验证码错误，请重试");
//                onClickGetPictureCode(mDataBinding.ivPictureCode);
//                mDataBinding.editPictureCode.setText("");
//            }

        }

        public void onClickConfirm() {

            mInputManager.hideSoftInputFromWindow(mDataBinding.getRoot().getWindowToken(), 0);
            String phone = mDataBinding.editPhone.getText().toString();
            String phoneCode = mDataBinding.editPhoneCode.getText().toString();
            String password = mDataBinding.editPassword.getText().toString();
            String confirmPassword = mDataBinding.editConfirmPassword.getText().toString();
            if (confirmPassword.equals(password)) {
                String status = LogInUtils.checkPassword(password);
                if (status.equals("1"))
                    RetrofitManager.toSubscribe(
                            ApiClient.getApiService(
                                    ApiService.class, RetrofitManager.RetrofitType.Object
                            ).getBackPassword(phone, password, phoneCode)
                            , new Subscriber<HttpResult<String>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(HttpResult<String> result) {
                                    if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                        Toast.makeText(mContext, "恭喜您的密码已成功找回", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                else
                    showMessage(status);
            } else
                showMessage("您两次输入的密码不一致，请检测后重试");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("忘记密码", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mCodeUtils = new CodeUtils();
        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void initViews() {

        Presenter presenter = new Presenter();
        mDataBinding.setPresenter(presenter);
        EditTextUtils.checkOnEditInputForButtonState(mContext, mDataBinding.btnConfirm, mDataBinding.editPhone, mDataBinding.editPassword, mDataBinding.editConfirmPassword, mDataBinding.editPhoneCode);
        RegisterCodeTimerService.setCountDownView(mDataBinding.btnGetPhoneCode, R.drawable.bg_red_btn);
        RegisterCodeTimerService.setCountDownTime(60000);
        mRegisterCodeTimer = RegisterCodeTimerService.getRegisterCodeTimer();
        presenter.onClickGetPictureCode(mDataBinding.ivPictureCode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }
}
