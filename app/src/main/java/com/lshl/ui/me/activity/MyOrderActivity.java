package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

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
import com.lshl.databinding.ActivityMyOrderBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.me.adapter.MyOrderAdapter;
import com.lshl.bean.MyOrderBean;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.RefreshHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends BaseActivity<ActivityMyOrderBinding> {
    private List<MyOrderBean.InfoBean.ListBean> mList;
    private MyOrderAdapter myOrderAdapter;
    private boolean isEnd;
    private MyHandler handler;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyOrderActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("我的预订单", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        handler = new MyHandler(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerOrder.setLayoutManager(manager);
        mDataBinding.spOrder.setHeader(new RotationHeader(mContext));
        mDataBinding.spOrder.setFooter(new RotationFooter(mContext));
        mDataBinding.spOrder.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                handler.onPullDownToRefresh(isEnd);
            }

            @Override
            public void onLoadmore() {
                handler.onPullUpToRefresh(isEnd);
            }
        });
        mDataBinding.recyclerOrder.addItemDecoration(new DividerGridItemDecoration(mContext));

        mDataBinding.recyclerOrder.setItemAnimator(new DefaultItemAnimator());
        mList = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(mList);
        mDataBinding.recyclerOrder.setAdapter(myOrderAdapter);
        loadData(1);
        mDataBinding.recyclerOrder.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerOrder) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                MyOrderDetailsActivity.actionStart(MyOrderActivity.this, position);
            }
        });
    }

    private void loadData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateMyOrder(LoginHelper.getInstance().getUserToken(), page + ""), new ProgressSubscriber<MyOrderBean>(mContext, new SubscriberOnNextListener<MyOrderBean>() {
            private List<MyOrderBean.InfoBean.ListBean> list;

            @Override
            public void onNext(MyOrderBean result) {
                if (result != null) {
                    list = result.getInfo().getList();
                    isEnd = result.getStatus() == 1;
                    mList.addAll(list);
                    myOrderAdapter.notifyDataSetChanged();
                    mDataBinding.spOrder.onFinishFreshAndLoad();
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    private static class MyHandler extends RefreshHandler {

        private final WeakReference<MyOrderActivity> mActivity;

        public MyHandler(MyOrderActivity activity) {
            super(activity.mDataBinding.spOrder);
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void getListDatas(int page) {
            if (page == 1) {
                mActivity.get().mList.clear();
            }
            mActivity.get().loadData(page);
        }
    }
}
