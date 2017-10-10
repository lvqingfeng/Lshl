package com.lshl.ui.appliance.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.LazyFragment;
import com.lshl.bean.ForHelpBean;
import com.lshl.databinding.FragmentForhelpBinding;
import com.lshl.ui.appliance.adapter.ForHelpAdapter;

/**
 * 求救定位
 * Created by Administrator on 2016/10/24.
 */

public class ForHelpFragment extends LazyFragment<FragmentForhelpBinding> {

    private ForHelpAdapter forHelpAdapter;
    private boolean isEnd;

    public static ForHelpFragment newInstance() {
        Bundle args = new Bundle();
        ForHelpFragment fragment = new ForHelpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void loadData() {

    }

    private void loadData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateForHelp(page + ""), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ForHelpBean>() {
            @Override
            public void onNext(ForHelpBean result) {

            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forhelp;
    }

    @Override
    protected void initViews() {

    }

}
