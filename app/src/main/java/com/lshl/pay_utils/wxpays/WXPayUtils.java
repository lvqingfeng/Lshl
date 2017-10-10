package com.lshl.pay_utils.wxpays;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.lshl.Constant;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.HttpResult;
import com.lshl.base.LshlApplication;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import rx.Subscriber;

public class WXPayUtils {

    private Context mContext;
    PayReq req;
    final IWXAPI msgApi;
    private String paySns;
    Map<String, String> resultunifiedorder;
    private static final String NOTIFY_URL = ApiService.BASE_URL + "Api/Wx/notify_url";
    public WXPayUtils(Context context) {

        mContext = context;
        req = new PayReq();

        msgApi = LshlApplication.getApplication().getWxApi();

    }


    public void wxPay(String paySn, String price) {
        paySns=paySn;
        boolean isPaySupported = msgApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
            GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
            getPrepayId.execute(paySn, price);
        } else {
            Toast.makeText(mContext, "您的手机暂不支持微信支付，请检测您是否已安装了微信客户端", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * 生成签名
     */
    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constant.WX_API_KEY);

        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion = sign", packageSign);
        return packageSign;
    }

    /**
     * 将Sign做最后的签名
     *
     * @param params
     * @return
     */
    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constant.WX_API_KEY);


        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        Log.e("orion 签名md5 =", appSign);
        return appSign;
    }

    /**
     * 将集合中的参数转换为xml格式的字符串
     *
     * @param params
     * @return
     */
    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("orion 生成后的参数 = ", sb.toString());
        return sb.toString();
    }

    /**
     * 用于请求微信后台统一下单的异步任务
     *
     * @author lzp
     */
    private class GetPrepayIdTask extends AsyncTask<String, Void, Map<String, String>> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(mContext, mContext.getString(R.string.app_tip),
                    mContext.getString(R.string.getting_prepayid));
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            dialog.dismiss();
            resultunifiedorder = result;
            if ("SUCCESS".equals(resultunifiedorder.get("return_code")) && "OK".equals(resultunifiedorder.get("return_msg"))) {
                genPayReq();
                sendPayReq();
                updateResult();
            } else {
                Toast.makeText(mContext, "订单提交失败：" + resultunifiedorder.get("return_msg"), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(String... params) {

            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

            String pay_sn = params[0];
            String price = params[1];

            // 获取需要上传的参数
            String entity = genProductArgs(pay_sn, price);

            Log.e("orion 参数 = ", entity);

            byte[] buf = Util.httpPost(url, entity);

            String content = new String(buf);
            Log.e("orion 返回 = ", content);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }
    }
    private void updateResult(){
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateweixinResule(paySns), new Subscriber<HttpResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpResult<String> result) {
                if (ApiService.STATUS_SUC.equals(result.getStatus())){

                }
            }
        });
    }
    /**
     * 将返回的xml格式的数据转换为Map集合
     *
     * @param content
     * @return
     */
    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }

    /**
     * 获取随机字符
     *
     * @return
     */
    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 获取时间绰
     *
     * @return
     */
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取订单号（随机码生成的）
     *
     * @return
     */
    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 获取需要上传的xml参数
     *
     * @return
     */
    private String genProductArgs(String pay_sn, String price) {

        try {
            String nonceStr = genNonceStr();//随机字符串

            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Constant.WX_APP_ID));
            packageParams.add(new BasicNameValuePair("body", pay_sn));//商品描述
            packageParams.add(new BasicNameValuePair("mch_id", Constant.WX_MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));//随机字符串
            packageParams.add(new BasicNameValuePair("notify_url", NOTIFY_URL));
            packageParams.add(new BasicNameValuePair("out_trade_no", pay_sn));//支付订单号
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new BasicNameValuePair("total_fee", price));//支付的价格（分）
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));

            String xmlstring = toXml(packageParams);

            return xmlstring;

        } catch (Exception e) {
            //Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }

    }

    private void genPayReq() {

        req.appId = Constant.WX_APP_ID;
        req.partnerId = Constant.WX_MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "prepay_id=" + resultunifiedorder.get("prepay_id");
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams).toUpperCase();


        Log.e("orion 再次处理，调起微信支付 = ", signParams.toString());

    }

    private void sendPayReq() {

        msgApi.registerApp(Constant.WX_APP_ID);
        msgApi.sendReq(req);
    }

}
