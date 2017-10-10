package com.lshl.ui.dscs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.base.HttpResult;
import com.lshl.bean.InvestListBean;
import com.lshl.databinding.ActivityInvestListBinding;
import com.lshl.databinding.InvestListItemBinding;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.RefreshHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class InvestListActivity extends BaseActivity<ActivityInvestListBinding> implements SpringView.OnFreshListener {

    private List<InvestListBean.ListBean> mListData;
    private String mUid;
    private InvestListAdapter mAdapter;

    private MyRefreshHandler mHandler;
    private boolean isEnd;

    public static void actionStart(Activity activity, String uid) {
        Intent intent = new Intent(activity, InvestListActivity.class);
        intent.putExtra("uid", uid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("捐款列表", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mUid = getIntent().getStringExtra("uid");
        mListData = new ArrayList<>();
        mHandler = new MyRefreshHandler(this);
    }

    @Override
    protected void initViews() {

        mHandler.onPullDownToRefresh(null);
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mAdapter = new InvestListAdapter();
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.setAdapter(mAdapter);

    }

    private void loadData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getInvestListBean(mUid, String.valueOf(page))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<InvestListBean>>() {

                    @Override
                    public void onNext(HttpResult<InvestListBean> result) {
                        InvestListBean info = result.getInfo();
                        if (info != null) {
                            isEnd = info.getEnd() == 1;
                            mListData.addAll(info.getList());
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                })
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invest_list;
    }

    @Override
    public void onRefresh() {
        mHandler.onPullDownToRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        mHandler.onPullUpToRefresh(isEnd);
    }

    private static class MyRefreshHandler extends RefreshHandler {

        private WeakReference<InvestListActivity> mActivity;

        MyRefreshHandler(InvestListActivity activity) {
            super(activity.mDataBinding.springView);
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void getListDatas(int page) {
            if (page == 1) {
                mActivity.get().mListData.clear();
            }
            mActivity.get().loadData(page);
        }
    }

    private class InvestListAdapter extends RecyclerView.Adapter<BindingViewHolder<InvestListItemBinding>> {

        @Override
        public BindingViewHolder<InvestListItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = getLayoutInflater().inflate(R.layout.item_layout_invest_list, parent, false);
            return new BindingViewHolder<>(InvestListItemBinding.bind(rootView));
        }

        @Override
        public void onBindViewHolder(BindingViewHolder<InvestListItemBinding> holder, int position) {
            holder.getBinding().setBean(mListData.get(position));
        }

        @Override
        public int getItemCount() {
            return mListData.size();
        }
    }
}
