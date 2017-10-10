package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityMyWealthBinding;
import com.lshl.ui.pay.SelectPayActivity;
import com.yunzhanghu.redpacketui.utils.RedPacketUtil;

/**
 * 我的财富
 */
public class MyWealthActivity extends BaseActivity<ActivityMyWealthBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyWealthActivity.class);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void change() {//零钱
            RedPacketUtil.startChangeActivity(mContext);
        }

        public void accountNumber() {//账号
            WealthAccountActivity.actionStart(MyWealthActivity.this);
        }

        public void record() {//记录
            CapitalRecordActivity.actionStart(MyWealthActivity.this);
        }

        public void mutual() {//互助还款
            MutualRepaymentActivity.actionStart(MyWealthActivity.this);
        }

        public void contribution() {//捐款
            SelectPayActivity.actionStart(MyWealthActivity.this);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("我的财富", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_wealth;
    }
}
