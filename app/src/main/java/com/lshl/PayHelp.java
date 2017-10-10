package com.lshl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.lshl.pay_utils.alipay.AliPayUtils;
import com.lshl.pay_utils.alipay.AliPayWebActivity;
import com.lshl.pay_utils.wxpays.WXPayUtils;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * 作者：吕振鹏
 * 创建时间：12月02日
 * 时间：11:03
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class PayHelp {
    /**
     * 银联所需参数
     */
    // “00” – 银联正式环境
    // “01” – 银联测试环境，该环境中不发生真实交易
    private static final String UNION_PAY_MODE = "01";//银联开发环境设置
    private static final String R_SUCCESS = "success";
    private static final String R_FAIL = "fail";
    private static final String R_CANCEL = "cancel";
    private static final int PLUGIN_VALID = 0;
    private static final int PLUGIN_NOT_INSTALLED = -1;
    private static final int PLUGIN_NEED_UPGRADE = 2;

    private WeakReference<Activity> mCurrentActivity;

    private AliPayUtils mAliPayUtils;

    public PayHelp(Activity activity) {
        mCurrentActivity = new WeakReference<>(activity);
    }


    /**
     * 开启银联支付
     *
     * @param tn 订单号
     */
    public void openUnionPay(String tn) {
        // mMode参数解释：
        // 0 - 启动银联正式环境
        // 1 - 连接银联测试环境
        int ret = UPPayAssistEx.startPay(mCurrentActivity.get(), null, null, tn, UNION_PAY_MODE);
        if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
            // 需要重新安装控件
            //Log.e(LOG_TAG, " plugin not found or need upgrade!!!");

            AlertDialog.Builder builder = new AlertDialog.Builder(mCurrentActivity.get());
            builder.setTitle("提示");
            builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UPPayAssistEx.installUPPayPlugin(mCurrentActivity.get());
                            dialog.dismiss();
                        }
                    });

            builder.setPositiveButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();

        }
        // Log.e(LOG_TAG, "" + ret);
    }

    /**
     * 开启微信支付
     */
    public void openWxPay(String paySn, String price) {
        price = String.valueOf((int) (Float.parseFloat(price) * 100));
        WXPayUtils wxPay = new WXPayUtils(mCurrentActivity.get());
        wxPay.wxPay(paySn, price);
    }

    /**
     * 开启支付宝支付
     */
    public void openAliPay(final String vipNum, final AliPayWebActivity.PayType payType, final String paySn, final String price) {
        //getAlipayUtils().payV2();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AliPayWebActivity.actionStart(mCurrentActivity.get(), payType, price, vipNum);
            }
        }, 300);
    }

    public void onUnionPayActivityForResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        String str = data.getExtras().getString("pay_result");
        if (str != null && str.equalsIgnoreCase(R_SUCCESS)) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 验签证书同后台验签证书
                    // 此处的verify，商户需送去商户后台做验签
                    boolean ret = verify(dataOrg, sign, UNION_PAY_MODE);
                    if (ret) {
                        // 验证通过后，显示支付结果
                        showResultMessage("支付成功！");
                    } else {
                        // 验证不通过后的处理
                        // 建议通过商户后台查询支付结果
                        showResultMessage("支付失败！");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                showResultMessage("未收到签名信息");
            }
        } else if (str != null && str.equalsIgnoreCase(R_FAIL)) {
            showResultMessage(" 支付失败！ ");
        } else if (str != null && str.equalsIgnoreCase(R_CANCEL)) {
            showResultMessage(" 你已取消了本次订单的支付！ ");
        }
    }

    private void showResultMessage(String message) {
        Toast.makeText(mCurrentActivity.get(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;

    }

    private AliPayUtils getAlipayUtils() {
        if (mAliPayUtils == null) {
            mAliPayUtils = AliPayUtils.newInstance(mCurrentActivity.get());
        }
        return mAliPayUtils;
    }

}
