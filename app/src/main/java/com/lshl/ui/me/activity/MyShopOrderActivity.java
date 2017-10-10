package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.SellOrderBean;
import com.lshl.databinding.ActivityMyShopOrderBinding;
import com.lshl.ui.me.adapter.SellOrderListAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.RefreshHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MyShopOrderActivity extends BaseActivity<ActivityMyShopOrderBinding> implements SpringView.OnFreshListener {

    private MyRefreshHandler mRefreshHandler;
    private List<SellOrderBean.InfoBean.ListBean> mListData;

    private boolean isEnd;

    private SellOrderListAdapter mAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyShopOrderActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("订单管理", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mListData = new ArrayList<>();
        mAdapter = new SellOrderListAdapter(mListData);
    }

    @Override
    protected void initViews() {
        mRefreshHandler = new MyRefreshHandler(this);
        mRefreshHandler.onPullDownToRefresh(null);

        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));

        mDataBinding.springView.setListener(this);

        mDataBinding.recyclerOrder.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerOrder.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerOrder.setAdapter(mAdapter);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_shop_order;
    }

    private void loadData(int page) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object)
                        .sellOrder(LoginHelper.getInstance().getUserToken(), String.valueOf(page))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<SellOrderBean>() {
                    @Override
                    public void onNext(SellOrderBean result) {
                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                            isEnd = result.getInfo().getEnd() == 1;
                            mListData.addAll(result.getInfo().getList());
                            mAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();
                        }
                    }
                })
        );

    }

    @Override
    public void onRefresh() {
        mRefreshHandler.onPullDownToRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        mRefreshHandler.onPullUpToRefresh(isEnd);
    }

    private static class MyRefreshHandler extends RefreshHandler {

        private WeakReference<MyShopOrderActivity> mActivity;

        MyRefreshHandler(MyShopOrderActivity activity) {
            super(activity.mDataBinding.springView);
            mActivity = new WeakReference<MyShopOrderActivity>(activity);
        }

        @Override
        public void getListDatas(int page) {
            if (page == 1) {
                mActivity.get().mListData.clear();
            }
            mActivity.get().loadData(page);
        }
    }

}
