package com.lshl.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.ChatHelper;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.AddressBean;
import com.lshl.bean.UpdateAppBean;
import com.lshl.databinding.ActivityMainBinding;
import com.lshl.service.DownloadService;
import com.lshl.ui.fragment.ApplianceFragment;
import com.lshl.ui.fragment.BusinBringFragment;
import com.lshl.ui.fragment.HomepageFragment;
import com.lshl.ui.fragment.InfoFragment;
import com.lshl.ui.fragment.LoginAlertFragment;
import com.lshl.ui.fragment.MeFragment;
import com.lshl.ui.fragment.NewsFragment;
import com.lshl.ui.fragment.ServiceFragment;
import com.lshl.ui.login.LoginActivity;
import com.lshl.ui.login.RegisterActivity;
import com.lshl.ui.me.activity.CreateCardsActivity;
import com.lshl.utils.AppBottomLayoutManager;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.JsonFormFileUtils;
import com.lshl.utils.SharedPreferencesUtils;
import com.lshl.view.HomeTabItem;
import com.lshl.view.NewsTabLayout;
import com.yunzhanghu.redpacketui.RedPacketConstant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import q.rorbin.badgeview.QBadgeView;
import rx.Subscriber;

import static com.umeng.socialize.Config.dialog;

public class MainActivity extends BaseActivity<ActivityMainBinding>
        implements BusinBringFragment.BusinBringFragmentCallback, NewsFragment.NewsCallback
        , MeFragment.MeCallback {
    private long exitTime = 0;

    private AppBottomLayoutManager mBottomLayoutManager;
    private LoginAlertFragment mLoginFragment;

    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;
    private String mCityName;
    //商汇界面右上角的点击事件
    private View.OnClickListener mBusinBringRightViewOnClickListener;
    private View mInfoTitleView;
    public TabLayout mInfoTabLayout;
    public NewsTabLayout mNewsTabGroup;
    private View mNewTitleView;
    private View.OnClickListener mNewsRightViewOnClickListener;
    private View.OnClickListener mMeRightViewOnClickListener;
    private BaiduMapLocationHelper mLocationHelper;
    private String mProvence;
    private String mCity;
    private String mCityNo;
    private List<AddressBean> mAddressData;
    private boolean isRegisterSuccess = false;
    private double longitude;
    private double latitude;
    private int versionName;
    private String apkUrl;
    private int newCode;
    private int minCode;
    public static final int FORCE_UPDATE_FLAG = 0;
    public static final int NORMAL_UPDATE_FLAG = 1;
    private String message;
    private QBadgeView qBadgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void setupTitle() {

    }

    public int getUnreadMessageTotal(){
        int unReadMsgToatl=0;
        int chatRoomMSg=0;
        unReadMsgToatl = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        for (EMConversation conversation:EMClient.getInstance().chatManager().getAllConversations().values()){
            if (conversation.getType()== EMConversation.EMConversationType.ChatRoom){
                chatRoomMSg=chatRoomMSg+conversation.getUnreadMsgCount();
            }
        }
        return unReadMsgToatl-chatRoomMSg;
    }
    private  void updateNumsHint(){
        qBadgeView.bindTarget(mDataBinding.info);
        qBadgeView.setBadgeBackgroundColor(ContextCompat.getColor(mContext,R.color.red));
        qBadgeView.setBadgeNumber(getUnreadMessageTotal());
        qBadgeView.setBadgeTextSize(6,true);
        qBadgeView.setGravityOffset(18,2,true);
        if (getUnreadMessageTotal()==0){
            qBadgeView.setBadgeNumber(0);
        }
    }

    @Override
    protected void initViews() {
        qBadgeView = new QBadgeView(mContext);
        //注册消息的监听
        registerBroadcastReceiver();
        mDataBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                CheckBox rightView = getRightView();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (i){
                    case R.id.main_shouye:
                        rightView.setVisibility(View.GONE);
                        setTextTitleView("首页",DEFAULT_TITLE_TEXT_COLOR);
                        transaction.replace(R.id.main_content,HomepageFragment.newInstance());
                        transaction.commit();
                        break;
                    case R.id.main_service:
                        rightView.setVisibility(View.GONE);
                        setTextTitleView("服务",DEFAULT_TITLE_TEXT_COLOR);
                        transaction.replace(R.id.main_content,ServiceFragment.newInstance());
                        transaction.commit();
                        break;
                    case R.id.main_info:
                        if (!LoginHelper.getInstance().isOnline()){
                            Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
                           return;
                        }
                        rightView.setVisibility(View.GONE);
                        setTitleCenterView(mInfoTitleView, false);
                        transaction.replace(R.id.main_content,InfoFragment.newInstance());
                        transaction.commit();
                        break;
                    case R.id.main_faxian:
                        rightView.setVisibility(View.GONE);
                        setTextTitleView("发现",DEFAULT_TITLE_TEXT_COLOR);
                        transaction.replace(R.id.main_content,ApplianceFragment.newInstance());
                        transaction.commit();
                        break;
                    case R.id.main_person:
                        setTextTitleView("个人",DEFAULT_TITLE_TEXT_COLOR);
                        rightView.setVisibility(View.VISIBLE);
                        rightView.setText("");
                        rightView.setButtonDrawable(R.drawable.ic_vector_notify);
                        rightView.setVisibility(View.VISIBLE);
                        if (mMeRightViewOnClickListener != null) {
                            rightView.setOnClickListener(mMeRightViewOnClickListener);
                        }
                        transaction.replace(R.id.main_content,MeFragment.newInstance());
                        transaction.commit();
                        break;
                }
            }
        });
        initNewsTabLayout();
        initInfoTabLayout();
        initBottomLayout();
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_NOTIFY);
        intentFilter.addAction(Constant.ACTION_CONTACT_REFRESU);
        intentFilter.addAction(Constant.ACTION_NEW_CONTACT_NOTIFY);
        intentFilter.addAction(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION);
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateNumsHint();
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * 切换当前地区
     */
    private void switchCurrentArea(String provinceName, String locationArea) {

        int provincePosition = 0;
        for (int i = 0; i < mAddressData.size(); i++) {
            AddressBean listAddress = mAddressData.get(i);
            if (listAddress.getCityname().equals(provinceName)) {
                provincePosition = i;
                break;
            }
        }
        List<AddressBean.SubBean> subList = mAddressData.get(provincePosition).getSub();
        for (AddressBean.SubBean subBean : subList) {
            if (subBean.getCityname().equals(locationArea)) {
                mCityNo = "0";
                mCityNo = "0";
                return;
            }
        }
        Toast.makeText(mContext, "未匹配到当前地区", Toast.LENGTH_SHORT).show();
    }

    private void initRadarData() {
        mLocationHelper = BaiduMapLocationHelper.getInstance().init(mContext);
        mLocationHelper.setLocationCallBack(new BaiduMapLocationHelper.OnLocationCallBack() {

            @Override
            public void callBackLatLng(BDLocation location, LatLng latLng) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                mProvence = location.getProvince();
                mCity = location.getCity();
//                switchCurrentArea(mProvence, mCity);
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .radarSendRequest(LoginHelper.getInstance().getUserToken()
                                , mCityNo, mCity), new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("lvzp", "完成。");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("lvzp", e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<String> result) {
                        Log.d("lvzp", result.getInfo());
                    }
                });
            }
        });
        mLocationHelper.locationStart();
        if (LoginHelper.getInstance().getUserBean().getUid() != null) {
            updateJingwei(longitude, latitude);
        }
    }
    private void initNewsTabLayout() {
        mNewTitleView = getLayoutInflater().inflate(R.layout.layout_title_news, null);
        mNewsTabGroup = (NewsTabLayout) mNewTitleView.findViewById(R.id.radio_group);
    }

    private void initInfoTabLayout() {
        mInfoTitleView = getLayoutInflater().inflate(R.layout.layout_title_tab_layout, null);
        mInfoTabLayout = (TabLayout) mInfoTitleView.findViewById(R.id.tab_layout);
    }

    @Override
    protected void initFieldBeforeMethods() {
        boolean isOpenRadar = (boolean) SharedPreferencesUtils.getParam(mContext, Constant.FRIEND_RADAR_KEY, false);
        if (isOpenRadar) {
            mAddressData = new ArrayList<>();
            try {
                mAddressData.addAll(JsonFormFileUtils.getJsonArray(mContext, "address.json", AddressBean.class));
            } catch (IOException e) {
                e.printStackTrace();
            }

            initRadarData();
        }
        isRegisterSuccess = getIntent().getBooleanExtra(RegisterActivity.RESULT_IS_REGISTER_SUCCER, false);
        isNeedPerfectBasicInfo();

        //检查更新app
        checkNeedUpdateApp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("mains", "主页重新被打开");
        boolean isConflict = intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false);
        isRegisterSuccess = intent.getBooleanExtra(RegisterActivity.RESULT_IS_REGISTER_SUCCER, false);
        isNeedPerfectBasicInfo();
        if (isConflict) {
            DialogUtils.alertDialog(mContext, "温馨提示", "当前账号已在另外一台设备上登陆", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    logout(sweetAlertDialog);
                }
            }, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    logout(sweetAlertDialog);
                }
            });
        }/**/
    }

    private void isNeedPerfectBasicInfo() {
        if (isRegisterSuccess) {
            CreateCardsActivity.actionStart(this);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateNumsHint();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNumsHint();
    }

    @Override
    protected void
    onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateNumsHint();
        switch (requestCode) {
            /*case Constant.REQUEST_CODE_LOGIN:
                if (resultCode == RESULT_OK) {
                    mBottomLayoutManager.isOnline = true;
                    mBottomLayoutManager.setCurrentItem(mBottomLayoutManager.getSelectItemPosition());
                }
                break;*/
            case Constant.REQUEST_CODE_LOGOUT:
                if (resultCode == RESULT_OK) {
                    mBottomLayoutManager.isOnline = false;
                    mBottomLayoutManager.setCurrentItem(mBottomLayoutManager.getSelectItemPosition());
                }
                break;
        }
    }

    /**
     * 退出登陆
     *
     * @param dialog
     */
    private void logout(SweetAlertDialog dialog) {
        dialog.dismissWithAnimation();
        ChatHelper.getInstance().checkIsLogout();
        LoginHelper.getInstance().setOnline(false);
        LoginActivity.actionStart(MainActivity.this, false, "");
    }

    public CheckBox getRightViews() {
        return getRightView();
    }
    public TextView getPersonBadgeView(){
        return mDataBinding.person;
    }
    public QBadgeView getInfoBadgeView(){
        return qBadgeView;
    }
    public ImageView getLeftViews() {
        return getTitleBackIcon();
    }

    private void initBottomLayout() {

        mLoginFragment = LoginAlertFragment.newInstance();

        mBottomLayoutManager = new AppBottomLayoutManager(mContext, mDataBinding.bottomLayout, R.id.main_content);

        mBottomLayoutManager.setLoginFragment(new AppBottomLayoutManager.AppLoginFragment() {
            @Override
            public Fragment getLoginFragment() {
                return mLoginFragment;
            }
        });

        mBottomLayoutManager.addFragment(0, HomepageFragment.newInstance());
        mBottomLayoutManager.addFragment(1, ServiceFragment.newInstance());
        mBottomLayoutManager.addFragment(2, InfoFragment.newInstance());
        mBottomLayoutManager.addFragment(3, ApplianceFragment.newInstance());
        mBottomLayoutManager.addFragment(4, MeFragment.newInstance());

        mBottomLayoutManager.setTabItem(0, R.drawable.ic_vector_main_news, R.drawable.ic_vector_main_news_unselect, "首页", false);
        mBottomLayoutManager.setTabItem(1, R.drawable.ic_vector_main_busin_bring, R.drawable.ic_vector_main_busin_bring_unselect, "服务", false);
        mBottomLayoutManager.setTabItem(2, R.drawable.ic_vector_main_info, R.drawable.ic_vector_main_info_unselect, "聊天", true);
        mBottomLayoutManager.setTabItem(3, R.drawable.ic_vector_main_appliance, R.drawable.ic_vector_main_appliance_unselect, "发现", false);
        mBottomLayoutManager.setTabItem(4, R.drawable.ic_vector_main_me, R.drawable.ic_vector_main_me_unselect, "个人", true);

        mBottomLayoutManager.setOnItemClickChangeListener(new AppBottomLayoutManager.OnItemClickChangeListener() {
            @Override
            public boolean onItemClick(ViewGroup bottomLayout, View item, int position) {

                mBottomLayoutManager.isOnline = LoginHelper.getInstance().isOnline();
                return false;
            }

            @Override
            public void onItemClickChanger(ViewGroup bottomLayout, View item, int position) {
                CheckBox rightView = getRightView();
                rightView.setVisibility(View.GONE);
                LinearLayout leftLayout = getLeftLayoutTitle();
                leftLayout.setVisibility(View.GONE);
                ImageView leftView = getLeftViews();
                leftView.setVisibility(View.GONE);
                if (position == 0) {
                    setTextTitleView("首页",DEFAULT_TITLE_TEXT_COLOR);
                    rightView.setVisibility(View.VISIBLE);
                    return;
                } else if (position == 1) {
                } else if (position == 2) {
                    setTitleCenterView(mInfoTitleView, false);
                    return;
                } else if (position == 4) {
                    rightView.setText("");
                    rightView.setButtonDrawable(R.drawable.ic_vector_notify);
                    rightView.setVisibility(View.VISIBLE);
                    if (mMeRightViewOnClickListener != null) {
                        rightView.setOnClickListener(mMeRightViewOnClickListener);
                    }
                }
                setTextTitleView(((HomeTabItem) item).getTitleText(), DEFAULT_TITLE_TEXT_COLOR);
            }

            @Override
            public void onItemReselected(ViewGroup bottomLayout, View item, int position) {

            }
        });

        mBottomLayoutManager.setCurrentItem(0);//设置默认选中的条目
    }

    @Override
    public void callBackRightViewClickListener(View.OnClickListener listener) {
        mBusinBringRightViewOnClickListener = listener;
    }

    @Override
    public void callBackCityName(String cityName) {
        mCityName = "全国";
    }

    @Override
    public void rightViewOnClick(View.OnClickListener listener) {
        mNewsRightViewOnClickListener = listener;
    }

    @Override
    public void onNotifyClick(View.OnClickListener listener) {
        mMeRightViewOnClickListener = listener;
    }

    /****
     * 再按一次退出程序  时间间隔不能超过2秒   防止误触发返回键导致进程终止
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(mContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /****
     * 如果注册成功 获取到Uid的 打开App就更新经纬度
     */
    private void updateJingwei(double jingdu, double weidu) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updatejingwei(LoginHelper.getInstance().getUserBean().getUid()
                , String.valueOf(jingdu) + "," + String.valueOf(weidu))
                , new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<String> result) {
                    }
                });
    }

    /**
     * 检查更新App
     *
     * @return
     */

    private void checkNeedUpdateApp() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .checkVersionUpdate("an"), new Subscriber<UpdateAppBean>() {

            private AlertDialog.Builder builder;

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("TAG", "updateMe--" + e.getMessage());
            }

            @Override
            public void onNext(UpdateAppBean result) {
                if (result.getStatus() == 1) {
                    newCode = Integer.parseInt((result.getInfo().getNew_code()).replace(".", ""));
                    minCode = Integer.parseInt((result.getInfo().getMin_code()).replace(".", ""));
                    apkUrl = result.getInfo().getUrl();
                    message = result.getInfo().getMessage();
                    versionName = Integer.parseInt((getVersionName(mContext)).replace(".", ""));
                    if (TextUtils.isEmpty(message)) {
                        message = "鲁商互联有新版本，请您更新";
                    }

                    if (versionName < newCode) {
                        if (versionName <= minCode) {
                            //强制更新
                            dialog = DialogUtils.createUpdateDialog(mContext, "更新提示 " + "(v" +
                                            result.getInfo().getNew_code() + ")", message, "立即更新", "",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DownloadService.actionStart(mContext, apkUrl);
                                            showUpdateToast();
                                        }
                                    }, null, FORCE_UPDATE_FLAG);
                            dialog.show();
                        } else {
                            //非强制更新
                            dialog = DialogUtils.createUpdateDialog(mContext, "更新提示 " + "(v" +
                                            result.getInfo().getNew_code() + ")", message, "立即更新", "暂不更新",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DownloadService.actionStart(mContext, apkUrl);
                                            showUpdateToast();
                                        }
                                    }, new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }, NORMAL_UPDATE_FLAG);
                            dialog.show();
                        }
                    }
                }
            }

            private void showUpdateToast() {
                Toast.makeText(mContext, "文件开始下载...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 获取当前版本号
    public String getVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(
                    "com.lshl", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // Log.e("msg", e.getMessage());
        }
        return versionName;
    }
    private void loadUnreadNum(){
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getPushUnreadNum(LoginHelper.getInstance().getUserToken())
                , new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        String unreadNum = stringHttpResult.getInfo();
                        QBadgeView badgeView = new QBadgeView(mContext);
                        if (!unreadNum.equals("0")) {
                            badgeView.bindTarget(mDataBinding.person);
                            badgeView.setBadgeNumber(Integer.parseInt(unreadNum));
                            badgeView.setBadgeTextSize(8,true);
                            badgeView.setGravityOffset(18,2,true);
                        } else {
                            badgeView.setBadgeNumber(Integer.parseInt("3"));
                        }
                    }
                }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
