package com.lshl.ui.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lshl.LoginHelper;
import com.lshl.PayHelp;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.PayBean;
import com.lshl.bean.PayZfbBean;
import com.lshl.databinding.ActivitySelectPayBinding;
import com.lshl.pay_utils.alipay.AliPayWebActivity;
import com.lshl.pay_utils.alipay.AuthResult;
import com.lshl.pay_utils.alipay.PayResult;
import com.lshl.pay_utils.alipay.util.OrderInfoUtil2_0;
import com.lshl.ui.me.activity.MemberCenterActivity;
import com.lshl.ui.me.activity.MemberRuleActivity;
import com.lshl.utils.CacheActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

public class SelectPayActivity extends BaseActivity<ActivitySelectPayBinding> {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private static final int TYPE_PAYMENT = 1;//缴费
    private static final int TYPE_DONATION = 2;//捐款
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQ" +
            "CkYD5ywWErALOyMLFmUOun9O71mPdYaSpURDU4hOWGCeN7g86kbAwDol4LbMTAzWWhACnkB9fv2IABaD63s" +
            "C2p251OHSCWGCqNUANklfi8RJQ86zJwJ1mSsts1knDzOdli826Z498ZDUudP35Ma/AX4L7AfDhWJcaYFH84" +
            "rXPenLR3pxXTZBjuMFpZz0Cy/WslDdAVJ+A5oiGcr+heCRMTQVfkI2/KepOanEaGXpMxX7VFFQIxk6KMh0g" +
            "TjZHJhvsUcotb+V1hskRR2TcI4EnOR5D2cX9/lPM4IKOyOUeqKV0qbWD2cCktsl5X4kiyJK5olAG2g7tht1" +
            "/ihBcaa+m5AgMBAAECgf8yFSDx1fnFIHGf2TYz9nPWuMoWCoVSarh69E1C6zsDm/+zonVTo5gFg3MkB//qv" +
            "qo4Y9RfpgQlD9Hwm9KAJS3EkyVap4VGNhbA96ARBaDDvCFubjrv8Q1ZpSUlLXk8omOZwpHwUX2oJGTlhHL2" +
            "1exRWijEfRStKtVXgOdCXZbwPE7QvWIX0XWvUMkLIMstGQoRNfPTQC21FM/Nqm0qLrea7dJugeUOa5Wodwr" +
            "RAPLgsQu4omBwqpkUOgKh/iY3Ca7KqcRcmbSWej3UXZAlzmbeq+w7JwxCGbxIAcWmrZtyJWM5GTe2OVU20U" +
            "IFmRvGO38xLqV/sl8gv2helJlsLPkCgYEA/qDrTfmENyFDVDuTav7oz/Pg02oDoabN86Ad1JuZNYVIEwLUB" +
            "fsjKu+qzlboo1qzI+PZpBoNwWji4iMDKxK+zPQOWW19xzrVM2fvnIlGZnm0DAv/fE1TTWf3/1XHETSuASyV" +
            "1Rz2wrewIZAAoRpuMn44gSe/zm9Qd9FXzeDpVzMCgYEApULig3oIvAwFvLwOa63fYtsFia3BxUZ9JITZhzA" +
            "HOiYb+DWP+6bsR5cWjjxwJL2ZIG9o/qsS4fmNIXpgcykKKB9Bincfd/7g6IV1cW36i8c1BTw/3b7lxWFqG6" +
            "MscSD1V3+HVK1btxMN0QAmJl7MkX8aZBDrvEqgObvg5sDQC2MCgYEAluJebSdFTLHeRuTCt210If4fJMg2f" +
            "Kzub8ZaxuipQgjHq9RKNX0a2s+IhJsFYf9WFhdnmZGCwzBW+a/LqdOC0spwUlfKZ6uaOMsHNtg1qnXF1jmp" +
            "3Eup6D2KvHhk7PIthi6YXt+57aqRqWG+rbiOwkb/TrFyPG7D3Sxk/m+fdfcCgYEAnleEgubFQa/fO2OQCZo" +
            "RxD0IhE8IrwUH2Jq1dbMN6agj+mEPsNXR5BIemW/NH1bOReaZWhD9yah6kc4YZZ63zZOND6dtbmK2ifN9gI" +
            "4Ylh7rbTW6F7PVoZXhvdNNPKOQjyJwvzBPBQ1rmoojA3anWOzIk4Im7EUwWcDrDw1FCPMCgYBcSyccMGqUT" +
            "ghO2GokzgNKbClRlBquiV7Dy2+wzW10U7oQT0JBFRKFx3huOpprNYdaNlnVwCB9rWxXpMAtV6zyJrYbNl+F" +
            "2ols+WAu4XNMAlWGCmg6NLnFz8rv1TftK/ASHN5VsGJCaTWgRq7Mjvni95mBxKoFMDtQxx0smMCPmA==";

