package com.lshl.base;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.lshl.ChatHelper;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.camera.FileUtils;
import com.lshl.service.MyPushIntentService;
import com.lshl.task.TaskBase;
import com.lshl.ui.me.imagegrid.bean.ImageBucket;
import com.lshl.ui.me.imagegrid.utils.AlbumHelper;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yunzhanghu.redpacketsdk.RedPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月09日
 * 时间：14:42
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class LshlApplication extends Application {

    private static Application sApl;
    public static int sWindowWidth;
    public static int sWindowHeight;
    private static LshlApplication instance;
    private List<ImageBucket> mImageBucket;
    private AuthorityCheckBean mAuthorityBean;
    //用来存储用户当前的deviceToken;
    private String mCurrentUserDeviceToken;

    private boolean isUploadNewsTab;

    private IWXAPI msgApi;


    public IWXAPI getWxApi() {
        return msgApi;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApl = this;
        instance = this;
        init();
    }

    private void init() {

        try {
            msgApi = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, false);
            // 将该app注册到微信
            msgApi.registerApp(Constant.WX_APP_ID);
        }catch (NoClassDefFoundError e){
            e.printStackTrace();
        }

        //初始化获取文件存储位置的工具类
        FileUtils.getInstance().init(this);
        //初始化环信
        initEMClient();

        initWindowSize();

        LoginHelper.getInstance().init(sApl);
        //初始化环信
        ChatHelper.getInstance().init(LshlApplication.getInstance());

        initPhoto();
        //初始化友盟分享
        UMShareAPI.get(this);
        Config.REDIRECT_URL = "https://www.baidu.com";
        PlatformConfig.setWeixin("wxa914d1bc745be9b8", "f9828ab3b130997e962e4bb9eb04db90");
        PlatformConfig.setSinaWeibo("2315019814", "1e147e61667172bcd5218512a9abced2");
        PlatformConfig.setQQZone("1105775445", "86LypuWjP9n0m2jx");

        initPushAgent();
    }

    public void setUploadNewsTab(boolean isUploadNewsTab) {
        this.isUploadNewsTab = isUploadNewsTab;
    }

    public boolean isUploadNewsTab() {
        return isUploadNewsTab;
    }

    public String getCurrentUserDeviceToken() {
        return mCurrentUserDeviceToken;
    }

    /**
     * 初始化友盟推送代理
     */
    private void initPushAgent() {

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d("lvzp", deviceToken);
                if (deviceToken.equals(mCurrentUserDeviceToken))
                    return;
                mCurrentUserDeviceToken = deviceToken;
                TaskBase.uploadDeviceToken(mCurrentUserDeviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d("lvzp", "onFailure --- " + s + "---- s1 = " + s1);
            }
        });
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);/**/
    }

    public static LshlApplication getApplication() {
        return instance;
    }

    private void initPhoto() {
        AlbumHelper helper = AlbumHelper.getHelper();
        helper.init(this);
        mImageBucket = helper.getImagesBucketList(false);
    }

    private void initWindowSize() {

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        sWindowWidth = metrics.widthPixels;
        sWindowHeight = metrics.heightPixels;

    }

    public void setAuthorityBean(AuthorityCheckBean bean) {
        mAuthorityBean = bean;
    }

    public AuthorityCheckBean getAuthorityBean() {
        return mAuthorityBean;
    }

    public List<ImageBucket> getImageBucket() {
        if (mImageBucket == null)
            return new ArrayList<>();
        return mImageBucket;
    }

    /**
     * 环信的初始化
     */
    private void initEMClient() {
        //红包的初始化
        RedPacket.getInstance().initContext(sApl);
        //打开Log开关 正式发布时请关闭 红包的log
        RedPacket.getInstance().setDebugMode(false);
    }

    public static Application getInstance() {
        return sApl;
    }

    public static String getCurrentUserName() {
        return LoginHelper.getInstance().getHxId();
    }



}
