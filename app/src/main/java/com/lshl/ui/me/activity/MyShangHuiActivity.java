package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.bean.AddressBean;
import com.lshl.databinding.ActivityMyShangHuiBinding;
import com.lshl.ui.me.adapter.MyShangHuiPagerAdapter;
import com.lshl.ui.me.fragment.AlreadyJoinShanghuiFragment;
import com.lshl.ui.me.fragment.NotJoinShanghuiFragment;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.JsonFormFileUtils;
import com.lshl.utils.SharedPreferencesUtils;
import com.lshl.widget.IosDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyShangHuiActivity extends BaseActivity<ActivityMyShangHuiBinding> {

    private ArrayList<Fragment> mViewList;
    private BaiduMapLocationHelper mLocationHelper;
    public Button mBtnSelectAddress;
    public TextView mTvSelectAddressName;
    private List<AddressBean> mAddressData;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyShangHuiActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setupTitle() {
        setTextTitleView("我的商会", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mAddressData = new ArrayList<>();
        try {
            mAddressData.addAll(JsonFormFileUtils.getJsonArray(mContext, "address.json", AddressBean.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mDataBinding.tvCurrentArea.setText(getString(R.string.current_area_s, "定位中..."));

        mViewList = new ArrayList<>();
        mViewList.add(AlreadyJoinShanghuiFragment.newInstance());
        mViewList.add(NotJoinShanghuiFragment.newInstance());
        mLocationHelper = BaiduMapLocationHelper.getInstance().init(mContext);
        mBtnSelectAddress = mDataBinding.btnShanghuiShaixuan;
        mTvSelectAddressName = mDataBinding.tvCurrentArea;
    }

    @Override
    public void lightOff() {
        super.lightOff();
    }

    @Override
    public void lightOn() {
        super.lightOn();
    }

    @Override
    protected void initViews() {


        mLocationHelper.setLocationCallBack(new BaiduMapLocationHelper.OnLocationCallBack() {
            @Override
            public void callBackLatLng(BDLocation location, LatLng latLng) {

                final String locationArea = location.getCity();
                String currentArea = (String) SharedPreferencesUtils.getParam(mContext, SharedPreferencesUtils.SHARED_CURRENT_AREA, "");
                mDataBinding.tvCurrentArea.setText(getString(R.string.current_area_s, locationArea));

                if (!TextUtils.isEmpty(currentArea)) {
                    if (currentArea.equals(locationArea))
                        return;
                }

                String currentCity = location.getProvince() + location.getCity();
                SpannableString spanText = new SpannableString("检测到您当前的位置为" + currentCity + "是否切换为当地的商会列表");

                spanText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimaryDark)), 10, 10 + currentCity.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                DialogUtils.iosAlertDialog(mContext, "温馨提示", spanText
                        , new IosDialog.OnClickListener() {
                            @Override
                            public void onClick(IosDialog dialog) {
                                dialog.dismiss();

                            }
                        }, new IosDialog.OnClickListener() {
                            @Override
                            public void onClick(IosDialog dialog) {
                                dialog.dismiss();
                                SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.SHARED_CURRENT_AREA, locationArea);
                                switchCurrentArea(locationArea);
                            }
                        });/**/
                Log.d("baiduMap", "百度地图获取到的城市为：" + location.getCity() + "----城市id = " + location.getCityCode());
            }
        });
        mLocationHelper.locationStart();

        MyShangHuiPagerAdapter myShangHuiPagerAdapter = new MyShangHuiPagerAdapter(getSupportFragmentManager(), mViewList);

        mDataBinding.vpShanghuiParent.setAdapter(myShangHuiPagerAdapter);
        mDataBinding.tabShanghuiTitle.setupWithViewPager(mDataBinding.vpShanghuiParent);
        mDataBinding.tabShanghuiTitle.setTabMode(TabLayout.MODE_FIXED);

    }

    /**
     * 切换当前地区
     */
    private void switchCurrentArea(String locationArea) {

        for (AddressBean listAddress : mAddressData) {
            for (AddressBean.SubBean subBean : listAddress.getSub()) {
                if (subBean.getCityname().equals(locationArea)) {
                    SharedPreferencesUtils.setParam(mContext, SharedPreferencesUtils.SHARED_CURRENT_AREA_CODE, subBean.getId());
                }
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_shang_hui;
    }


}