    public static final String APPID = "2016101002072824";
    public static final String PAY_STATUS = "status";
    /**
     * 输入框小数的位数
     */
    private static final int DECIMAL_DIGITS = 2;

    private static final double VipPrice = 120.00;

    private PayHelp mPayHelp;
    private PayType mPayType = PayType.ALI_PAY;

    private boolean isBuyVip;
    private String mVipPrice;
    private String mPaySn;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SelectPayActivity.class);
        activity.startActivity(intent);
    }

    public static void actionStart(Activity activity, boolean isBuyVip, String vipPrice) {
        Intent intent = new Intent(activity, SelectPayActivity.class);
        intent.putExtra("is_buy_vip", isBuyVip);
        intent.putExtra("vip_price", vipPrice);
        activity.startActivity(intent);
    }

    public enum PayType {
        UNION_PAY, WX_PAY, ALI_PAY
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(SelectPayActivity.this, "恭喜您购买成功", Toast.LENGTH_LONG).show();
                        finish();
                        CacheActivity.finishSingleActivityByClass(MemberRuleActivity.class);
                        CacheActivity.finishSingleActivityByClass(MemberCenterActivity.class);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(SelectPayActivity.this, "购买结果确认中", Toast.LENGTH_LONG).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(SelectPayActivity.this, "购买失败", Toast.LENGTH_LONG).show();

                        }
                        finish();
                        CacheActivity.finishSingleActivityByClass(MemberRuleActivity.class);
                        CacheActivity.finishSingleActivityByClass(MemberCenterActivity.class);
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
                        Toast.makeText(SelectPayActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(SelectPayActivity.this,
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


    public class Presenter {

        public void onClickStartPay() {
            String price = "0";
            String type = "";
            String nums = "";
            if (isBuyVip) {
                type = "1";
                price = mVipPrice;
                nums = String.valueOf((int) Float.parseFloat(mVipPrice) / VipPrice);
            } else {
                type = "2";
                price = mDataBinding.editInputMoney.getText().toString();
            }
            final String finalPrice = price;
            if (TextUtils.isEmpty(finalPrice)) {
                showMessage("请填写支付金额");
                return;
            }
            final String finalNums = nums;
            if (mPayType == PayType.ALI_PAY) {
                AliPayWebActivity.PayType types = null;
//                if (isBuyVip) {
//                    types = AliPayWebActivity.PayType.PAY_MEMBERSHIP;
//                } else {
//                    types = AliPayWebActivity.PayType.DONATION;
//                }
////                loadDingdan(finalPrice,type,nums);
//                mPayHelp.openAliPay(finalNums, types, mPaySn, finalPrice);

                loadDingdan(finalPrice, TYPE_PAYMENT, finalNums);

            } else
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.Object
                        ).preOrder(LoginHelper.getInstance().getUserToken(), finalPrice, type, nums)
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<PayBean>>() {
                            @Override
                            public void onNext(HttpResult<PayBean> result) {
                                mPaySn = result.getInfo().getOrderno();
                                switch (mPayType) {
                                    case WX_PAY:
                                        mPayHelp.openWxPay(mPaySn, finalPrice);
                                        break;
                                    case ALI_PAY:
//                                        AliPayWebActivity.PayType types = null;
//                                        if (isBuyVip) {
//                                            types = AliPayWebActivity.PayType.PAY_MEMBERSHIP;
//                                        } else {
//                                            types = AliPayWebActivity.PayType.DONATION;
//                                        }
//                                        mPayHelp.openAliPay(finalNums, types, mPaySn, finalPrice);

                                        loadDingdan(finalPrice, TYPE_PAYMENT, finalNums);
                                        break;
                                    case UNION_PAY:
                                        break;
                                }
                            }
                        })
                );
        }
    }

    private void loadDingdan(final String money, int type, String nums) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).updatePaymentResult(LoginHelper.getInstance().getUserToken(), type, money, nums), new Subscriber<PayZfbBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final PayZfbBean payZfbBean) {
                if (payZfbBean.getStatus() == 1) {

                    String orderNo = payZfbBean.getInfo().getOrderno();
                    String notifyUrl = payZfbBean.getInfo().getNotify_url();
                    String privateKey = payZfbBean.getInfo().getPrivate_key();
                    pay("鲁商互联会员", "会员购买-鲁商互联会员中心", money, orderNo, notifyUrl);
                }
            }
        });
    }


    /**
     * App支付
     *
     * @param subject
     * @param body
     * @param total_amount
     * @param out_trade_no
     * @param notify_url
     */
    private void pay(String subject, String body, String total_amount, String out_trade_no, String notify_url) {
        Map<String, String> params = buildOrderParamMap(APPID, total_amount, subject, body, out_trade_no, notify_url);
        String orderParam = buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(SelectPayActivity.this);
                // 调用支付接口，获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        //进入这个页面是为了获取订单号的
        int payStatus = -1;
        int errCode = intent.getIntExtra(PAY_STATUS, -1);
        if (errCode == 0) {
            payStatus = PayStatusActivity.PAY_STATUS_SUCCEED;
        } else {
            payStatus = PayStatusActivity.PAY_STATUS_FAIL;
        }
        Intent intents = new Intent(this, PayStatusActivity.class);
        intents.putExtra(PayStatusActivity.PAY_STATUS, payStatus);
        intents.putExtra(PayStatusActivity.PAY_SN, mPaySn);
        startActivity(intents);
        finish();
    }

    @Override
    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        isBuyVip = intent.getBooleanExtra("is_buy_vip", false);
        mVipPrice = intent.getStringExtra("vip_price");
        mPayHelp = new PayHelp(this);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("支付中心", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.radioGroupPay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_union_pay:
                        mPayType = PayType.UNION_PAY;
                        break;
                    case R.id.radio_ali_pay:
                        mPayType = PayType.ALI_PAY;
                        break;
                    case R.id.radio_wx_pay:
                        mPayType = PayType.WX_PAY;
                        break;
                }
            }
        });
        mDataBinding.radioAliPay.setChecked(true);
        mDataBinding.editInputMoney.setFilters(new InputFilter[]{lengthfilter});
        mDataBinding.setPresenter(new Presenter());

        if (isBuyVip) {
            mDataBinding.editInputMoney.setVisibility(View.GONE);
            mDataBinding.llVipPay.setVisibility(View.VISIBLE);
            mDataBinding.tvInputMoney.setText(mVipPrice);
        }

    }

    /**
     * 构造支付订单参数列表
     *
     * @param app_id
     * @return
     */
    public static Map<String, String> buildOrderParamMap(String app_id, String total_amount, String subject, String body, String out_trade_no, String notify_url) {
        Map<String, String> keyValues = new HashMap<String, String>();

        keyValues.put("app_id", app_id);
        keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSEC" +
                "URITY_PAY\",\"total_amount\":\"" + total_amount + "\",\"subject\":\"" + subject +
                "\",\"body\":\"" + body + "\",\"out_trade_no\":\"" + out_trade_no + "\"}");
        keyValues.put("charset", "utf-8");
        keyValues.put("method", "alipay.trade.app.pay");
        keyValues.put("sign_type", "RSA");
        keyValues.put("timestamp", getNowDate());
        keyValues.put("version", "1.0");
        keyValues.put("notify_url", notify_url);
        return keyValues;
    }

    /**
     * 构造支付订单参数信息
     *
     * @param map 支付订单参数
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_pay;
    }

    /**
     * 设置小数位数控制
     */
    InputFilter lengthfilter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            // 删除等特殊字符，直接返回
            if ("".equals(source.toString())) {
                return null;
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
                if (diff > 0) {
                    return source.subSequence(start, end - diff);
                }
            }
            return null;
        }
    };

}
