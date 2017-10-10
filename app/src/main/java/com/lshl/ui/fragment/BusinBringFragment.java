package com.lshl.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.base.BaseFragment;
import com.lshl.bean.AddressBean;
import com.lshl.databinding.FragmentBusinBringBinding;
import com.lshl.ui.MainActivity;
import com.lshl.ui.business.adapter.ShangHuiPagerAdapter;
import com.lshl.ui.business.fragment.QiYeFragment;
import com.lshl.ui.business.fragment.ShangHuiFragment;
import com.lshl.utils.JsonFormFileUtils;
import com.lshl.view.AddressSelectPopWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 商汇Fragment
 */
public class BusinBringFragment extends BaseFragment<FragmentBusinBringBinding> {

    private List<BaseFragment> mList;

    private ShangHuiPagerAdapter shangHuiPagerAdapter;

    private boolean isLocationSuc = true;

    private CheckBox mRightView;
    private BaiduMapLocationHelper mLocationHelper;

    private List<AddressBean> mAddressData;
    private AddressSelectPopWindow mAddressPop;

    private ShangHuiFragment mShangHuiFragment;
    private QiYeFragment mQiYeFragment;

    public String mCityId = "0";
    public String mSearchInfo;
    private int mTabPosition;

    private String mLocationCityId;

    private String mLocationCityName;
    private String mShangHuiSelectCityName;
    private String mQiYeSelectCityName;

    private BusinBringFragmentCallback mCallBack;
    private ImageView mLeftView;

    public static BusinBringFragment newInstance() {
        BusinBringFragment fragment = new BusinBringFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (BusinBringFragmentCallback) context;
        MainActivity activity = ((MainActivity) getActivity());
        mRightView = activity.getRightViews();
        mLeftView = activity.getLeftViews();
        mRightView.setText("全国");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddressPop = new AddressSelectPopWindow(getActivity());
        if (getArguments() != null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRightView = null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_busin_bring;
    }

    @Override
    protected void initViews() {

        mAddressData = new ArrayList<>();
        try {
            mAddressData.addAll(JsonFormFileUtils.getJsonArray(mContext, "address.json", AddressBean.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mLocationHelper = BaiduMapLocationHelper.getInstance().init(mContext);
        //刷新按钮的回调
        if (mLeftView != null) {
            mLeftView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLocationSuc) {
                        mSearchInfo = "";
                        mShangHuiSelectCityName = mLocationCityName;
                        mQiYeSelectCityName = mLocationCityName;
                        mCityId = "0";
                        if (mTabPosition == 0) {
                            mShangHuiFragment.loadListData();
                        } else if (mTabPosition == 1) {
                            mQiYeFragment.loadListData();
                        }
                        mRightView.setText("全国");
                        mDataBinding.editSearchContent.setText("");
                    } else {
                        Toast.makeText(mContext, "正在定位中，请稍后重试!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (mRightView != null) {
            //设置右上角的定位点击事件
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity) getActivity()).lightOff();
                    mAddressPop.addressReset();
                    mAddressPop.showPopupWindow(mDataBinding.getRoot());
                }
            };
            mCallBack.callBackRightViewClickListener(listener);
            mRightView.setOnClickListener(listener);
            //定位的回调
            mLocationHelper.setLocationCallBack(new BaiduMapLocationHelper.OnLocationCallBack() {
                @Override
                public void callBackLatLng(BDLocation location, LatLng latLng) {
                    String provinceName = "";
                    String cityName = "全国";

                    mCallBack.callBackCityName("全国");

                    mLocationCityName = cityName;
                    mShangHuiSelectCityName = cityName;
                    mQiYeSelectCityName = cityName;

                    mRightView.setText("全国");
                    switchCurrentArea(provinceName, cityName);
                    initFragmentPager();
                    isLocationSuc = true;
                }
            });
        }

        //开启定位
        mLocationHelper.locationStart();

        //城市选择的选择监听回调
        mAddressPop.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                mCityId = cityNo;
                if (mTabPosition == 0) {
                    mShangHuiSelectCityName = cityData;
                    mShangHuiFragment.loadListData();
                } else if (mTabPosition == 1) {
                    mQiYeSelectCityName = cityData;
                    mQiYeFragment.loadListData();
                }

                mCallBack.callBackCityName(cityData);
                if (mRightView != null) {
                    mRightView.setText(cityData);
                }
            }
        });
        //城市选择列表关闭的时的监听器
        mAddressPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ((BaseActivity) getActivity()).lightOn();
            }
        });
        //搜索按钮的点击事件
        mDataBinding.tvSearchGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchInfo = mDataBinding.editSearchContent.getText().toString().trim();
                if (mTabPosition == 0) {
                    mShangHuiFragment.loadListData();
                } else if (mTabPosition == 1) {
                    mQiYeFragment.loadListData();
                }
                closeKeybord(mDataBinding.editSearchContent,mContext);

            }
        });
    }
    /**
     * 关闭软键盘
     */
    public static void closeKeybord(EditText mEditText, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private void initFragmentPager() {

        mList = new ArrayList<>();
        mShangHuiFragment = ShangHuiFragment.newInstance(mCityId);
        mQiYeFragment = QiYeFragment.newInstance(mCityId);
        mList.add(mShangHuiFragment);
        mList.add(mQiYeFragment);
        shangHuiPagerAdapter = new ShangHuiPagerAdapter(getChildFragmentManager(), mList);
        mDataBinding.vpShanghuisParent.setAdapter(shangHuiPagerAdapter);
        mDataBinding.tabLayout.setupWithViewPager(mDataBinding.vpShanghuisParent);
        mDataBinding.vpShanghuisParent.setOffscreenPageLimit(mList.size());
        mDataBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabPosition = tab.getPosition();
                String cityData = "地区错乱，请复位";
                if (mTabPosition == 0) {
                    cityData = mShangHuiSelectCityName;
                } else if (mTabPosition == 1) {
                    cityData = mQiYeSelectCityName;
                }
                mCallBack.callBackCityName(cityData);
                if (mRightView != null) {
                    mRightView.setText(cityData);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 切换当前地区
     */
    private void switchCurrentArea(String provinceName, String locationArea) {

        int provincePosition = 0;
        for (int i = 0; i < mAddressData.size(); i++) {
            AddressBean listAddress = mAddressData.get(i);
            if (listAddress.getCityname().equals(provinceName)) {
                provincePosition = i;
                break;
            }
        }
        List<AddressBean.SubBean> subList = mAddressData.get(provincePosition).getSub();
        for (AddressBean.SubBean subBean : subList) {
            if (subBean.getCityname().equals(locationArea)) {
                mLocationCityId = "0";
                mCityId = "0";
                return;
            }
        }
//        Toast.makeText(mContext, "未匹配到当前地区", Toast.LENGTH_SHORT).show();
    }


    public interface BusinBringFragmentCallback {
        void callBackCityName(String cityName);

        void callBackRightViewClickListener(View.OnClickListener listener);
    }

}
