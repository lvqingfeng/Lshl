package com.lshl.ui.appliance.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.R;
import com.lshl.base.LazyFragment;
import com.lshl.bean.MyMutualBean;
import com.lshl.databinding.FragmentWelfareBinding;
import com.lshl.ui.me.adapter.LaunchWelfareAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.RefreshHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */

public class MutualFragment extends LazyFragment<FragmentWelfareBinding>{
    private String ma_id;
    private String classify;
    private boolean isEnd;
    private MyHandler handler;
    private List<MyMutualBean.InfoBean.ListBean> mList;
    private LaunchWelfareAdapter launchWelfareAdapter;
    public static MutualFragment newInstance(String classify) {
        Bundle args = new Bundle();
        args.putString("classify",classify);
        MutualFragment fragment = new MutualFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void loadData() {
        handler.onPullDownToRefresh(null);
    }
    private void loadData(int page){

    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void initViews() {
        handler=new MyHandler(this);
        classify=getArguments().getString("classify");
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(new SpringView.OnFreshListener() {
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
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList=new ArrayList<>();

    }
    private static class MyHandler extends RefreshHandler{

        private final WeakReference<MutualFragment> mFragment;

        public MyHandler(MutualFragment fragment) {
            super(fragment.mDataBinding.springView);
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void getListDatas(int page) {
            if (page==1){
                mFragment.get().mList.clear();
            }
            mFragment.get().loadData(page);
        }
    }
}
