package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.QiujiuDetailsBean;
import com.lshl.databinding.ActivityQiujiuDetailsBinding;
import com.lshl.ui.me.adapter.MyQiuJiuDetailsAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class QiuJiuDetailsActivity extends BaseActivity<ActivityQiujiuDetailsBinding> {
    private String pid;
    private List<QiujiuDetailsBean.InfoBean> mList;
    private LinearLayoutManager manager;
    private MyQiuJiuDetailsAdapter myQiuJiuDetailsAdapter;
    private BaiduMap map;
    private BaiduMapLocationHelper locationHelper;

    public static void actionStart(Activity activity, String pid) {
        Intent intent = new Intent(activity, QiuJiuDetailsActivity.class);
        intent.putExtra("pid", pid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("求救信息详情", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        map = mDataBinding.mapQiu.getMap();
        locationHelper = BaiduMapLocationHelper.getInstance().init(getApplicationContext());
        locationHelper.locationStart();
        manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerQiujiudetails.setLayoutManager(manager);
        mDataBinding.recyclerQiujiudetails.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerQiujiudetails.setItemAnimator(new DefaultItemAnimator());
        mList = new ArrayList<>();
        myQiuJiuDetailsAdapter = new MyQiuJiuDetailsAdapter(mList);
        mDataBinding.recyclerQiujiudetails.setAdapter(myQiuJiuDetailsAdapter);
        mDataBinding.spQiujiudetails.setHeader(new RotationHeader(mContext));
        mDataBinding.spQiujiudetails.setFooter(new RotationFooter(mContext));
        mDataBinding.spQiujiudetails.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mDataBinding.recyclerQiujiudetails.onFinishTemporaryDetach();
            }

            @Override
            public void onLoadmore() {
                mDataBinding.recyclerQiujiudetails.onFinishTemporaryDetach();
            }
        });

        pid = getIntent().getStringExtra("pid");
        int i = Integer.parseInt(pid);
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.Object).qiujiuDetails(LoginHelper.getInstance().getUserToken(), i), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<QiujiuDetailsBean>() {
            @Override
            public void onNext(QiujiuDetailsBean result) {
                List<QiujiuDetailsBean.InfoBean> list = result.getInfo();
                mList.addAll(list);
                myQiuJiuDetailsAdapter.notifyDataSetChanged();
            }
        }));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qiujiu_details;
    }
}
