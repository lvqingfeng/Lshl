package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityShijiuDetailsBinding;
import com.lshl.bean.ResultInfoBean;
import com.lshl.bean.ShiJiuDetailsBean;

public class ShiJiuDetailsActivity extends BaseActivity<ActivityShijiuDetailsBinding> {
    private int pid;
    private int gid;
    private String pre_status;
    private String gre_status;
    private boolean isreport = true;
    private BaiduMap mBaiduMap;

    public static void actionStart(Activity activity, int pid, int gid, String gre_status) {
        Intent intent = new Intent(activity, ShiJiuDetailsActivity.class);
        intent.putExtra("pid", pid);
        intent.putExtra("gid", gid);
        intent.putExtra("gre_status", gre_status);
        activity.startActivity(intent);
    }

    public class Presenter {
        public void confirmArrive() {//救援人员到达以后的操作
            if (isreport) {
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).arriveOperation(LoginHelper.getInstance().getUserToken(), "1", gid, null), new ProgressSubscriber<ResultInfoBean>(mContext, new SubscriberOnNextListener<ResultInfoBean>() {
                    @Override
                    public void onNext(ResultInfoBean result) {
                        mDataBinding.ibtnArrive.setText("举报");
                        mDataBinding.ibtnArrive.setBackgroundResource(R.drawable.bg_red_btn);
                        isreport = false;
                    }
                }));
            } else {
                ReportActivity.actionStart(ShiJiuDetailsActivity.this, gid);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("施救信息详情", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        //定位
        mBaiduMap = mDataBinding.mapShijiu.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        BaiduMapLocationHelper.getInstance().init(mContext).locationStart();
        pid = getIntent().getIntExtra("pid", 0);
        gid = getIntent().getIntExtra("gid", 0);
        gre_status = getIntent().getStringExtra("gre_status");
        mDataBinding.setPresenter(new Presenter());
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .qiujiuMessage(LoginHelper.getInstance().getUserToken(), pid), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ShiJiuDetailsBean>() {
            @Override
            public void onNext(ShiJiuDetailsBean result) {
                ShiJiuDetailsBean.InfoBean bean = result.getInfo().get(0);
                mDataBinding.setInfoBean(bean);
                pre_status = bean.getPre_status();
                if ("1".equals(pre_status)) {
                    mDataBinding.ibtnArrive.setVisibility(View.GONE);
                } else {
                    if (gre_status.equals("1")) {
                        mDataBinding.ibtnArrive.setText("举报");
                        mDataBinding.ibtnArrive.setBackgroundResource(R.drawable.bg_red_btn);
                    }
                }
            }
        }));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataBinding.mapShijiu.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shijiu_details;
    }
}
