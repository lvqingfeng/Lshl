package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.WealthAccountBean;
import com.lshl.databinding.ActivityWealthAccountBinding;

public class WealthAccountActivity extends BaseActivity<ActivityWealthAccountBinding> {
    private String weixin = "";
    private String zhifubao = "";

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, WealthAccountActivity.class);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void zhifubao() {
            ZhiFuBaoActivity.actionStart(WealthAccountActivity.this, zhifubao);
        }

        public void weixin() {
            WeiXinActivity.actionStart(WealthAccountActivity.this, weixin);
        }

        public void yinhangka() {
            BankNumActivity.actionStart(WealthAccountActivity.this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("财富账号", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData(LoginHelper.getInstance().getUserToken());
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        loadData(LoginHelper.getInstance().getUserToken());
    }

    private void loadData(String token) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).wealthAccount(token), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<WealthAccountBean>() {
            @Override
            public void onNext(WealthAccountBean result) {
                if (result != null) {
                    weixin = result.getInfo().getWa_wei();
                    zhifubao = result.getInfo().getWa_zhi();
                    mDataBinding.zhifubao.setText(zhifubao);
                    mDataBinding.weixin.setText(weixin);
                    mDataBinding.yinhangka.setText(result.getInfo().getWa_banknum());
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wealth_account;
    }
}
