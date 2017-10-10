package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.Capitalbean;
import com.lshl.databinding.ActivityDscsRankBinding;
import com.lshl.ui.pay.SelectPayActivity;

public class DscsRankActivity extends BaseActivity<ActivityDscsRankBinding> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, DscsRankActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("资金流向",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.Object).updateCapital(null),
                new ProgressSubscriber<>(mContext,
                        new SubscriberOnNextListener<Capitalbean>() {
            @Override
            public void onNext(Capitalbean result) {
                if (result!=null){
                    mDataBinding.all.setText("￥"+String.valueOf(result.getInfo().getAll()));
                    mDataBinding.welfare.setText("￥"+String.valueOf(result.getInfo().getGongyi()));
                    mDataBinding.forhelp.setText("￥"+String.valueOf(result.getInfo().getHuzhu()));
                    mDataBinding.balance.setText("￥"+String.valueOf(result.getInfo().getSurplus()));
                }

            }
        }));

        mDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPayActivity.actionStart(DscsRankActivity.this);
            }
        });
        mDataBinding.llMutual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MutualActivity.actionStart(DscsRankActivity.this,MutualActivity.FROM_SUCCESS);
            }
        });
        mDataBinding.llWelfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WelfareActivity.actionStart(DscsRankActivity.this,WelfareActivity.FROM_SUCCESS);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dscs_rank;
    }
}
