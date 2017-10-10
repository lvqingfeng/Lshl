package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityReportBinding;
import com.lshl.bean.ResultInfoBean;

public class ReportActivity extends BaseActivity<ActivityReportBinding> {
    private int gid;

    public static void actionStart(Activity activity, int gid) {
        Intent intent = new Intent(activity, ReportActivity.class);
        intent.putExtra("gid", gid);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void report() {
            if (mDataBinding.etMessage.getText() != null) {
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).arriveOperation(LoginHelper.getInstance().getUserToken(), "2", gid, mDataBinding.etMessage.getText().toString()), new ProgressSubscriber<ResultInfoBean>(mContext, new SubscriberOnNextListener<ResultInfoBean>() {
                    @Override
                    public void onNext(ResultInfoBean result) {
                        if (result.getStatus() == 1) {
                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                            ReportActivity.this.finish();
                        }
                    }
                }));
            } else {
                Toast.makeText(mContext, "请输入举报信息", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("举报信息", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresent(new Presenter());
        gid = getIntent().getIntExtra("gid", 0);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }
}
