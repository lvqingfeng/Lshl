package com.lshl.ui.me.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.R;
import com.lshl.base.LazyFragment;
import com.lshl.bean.AlreadyJoinBean;
import com.lshl.databinding.FragmentAlreadyBinding;
import com.lshl.ui.me.adapter.AlreadyJoinAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.RefreshHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 已加入商会.
 */
public class AlreadyJoinShanghuiFragment extends LazyFragment<FragmentAlreadyBinding> {
    private List<AlreadyJoinBean.InfoEntity> mList;
    private AlreadyJoinAdapter alreadyJoinAdapter;
    private boolean isEnd;
    private MyHandler mHandler;

    public static AlreadyJoinShanghuiFragment newInstance() {
        AlreadyJoinShanghuiFragment alreadyJoinShanghuiFragment = new AlreadyJoinShanghuiFragment();
        return alreadyJoinShanghuiFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmen_already;
    }

    @Override
    protected void initViews() {
        mHandler=new MyHandler(this);
        mList = new ArrayList<>();
        alreadyJoinAdapter = new AlreadyJoinAdapter(mList);
        mDataBinding.recyclerAlready.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDataBinding.recyclerAlready.setAdapter(alreadyJoinAdapter);
        mDataBinding.recyclerAlready.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        mDataBinding.recyclerAlready.setItemAnimator(new DefaultItemAnimator());


        mDataBinding.spAlready.setHeader(new RotationHeader(getActivity()));
        mDataBinding.spAlready.setFooter(new RotationFooter(getActivity()));
        mDataBinding.spAlready.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mDataBinding.spAlready.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mDataBinding.spAlready.onFinishFreshAndLoad();
            }
        });
    }

    @Override
    protected void loadData() {

    }
    private void loadData(int page){
//        RetrofitManager.toSubscribe(
//                ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
//                        .aleradyJoinShanghui(LoginHelper.getInstance().getUserToken())
//                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<AlreadyJoinBean>() {
//                    @Override
//                    public void onNext(AlreadyJoinBean result) {
//                        if (result != null) {
//                            isEnd=result.getStatus()==1;
//                            List<AlreadyJoinBean.InfoEntity> info = result.getInfo();
//                            mList.addAll(info);
//                            alreadyJoinAdapter.notifyDataSetChanged();
//                            mDataBinding.spAlready.onFinishFreshAndLoad();
//                        }
//                    }
//                })
//        );
    }
    private static class MyHandler extends RefreshHandler{
        private final WeakReference<AlreadyJoinShanghuiFragment> mFragment;

        public MyHandler(AlreadyJoinShanghuiFragment fragment) {
            super(fragment.mDataBinding.spAlready);
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
