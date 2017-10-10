package com.lshl.ui.business.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.LazyFragment;
import com.lshl.databinding.FragmentShpersentBinding;

/**
 * Created by Administrator on 2016/11/3.
 */

public class SHPresentFragment extends LazyFragment<FragmentShpersentBinding> {
    private String buid;

    public static SHPresentFragment newInstance() {
        SHPresentFragment fragment = new SHPresentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shpersent;
    }

    @Override
    protected void initViews() {
        buid = getArguments().getString("buid");
        String url=ApiService.SHDETAILS+buid;
        mDataBinding.wvShanghuiPresent.loadUrl(url);
    }
}
