package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.bean.request.ChooseAddressBean;
import com.lshl.databinding.ActivityChooseAddressBinding;
import com.lshl.ui.me.adapter.ChooseAddressAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ChooseAddressActivity extends BaseActivity<ActivityChooseAddressBinding> {
    private List<ChooseAddressBean.InfoBean> mList;
    private ChooseAddressAdapter chooseAddressAdapter;
    private BaiduMapLocationHelper locationHelper;

    public static void actionStart(Activity activity,int requestCode) {
        Intent intent = new Intent(activity, SendDynamicActivity.class);
        activity.startActivityForResult(intent,requestCode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("选择地址",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        locationHelper = BaiduMapLocationHelper.getInstance().init(mContext);
        locationHelper.locationStart();
        mList=new ArrayList<>();
        chooseAddressAdapter = new ChooseAddressAdapter(mList);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.setAdapter(chooseAddressAdapter);
        locationHelper.setLocationCallBack(new BaiduMapLocationHelper.OnLocationCallBack() {
            @Override
            public void callBackLatLng(BDLocation location, LatLng latLng) {

            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_address;
    }
}
