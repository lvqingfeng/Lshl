package com.lshl.pay_utils.alipay;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityAliPayWebBinding;

public class AliPayWebActivity extends BaseActivity<ActivityAliPayWebBinding> {

    private PayType mPayType;
    private String mMoney;
    private String mVipNum;

    public static void actionStart(Activity activity, PayType type, String money, String vipNum) {
        Intent intent = new Intent(activity, AliPayWebActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("money", money);
        intent.putExtra("vip_num", vipNum);
        activity.startActivity(intent);
    }

    public enum PayType {
        PAY_MEMBERSHIP(1),//1交会费
        DONATION(2);//2捐款
     /*   COMMONWEAL(3),//3公益支出
        HELP(4),//4互助支出
        HELP_DONATION(5),// 5互助捐款
        HELP_REPAYMENT(6);//6互助还款*/

        private int value;

        PayType(int value) {
            this.value = value;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initFieldBeforeMethods() {

        Intent intent = getIntent();
        mPayType = (PayType) intent.getSerializableExtra("type");
        mMoney = intent.getStringExtra("money");
        mVipNum = intent.getStringExtra("vip_num");

    }

    @Override
    protected void setupTitle() {
        setTextTitleView("支付界面", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        String url = ApiService.ALIPAY_WEB
                + "/token/" + LoginHelper.getInstance().getUserToken()
                + "/money/" + mMoney
                + "/type/" + mPayType.value
                + "/nums/" + mVipNum;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ali_pay_web;
    }
}
