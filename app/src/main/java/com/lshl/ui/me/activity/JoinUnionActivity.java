package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityJoinUnionBinding;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import okhttp3.ResponseBody;

public class JoinUnionActivity extends BaseActivity<ActivityJoinUnionBinding> {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, JoinUnionActivity.class);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void joinNuion() {
            String ip = getIp();
            RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                    RetrofitManager.RetrofitType.String).joinUnion(LoginHelper.getInstance().getUserToken(), ip), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                @Override
                public void onNext(ResponseBody result) {
                    try {
                        String string = result.string();
                        JSONObject object = JSON.parseObject(string);
                        String status = object.getString("status");
                        String message = object.getString("message");
                        if ("1".equals(status)) {
//                            startActivity(new Intent(JoinUnionActivity.this,TimelyRainActivity.class));
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("及时雨", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_join_union;
    }

    /**
     * 获取ip地址
     */
    private String getIp() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {//wifi状态
            wifiManager.setWifiEnabled(true);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ip = int2ip(ipAddress);
            return ip;
        } else if (isGpsEnabled(mContext)) {//GPRS状态
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (SocketException ex) {

            }
        } else {
            Toast.makeText(mContext, "网络无连接,请连接您的网络", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
