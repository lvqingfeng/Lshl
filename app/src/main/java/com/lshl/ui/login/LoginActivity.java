package com.lshl.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lshl.ChatHelper;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseActivity;
import com.lshl.base.LshlApplication;
import com.lshl.bean.User;
import com.lshl.databinding.ActivityLoginBinding;
import com.lshl.db.bean.HxUserBean;
import com.lshl.task.TaskBase;
import com.lshl.ui.MainActivity;
import com.lshl.utils.AppManager;
import com.lshl.utils.DateUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.EditTextUtils;
import com.lshl.utils.NetUtils;
import com.lshl.utils.UserUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private static final int REQUEST_CODE_REGISTER = 0x000123;

    private boolean isLoginChatSuc;
    private boolean isUserInfoLoadSuc;
    private User mUser;
    private InputMethodManager mInputManager;
    private boolean isExceptionLogin;
    private String mExceptionMessage;
    private boolean isRegisterSuccess;


    public static void actionStart(Activity activity, boolean isExceptionLogin, String exceptionMessage) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra("is_exception", isExceptionLogin);
        intent.putExtra("message", exceptionMessage);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void onClickForgetPassword() {
            ForgetPasswordActivity.actionStart(LoginActivity.this);
        }

        public void startLogin(View view) {
            if (!NetUtils.isConnected(mContext)) {
                Toast.makeText(mContext, "当前网络无连接,请保持网络畅通", Toast.LENGTH_SHORT).show();
                return;
            }
            view.setEnabled(false);
            String account = mDataBinding.editAccount.getText().toString();
            String password = mDataBinding.editPassword.getText().toString();
            LoginActivity.this.startLogin(account, password);
            view.setEnabled(true);
        }

        public void onClickParent() {
            mInputManager.hideSoftInputFromWindow(mDataBinding.getRoot().getWindowToken(), 0);
        }

        public void startRegister() {
            RegisterActivity.actionStart(LoginActivity.this, REQUEST_CODE_REGISTER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGISTER) {
            if (resultCode == RESULT_OK) {
                boolean isAutoLogin = data.getBooleanExtra(RegisterActivity.RESULT_IS_AUTO_LOGIN, false);
                isRegisterSuccess = data.getBooleanExtra(RegisterActivity.RESULT_IS_REGISTER_SUCCER, false);
                String phone = data.getStringExtra(RegisterActivity.RESULT_PHONE);
                String password = data.getStringExtra(RegisterActivity.RESULT_PASSWORD);
                if (isAutoLogin) {
                    startLogin(phone, password);
                } else {
                    mDataBinding.editAccount.setText("");
                    mDataBinding.editAccount.setText(phone);
                    mDataBinding.editPassword.setText(password);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {

        isUseDefaultTitle = false;
        Intent intent = getIntent();
        isExceptionLogin = intent.getBooleanExtra("is_exception", false);
        mExceptionMessage = intent.getStringExtra("message");

    }

    @Override
    protected void setupTitle() {

    }

    @Override
    protected void initViews() {

        checkIsExceptionLogin();
        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //mDataBinding.inputLayoutPassword.setPasswordVisibilityToggleEnabled(false);
        EditTextUtils.checkOnEditInputForButtonState(mContext, mDataBinding.btnLogin, mDataBinding.editAccount, mDataBinding.editPassword);
        String userName = UserUtils.getUserName(mContext);
        String password = UserUtils.getUserPassword(mContext);
        if (!TextUtils.isEmpty(userName)) {
            mDataBinding.editAccount.setText(userName);
            mDataBinding.editPassword.setText(password);
            if (TextUtils.isEmpty(password)) {
                mDataBinding.editPassword.setFocusable(true);
                mDataBinding.editPassword.requestFocus();
                mDataBinding.editPassword.setFocusableInTouchMode(true);
            }
        }
        mDataBinding.setPresenter(new Presenter());

        mDataBinding.editAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before > 0) {
                    if (s.length() <= 1) {
                        mDataBinding.editPassword.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkIsExceptionLogin() {
        if (isExceptionLogin) {
            JSONObject object = JSON.parseObject(mExceptionMessage);
            JSONObject infoObject = object.getJSONObject("info");
            long time = System.currentTimeMillis();
            if (infoObject.containsKey("addtime")) {
                time = infoObject.getLong("addtime");
            } else {
                time = time / 1000;
            }
            String cityName = "";
            if (infoObject.containsKey("cityname")) {
                cityName = infoObject.getString("cityname");
            }

            SweetAlertDialog dialog = DialogUtils.alertDialog(mContext, "登录异常", "检测到您于" + DateUtils.getDateToString2(time * 1000) + "在" + cityName + "登录，如果非本人登录请及时修改密码",
                    new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            ForgetPasswordActivity.actionStart(LoginActivity.this);
                        }
                    });
            dialog.setCancelText("是本人操作");
            dialog.setConfirmText("找回密码");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    private void startLogin(final String account, final String password) {
        DialogUtils.showProgressDialog(mContext, "正在登录中", "", "");
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.String
                ).login(account, password),
                new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "请求超时", Toast.LENGTH_SHORT).show();
                        hideDialogError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String resultStr = responseBody.string();
                            JSONObject object = JSON.parseObject(resultStr);
                            String status = object.getString("status");
                            if (status.equals(ApiService.STATUS_SUC)) {
                                JSONObject infoObject = object.getJSONObject("info");
                                mUser = JSON.parseObject(infoObject.toJSONString(), User.class);
                                UserUtils.putUserName(mContext, account);
                                UserUtils.putUserPassword(mContext, password);
                                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                                loginChat(mUser.getHxid(), password);
                                userBasicInfo(mUser.getToken(), mUser.getHxid());
                            } else {
                                showMessage(object.getString("info"));
                                hideDialogError();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            hideDialogError();
                        }
                    }
                }
        );

    }

    /**
     * 获取用户的基本信息
     *
     * @param token
     * @param hxid
     */
    private void userBasicInfo(String token, String hxid) {
        DialogUtils.showProgressDialog(mContext, "正在加载个人数据...", "", "");
        TaskBase.getUserDetails(token, hxid, new TaskBase.OnGetDataCallBack<HxUserBean>() {
            @Override
            public void onResult(HxUserBean userBean) {
                if (userBean != null) {
                    isUserInfoLoadSuc = true;
                    mUser.setAvatar(userBean.getAvatar());
                    mUser.setRealName(userBean.getRealname());
                    mUser.setPhone(userBean.getPhone());
                    saveUserBean();
                }
            }

            @Override
            public void onError(String err) {
                hideDialogError();
                showMessage("获取用户基本信息异常：" + err);
            }
        });

    }

    private void loginChat(final String account, final String password) {

        if (EMClient.getInstance().isConnected()) {
            ChatHelper.getInstance().logout(true, new EMCallBack() {
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            login(account, password);
                        }
                    });
                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        } else
            login(account, password);
    }

    private void login(String account, String password) {
        EMClient.getInstance().login(account, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().getOptions().setAutoLogin(true);
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isLoginChatSuc = true;
                        saveUserBean();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d("main", "进度 = " + progress + "--- 状态 = " + status);
            }

            @Override
            public void onError(int code, final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideDialogError();
                        showMessage(message);
                    }
                });

            }
        });
    }

    private void saveUserBean() {
        if (isLoginChatSuc && isUserInfoLoadSuc) {
            UserUtils.saveUser(mContext, mUser);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent().setAction(Constant.ACTION_PAGE_REFRESH));
            LoginHelper.getInstance().setOnline(true);
            TaskBase.uploadDeviceToken(LshlApplication.getApplication().getCurrentUserDeviceToken());
            //隐藏进度条
            DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
            Toast.makeText(mContext, "登录成功！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.putExtra(RegisterActivity.RESULT_IS_REGISTER_SUCCER, isRegisterSuccess);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        AppManager.getAppManager().finishAllActivity();
    }

    private void hideDialogError() {
        DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
    }
}
