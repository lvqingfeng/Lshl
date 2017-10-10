package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
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
import com.lshl.databinding.ActivityChangePhoneBinding;
import com.lshl.service.RegisterCodeTimer;
import com.lshl.service.RegisterCodeTimerService;
import com.lshl.utils.CodeUtils;
import com.lshl.utils.EditTextUtils;
import com.lshl.utils.UserUtils;

/**
 * 修改手机号
 */
public class ChangePhoneActivity extends BaseActivity<ActivityChangePhoneBinding> {

    private CodeUtils mCodeUtils;
    private String mCodeStr;
    private InputMethodManager mInputManager;
    private RegisterCodeTimer mRegisterCodeTimer;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ChangePhoneActivity.class);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void onClickGetPictureCode(View view) {
            ((ImageView) view).setImageBitmap(mCodeUtils.createBitmap());
            mCodeStr = mCodeUtils.getCode();
        }

        public void onClickGetPhoneCode() {

            mInputManager.hideSoftInputFromWindow(mDataBinding.getRoot().getWindowToken(), 0);
            String phone = mDataBinding.editNewPhone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                showMessage("请填写您的新手机号");
                return;
            }
//            if (mRegisterCodeTimer != null) {
//                long millisUntilFinished = mRegisterCodeTimer.mMillisUntilFinished;
//
//                if (millisUntilFinished >= 0) {
//                    showMessage("您的验证码获取过于频繁，请稍后重试");
//                    return;
//                }
//            }
            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                    , RetrofitManager.RetrofitType.Object).newSendVerification(LoginHelper.getInstance().getUserBean().getPhone())
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                        @Override
                        public void onNext(HttpResult<String> result) {
                            Log.i("屮艸芔茻", LoginHelper.getInstance().getUserBean().getPhone());
//                    RegisterCodeTimerService.startService(ChangePhoneActivity.this, R.drawable.bg_gray_btn);
                            showMessage("验证码发送成功，请您耐心等待");
                        }
                    }));
//            String pictureCode = mDataBinding.editPictureCode.getText().toString();
//            if (pictureCode.equalsIgnoreCase(mCodeStr)) {
//
//                RetrofitManager.toSubscribe(
//                        ApiClient.getApiService(
//                                ApiService.class, RetrofitManager.RetrofitType.Object
//                        ).sendPhoneCode(phone)
//                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
//                            @Override
//                            public void onNext(HttpResult<String> result) {
//
//                            }
//                        })
//                );
//            } else {
//                showMessage("验证码错误，请重新输入");
//                mDataBinding.editPictureCode.setText("");
//                onClickGetPictureCode(mDataBinding.ivPictureCode);
//            }
        }

        public void onClickSubmit() {
//            String oldPhone = mDataBinding.editOldPhone.getText().toString();
            final String newphone = mDataBinding.editNewPhone.getText().toString();
            String phoneCode = mDataBinding.editPhoneCode.getText().toString();
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).updateMemberPhone(LoginHelper.getInstance().getUserBean().getPhone(), LoginHelper.getInstance().getUserToken(), newphone, phoneCode)
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                        @Override
                        public void onNext(HttpResult<String> result) {
                            UserUtils.putUserName(mContext, newphone);
                            Toast.makeText(mContext, "恭喜您已成功更换手机号", Toast.LENGTH_SHORT).show();
                            finish();
//                            AppManager.getAppManager().finishAllActivity();
//                            new Intent(ChangePhoneActivity.this, LoginActivity.class);
                        }
                    })
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("更换手机号", DEFAULT_TITLE_TEXT_COLOR);
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

        EditTextUtils.checkOnEditInputForButtonState(mContext, mDataBinding.btnSubmit, mDataBinding.editPhoneCode);

        RegisterCodeTimerService.setCountDownTime(60000);
        RegisterCodeTimerService.setCountDownView(mDataBinding.btnGetPhoneCode, R.drawable.bg_red_btn);

        presenter.onClickGetPictureCode(mDataBinding.ivPictureCode);

        mRegisterCodeTimer = RegisterCodeTimerService.getRegisterCodeTimer();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone;
    }
}
