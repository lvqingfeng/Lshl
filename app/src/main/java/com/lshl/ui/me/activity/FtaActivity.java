package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityFtaBinding;

/**
 * 个人中心我的自贸区
 */
public class FtaActivity extends BaseActivity<ActivityFtaBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, FtaActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class Presenter {
        /**
         * 我的订单
         */
        public void myOrder() {
            MyOrderActivity.actionStart(FtaActivity.this);
        }

        /***
         * 我的关注
         **/
        public void myCollection() {
            MyCollectionActivity.actionStart(FtaActivity.this);
        }

        /***
         * 我的店铺
         */
        public void myShop() {
            MyGoodsActivity.actionStart(FtaActivity.this);
        }
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("自贸区", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fta;
    }
}
