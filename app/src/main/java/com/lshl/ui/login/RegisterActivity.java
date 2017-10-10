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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.databinding.ActivityRegisterBinding;
import com.lshl.service.RegisterCodeTimer;
import com.lshl.service.RegisterCodeTimerService;
import com.lshl.utils.CodeUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.EditTextUtils;
import com.lshl.utils.LogInUtils;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    public static final String RESULT_PHONE = "phone";
    public static final String RESULT_PASSWORD = "password";
    public static final String RESULT_IS_AUTO_LOGIN = "isAutoLogin";
    public static final String RESULT_IS_REGISTER_SUCCER = "is_register_success";

    private CodeUtils mCodeUtils;
    private String mCodeStr;
    private InputMethodManager mInputManager;
    private RegisterCodeTimer mRegisterCodeTimer;

    public static void actionStart(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public class Presenter {

        public void onClickGetCode() {
            mInputManager.hideSoftInputFromWindow(mDataBinding.getRoot().getWindowToken(), 0);
            String phone = mDataBinding.editPhone.getText().toString();
            String pictureCode = mDataBinding.editPictureCode.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                showMessage("请填写您的手机号");
                return;
            }


            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).sendPhoneCode(phone)
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                        @Override
                        public void onNext(HttpResult<String> result) {
                            if (result.getStatus().equals("0")) {
                                Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                            } else {
                                RegisterCodeTimerService.startService(RegisterActivity.this, R.drawable.bg_gray_btn);
                                showMessage("验证码发送成功，请您耐心等待");
                            }
                        }
                    })
            );
//            if (mRegisterCodeTimer != null) {
//                long millisUntilFinished = mRegisterCodeTimer.mMillisUntilFinished;
//
//                if (millisUntilFinished >= 0) {
//                    showMessage("您的验证码获取过于频繁，请稍后重试");
//                    return;
//                }
//            }
//            if (pictureCode.equalsIgnoreCase(mCodeStr)) {
//
//                RetrofitManager.toSubscribe(
//                        ApiClient.getApiService(
//                                ApiService.class, RetrofitManager.RetrofitType.Object
//                        ).sendPhoneCode(phone)
//                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
//                            @Override
//                            public void onNext(HttpResult<String> result) {
//                                RegisterCodeTimerService.startService(RegisterActivity.this, R.drawable.bg_gray_btn);
//                                showMessage("验证码发送成功，请您耐心等待");
//                            }
//                        })
//                );
//            } else {
//                showMessage("验证码错误，请重新输入");
//                mDataBinding.editPictureCode.setText("");
//                onClickGetPictureCode(mDataBinding.ivPictureCode);
//            }
        }

        public void onClickGetPictureCode(View view) {
            ((ImageView) view).setImageBitmap(mCodeUtils.createBitmap());
            mCodeStr = mCodeUtils.getCode();
        }


        public void startRegister() {

            mInputManager.hideSoftInputFromWindow(mDataBinding.getRoot().getWindowToken(), 0);
            String phone = mDataBinding.editPhone.getText().toString();
            String phoneCode = mDataBinding.editPhoneCode.getText().toString();
            String password = mDataBinding.editPassword.getText().toString();
            String confirmPassword = mDataBinding.editConfirmPassword.getText().toString();
            String status = LogInUtils.checkPassword(password);
            if (password.equals(confirmPassword)) {
                if (status.equals("1"))
                    RetrofitManager.toSubscribe(
                            ApiClient.getApiService(
                                    ApiService.class, RetrofitManager.RetrofitType.Object
                            ).startRegister(phone, phoneCode, password)
                            , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                                @Override
                                public void onNext(ResponseBody result) {
                                    try {
                                        String resultStr = result.string();
                                        JSONObject object = JSON.parseObject(resultStr);
                                        String status = object.getString("status");
                                        if (status.equals(ApiService.STATUS_SUC)) {
                                            DialogUtils.alertDialog(mContext, "注册成功", "您是否要自动登录"
                                                    , new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            sweetAlertDialog.dismissWithAnimation();
                                                            setupResult(false);
                                                        }
                                                    }, new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            sweetAlertDialog.dismissWithAnimation();
                                                            setupResult(true);

                                                        }
                                                    });
                                        } else {
                                            showMessage(object.getString("info"));
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            })
                    );
                else {
                    showMessage(status);
                }
            } else {
                showMessage("您两次输入的密码不一致");
            }

        }

    }

    /**
     * 设置注册成功后返回给上一个页面的数据
     *
     * @param isAutoLogin 是否自动登陆
     */
    private void setupResult(boolean isAutoLogin) {
        String phone = mDataBinding.editPhone.getText().toString();
        String password = mDataBinding.editPassword.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(RESULT_IS_REGISTER_SUCCER, true);
        intent.putExtra(RESULT_PHONE, phone);
        intent.putExtra(RESULT_PASSWORD, password);
        intent.putExtra(RESULT_IS_AUTO_LOGIN, isAutoLogin);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("注册", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //RegisterCodeTimerService.onActivityFinish();
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
        //EditTextUtils.checkOnEditInputForButtonState(mContext, mDataBinding.btnGetPhoneCode, mDataBinding.editPhone, mDataBinding.editPictureCode);
        //, mDataBinding.editConfirmPassword//重复密码的检测
        EditTextUtils.checkOnEditInputForButtonState(mContext, mDataBinding.btnRegister, mDataBinding.editPhone, mDataBinding.editPhoneCode, mDataBinding.editPassword);

        RegisterCodeTimerService.setCountDownTime(60000);
        RegisterCodeTimerService.setCountDownView(mDataBinding.btnGetPhoneCode, R.drawable.bg_red_btn);

        presenter.onClickGetPictureCode(mDataBinding.ivPictureCode);

        mRegisterCodeTimer = RegisterCodeTimerService.getRegisterCodeTimer();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }
}
