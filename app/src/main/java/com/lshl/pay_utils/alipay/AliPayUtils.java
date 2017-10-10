package com.lshl.pay_utils.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lshl.pay_utils.OnPayResultListener;
import com.lshl.pay_utils.alipay.util.OrderInfoUtil2_0;

import java.util.Map;

/**
 * 作者：吕振鹏
 * 创建时间：12月15日
 * 时间：12:31
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class AliPayUtils {

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016120103671872";

    /**2016101002072824
     * 支付宝账户登录授权业务：入参pid值2016120103671872
     */
    public static final String PID = "2088421986649292";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /**
     * 商户私钥，pkcs8格式
     */
    public static final String RSA_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCkYD5ywWErALOyMLFmUOun9O71mPdYaSpURDU4hOWGCeN7g86kbAwDol4LbMTAzWWhACnkB9fv2IABaD63sC2p251OHSCWGCqNUANklfi8RJQ86zJwJ1mSsts1knDzOdli826Z498ZDUudP35Ma/AX4L7AfDhWJcaYFH84rXPenLR3pxXTZBjuMFpZz0Cy/WslDdAVJ+A5oiGcr+heCRMTQVfkI2/KepOanEaGXpMxX7VFFQIxk6KMh0gTjZHJhvsUcotb+V1hskRR2TcI4EnOR5D2cX9/lPM4IKOyOUeqKV0qbWD2cCktsl5X4kiyJK5olAG2g7tht1/ihBcaa+m5AgMBAAECgf8yFSDx1fnFIHGf2TYz9nPWuMoWCoVSarh69E1C6zsDm/+zonVTo5gFg3MkB//qvqo4Y9RfpgQlD9Hwm9KAJS3EkyVap4VGNhbA96ARBaDDvCFubjrv8Q1ZpSUlLXk8omOZwpHwUX2oJGTlhHL21exRWijEfRStKtVXgOdCXZbwPE7QvWIX0XWvUMkLIMstGQoRNfPTQC21FM/Nqm0qLrea7dJugeUOa5WodwrRAPLgsQu4omBwqpkUOgKh/iY3Ca7KqcRcmbSWej3UXZAlzmbeq+w7JwxCGbxIAcWmrZtyJWM5GTe2OVU20UIFmRvGO38xLqV/sl8gv2helJlsLPkCgYEA/qDrTfmENyFDVDuTav7oz/Pg02oDoabN86Ad1JuZNYVIEwLUBfsjKu+qzlboo1qzI+PZpBoNwWji4iMDKxK+zPQOWW19xzrVM2fvnIlGZnm0DAv/fE1TTWf3/1XHETSuASyV1Rz2wrewIZAAoRpuMn44gSe/zm9Qd9FXzeDpVzMCgYEApULig3oIvAwFvLwOa63fYtsFia3BxUZ9JITZhzAHOiYb+DWP+6bsR5cWjjxwJL2ZIG9o/qsS4fmNIXpgcykKKB9Bincfd/7g6IV1cW36i8c1BTw/3b7lxWFqG6MscSD1V3+HVK1btxMN0QAmJl7MkX8aZBDrvEqgObvg5sDQC2MCgYEAluJebSdFTLHeRuTCt210If4fJMg2fKzub8ZaxuipQgjHq9RKNX0a2s+IhJsFYf9WFhdnmZGCwzBW+a/LqdOC0spwUlfKZ6uaOMsHNtg1qnXF1jmp3Eup6D2KvHhk7PIthi6YXt+57aqRqWG+rbiOwkb/TrFyPG7D3Sxk/m+fdfcCgYEAnleEgubFQa/fO2OQCZoRxD0IhE8IrwUH2Jq1dbMN6agj+mEPsNXR5BIemW/NH1bOReaZWhD9yah6kc4YZZ63zZOND6dtbmK2ifN9gI4Ylh7rbTW6F7PVoZXhvdNNPKOQjyJwvzBPBQ1rmoojA3anWOzIk4Im7EUwWcDrDw1FCPMCgYBcSyccMGqUTghO2GokzgNKbClRlBquiV7Dy2+wzW10U7oQT0JBFRKFx3huOpprNYdaNlnVwCB9rWxXpMAtV6zyJrYbNl+F2ols+WAu4XNMAlWGCmg6NLnFz8rv1TftK/ASHN5VsGJCaTWgRq7Mjvni95mBxKoFMDtQxx0smMCPmA==";


    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private static AliPayUtils sAliPayUtils;

    private Context mContext;
    private OnPayResultListener mOnPayResultListener;


    private AliPayUtils(Context context) {
        mContext = context;
    }

    public static AliPayUtils newInstance(Context context) {

        if (sAliPayUtils == null) {
            sAliPayUtils = new AliPayUtils(context);
        }
        return sAliPayUtils;
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(mContext,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(mContext,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    public void setOnPayResultListener(OnPayResultListener listener) {
        mOnPayResultListener = listener;
    }

    /**
     * 支付宝支付业务
     */
    public void payV2() {

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
