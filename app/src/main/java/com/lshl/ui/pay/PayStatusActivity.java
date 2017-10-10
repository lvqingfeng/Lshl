package com.lshl.ui.pay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.databinding.ActivityPayStatusBinding;
import com.lshl.ui.MainActivity;

import rx.Subscriber;

public class PayStatusActivity extends BaseActivity<ActivityPayStatusBinding> {

    public static final String PAY_STATUS = "pay_status";
    public static final String PAY_SN = "pay_sn";

    public static final int PAY_STATUS_SUCCEED = 1;//支付成功状态
    public static final int PAY_STATUS_FAIL = 3;//支付失败状态

    private int mPayStatus;
    private String mPaySn;


    public class Presenter {
        public void onClickGoHome() {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        mPayStatus = intent.getIntExtra(PAY_STATUS, -1);
        mPaySn = intent.getStringExtra(PAY_SN);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("支付状态", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    public void finish() {
        startActivity(new Intent(this, MainActivity.class));
        super.finish();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
        if (mPayStatus == PAY_STATUS_SUCCEED) {
            updatePayStatus();
        } else if (mPayStatus == PAY_STATUS_FAIL) {
            mDataBinding.ivSuc.setVisibility(View.GONE);
            mDataBinding.ivErr.setVisibility(View.VISIBLE);
            mDataBinding.tvPayStatus.setText("支付失败");
        }
    }

    private void updatePayStatus() {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).updatePayStataus(mPaySn)
                , new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        Log.d("lvzp", "支付成功,已成功通知后台");
                    }
                }
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_status;
    }
}
