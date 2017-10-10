package com.lshl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.ui.MainActivity;
import com.lshl.ui.login.LoginActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 开屏页
 */
public class SplashActivity extends AppCompatActivity {

    private static final int sleepTime = 2400;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
        TextView versionText = (TextView) findViewById(R.id.tv_version);

        versionText.setText(getVersion()+"");
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        rootLayout.startAnimation(animation);

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkIsLogin();
       /* PermissionManager.applyPermission(this, new PermissionManager.OnApplyPermissionCallback() {
            @Override
            public void onSuccess() {

            }
        });*/
    }

    private void checkIsLogin() {
        if (LoginHelper.getInstance().isOnline()) {//检测本地存储的登陆信息是否还存在，如果存在表示处于登陆状态
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.String
                    ).tokenIsOk(LoginHelper.getInstance().getUserBean().getUid(), LoginHelper.getInstance().getUserToken())
                    , new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            startLogin(false, "");
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String resultStr = responseBody.string();
                                JSONObject object = JSON.parseObject(resultStr);
                                String status = object.getString("status");


                                if (status.equals(ApiService.STATUS_SUC)) {//判断目前本地存储的token是否和网络中的token相同


                                    new Thread(new Runnable() {
                                        public void run() {

                                            //检测环信是否登陆成功
                                            if (ChatHelper.getInstance().isLoggedIn()) {
                                                if (EMClient.getInstance().isConnected()) {
                                                    // auto login mode, make sure all group and conversation is loaed before enter the main screen
                                                    long start = System.currentTimeMillis();
                                                    EMClient.getInstance().groupManager().loadAllGroups();
                                                    EMClient.getInstance().chatManager().loadAllConversations();
                                                    long costTime = System.currentTimeMillis() - start;
                                                    //wait
                                                    if (sleepTime - costTime > 0) {
                                                        try {
                                                            Thread.sleep(sleepTime - costTime);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    //enter main screen
                                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                                    finish();
                                                } else {
                                                    ChatHelper.getInstance().login(new EMCallBack() {
                                                        @Override
                                                        public void onSuccess() {
                                                            //enter main screen
                                                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                                            finish();
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    EMClient.getInstance().getOptions().setAutoLogin(true);
                                                                    Toast.makeText(SplashActivity.this, "聊天服务器已登陆成功", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }

                                                        @Override
                                                        public void onError(int i, String s) {
                                                            startLogin(false, "");
                                                        }

                                                        @Override
                                                        public void onProgress(int i, String s) {

                                                        }
                                                    });

                                                }
                                            } else {
                                                if (LoginHelper.getInstance().isOnline()) {
                                                    LoginHelper.getInstance().setOnline(false);
                                                }
                                                startLogin(false, "");
                                            }
                                        }
                                    }).start();

                                } else {
                                    ChatHelper.getInstance().checkIsLogout();
                                    LoginHelper.getInstance().setOnline(false);
                                    startLogin(true, resultStr);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                startLogin(false, "");
                            }
                        }
                    }
            );

        } else {
            startLogin(false, "");
        }
    }

    /**
     * 进入等待界面，然后跳转到登陆页面
     */
    private void startLogin(final boolean isExceptionLogin, final String message) {
        if (isExceptionLogin){
            ChatHelper.getInstance().checkIsLogout();
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //startActivity(new Intent(SplashActivity.this, MainActivity.class));
                LoginActivity.actionStart(SplashActivity.this, isExceptionLogin, message);
                // startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }.start();
    }


    /**
     * get sdk version
     */
    private String getVersion() {
        return EMClient.getInstance().getChatConfig().getVersion();
    }
}
