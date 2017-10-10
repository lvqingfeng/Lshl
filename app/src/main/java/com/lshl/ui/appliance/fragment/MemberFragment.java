package com.lshl.ui.appliance.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
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
import com.lshl.bean.MemberBean;
import com.lshl.databinding.FragmentMemberBinding;
import com.lshl.ui.appliance.adapter.MemberAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.RefreshHandler;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * 联盟成员
 * Created by Administrator on 2016/10/24.
 */

public class MemberFragment extends LazyFragment<FragmentMemberBinding> {
    private List<MemberBean.InfoBean.ListBean> mList;
    private MemberAdapter memberAdapter;
    private boolean isEnd;
    private MyHandler handler;

    public static MemberFragment newInstance() {
        Bundle args = new Bundle();

        MemberFragment fragment = new MemberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void loadData() {
        handler.onPullDownToRefresh(null);
    }

    private void loadData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.String)
                        .updateMember(LoginHelper.getInstance().getUserToken(), page),
                new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody result) {
                        if (result != null) {
                            try {
                                String string = result.string();
                                Gson gson = new Gson();
                                MemberBean bean = gson.fromJson(string, MemberBean.class);
                                if (bean != null) {
                                    isEnd = bean.getStatus() == 1;
                                    mList.addAll(bean.getInfo().getList());
                                    memberAdapter.notifyDataSetChanged();
                                    mDataBinding.spMember.onFinishFreshAndLoad();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_member;
    }

    @Override
    protected void initViews() {
        handler = new MyHandler(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerMember.setLayoutManager(manager);
        mDataBinding.recyclerMember.addItemDecoration(new DividerGridItemDecoration(getContext()));
        mDataBinding.recyclerMember.setItemAnimator(new DefaultItemAnimator());
        mList = new ArrayList<>();
        memberAdapter = new MemberAdapter(mList);
        mDataBinding.recyclerMember.setAdapter(memberAdapter);
        mDataBinding.spMember.setHeader(new RotationHeader(mContext));
        mDataBinding.spMember.setFooter(new RotationFooter(mContext));
        mDataBinding.spMember.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                handler.onPullDownToRefresh(isEnd);
            }

            @Override
            public void onLoadmore() {
                handler.onPullUpToRefresh(isEnd);
            }
        });
    }

    private static class MyHandler extends RefreshHandler {

        private final WeakReference<MemberFragment> mFragment;

        public MyHandler(MemberFragment fragment) {
            super(fragment.mDataBinding.spMember);
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
