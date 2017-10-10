package com.lshl.ui.me.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.lshl.base.LazyFragment;
import com.lshl.bean.MyMutualBean;
import com.lshl.databinding.FragmentLaunchBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.me.adapter.LaunchHelpAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 互助界面
 * 互助
 * Created by Administrator on 2016/11/7.
 */

public class LaunchMutualFragment extends LazyFragment<FragmentLaunchBinding>
        implements SpringView.OnFreshListener {

    private boolean isEnd;
    private String ma_id;

    private List<MyMutualBean.InfoBean.ListBean> mList;
    private LaunchHelpAdapter launchWelfareAdapter;

    public static LaunchMutualFragment newInstance() {
        Bundle args = new Bundle();
        LaunchMutualFragment fragment = new LaunchMutualFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadData() {
        initLoadData();
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(
                ApiService.class
                , RetrofitManager.RetrofitType.Object)
                .updateMyMutualList(LoginHelper.getInstance().getUserToken(), String.valueOf(page)), new ProgressSubscriber<>(
                mContext, new SubscriberOnNextListener<MyMutualBean>() {
            @Override
            public void onNext(MyMutualBean result) {
                if (result != null) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    launchWelfareAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                    if (result.getInfo().getList().size() > 0) {
                        mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    } else {
                        mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                    }
                }
            }
        }));

    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_launch;
    }

    @Override
    protected void initViews() {

        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        setListData(mList);
        launchWelfareAdapter = new LaunchHelpAdapter(mList);
        mDataBinding.recyclerView.setAdapter(launchWelfareAdapter);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String ma_id = mList.get(vh.getLayoutPosition()).getMa_id();
                DscsProjectDetailsActivity.actionStart(getActivity(), DscsProjectDetailsActivity.FROM_MA, ma_id);
            }
        });
    }


    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }
}