package com.lshl.ui.appliance.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
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
import com.lshl.base.LazyFragment;
import com.lshl.databinding.FragmentAllrankBinding;
import com.lshl.ui.appliance.adapter.AllRankAdapter;
import com.lshl.bean.RankBean;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.RefreshHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class AllRankFragment extends LazyFragment<FragmentAllrankBinding> {
    private List<RankBean.InfoEntity.ListEntity> mList;
    private AllRankAdapter allRankAdapter;
    private boolean isEnd;
    private MyHandler handler;

    public static AllRankFragment newInstance() {
        Bundle args = new Bundle();
        AllRankFragment fragment = new AllRankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadData() {
        handler.onPullDownToRefresh(null);
    }

    private void loadData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateRankList("all", page + ""), new ProgressSubscriber<RankBean>(mContext, new SubscriberOnNextListener<RankBean>() {
            @Override
            public void onNext(RankBean result) {
                if (result != null) {
                    isEnd = result.getStatus() == 1;
                    List<RankBean.InfoEntity.ListEntity> list = result.getInfo().getList();
                    mList.addAll(list);
                    allRankAdapter.notifyDataSetChanged();
                    mDataBinding.spRank.onFinishFreshAndLoad();
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_allrank;
    }

    @Override
    protected void initViews() {
        handler = new MyHandler(this);
        mDataBinding.spRank.setHeader(new RotationHeader(mContext));
        mDataBinding.spRank.setFooter(new RotationFooter(mContext));
        mDataBinding.spRank.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                handler.onPullDownToRefresh(isEnd);
            }

            @Override
            public void onLoadmore() {
                handler.onPullUpToRefresh(isEnd);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerRank.setLayoutManager(manager);
        mDataBinding.recyclerRank.setItemAnimator(new DefaultItemAnimator());
        mDataBinding.recyclerRank.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();

    }

    private static class MyHandler extends RefreshHandler {

        private final WeakReference<AllRankFragment> mFragment;

        public MyHandler(AllRankFragment fragment) {
            super(fragment.mDataBinding.spRank);
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void getListDatas(int page) {
            if (page == 1) {
                mFragment.get().mList.clear();
            }
            mFragment.get().loadData(page);
        }
    }
}
