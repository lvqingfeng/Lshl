package com.lshl.ui.me.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.lshl.ChatHelper;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivitySettringBinding;
import com.lshl.ui.login.LoginActivity;
import com.lshl.utils.DialogUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SettingActivity extends BaseActivity<ActivitySettringBinding> {
    private String status;
    public static void actionStart(Activity activity, String status,int requestCode) {
        Intent intent = new Intent(activity, SettingActivity.class);
        intent.putExtra("status",status);
        activity.startActivityForResult(intent, requestCode);
    }


    public class Presenter {
        //使用说明
        public void onClickExplain(){
            UseExplainActivity.actionStart(SettingActivity.this);
        }
        public void onClickSuggest(){
            FeedBackSuggestActivity.actionStart(SettingActivity.this);
        }
        //退出登陆的操作
        public void exitLogin() {
            DialogUtils.alertDialog(mContext, "温馨提示", "确认退出当前账号?", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            }, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();

                    ChatHelper.getInstance().logout(true, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LoginHelper.getInstance().setOnline(false);
                                    LoginActivity.actionStart(SettingActivity.this, false, "");
                                    Toast.makeText(mContext, "退出成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onError(int i, final String s) {
                            // TODO Auto-generated method stub
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(mDataBinding.getRoot(), "退出异常：" + s, Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                }
            });
        }

        public void aboutUs() {//关于我们
            AboutUsActivity.actionStart(SettingActivity.this);
        }

        public void newMessage() {//新消息
            NewMessageActivity.actionStart(SettingActivity.this);
        }

        public void security() {//账号与安全
            SecurityActivity.actionStart(SettingActivity.this,status);
        }

        public void onClickBlackList() {//黑名单列表
            BlackListActivity.actionStart(SettingActivity.this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("设置", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        status=getIntent().getStringExtra("status");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settring;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == Constant.REQUEST_CODE_LOGIN) {
            if (resultCode != RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        }*/
    }
}
