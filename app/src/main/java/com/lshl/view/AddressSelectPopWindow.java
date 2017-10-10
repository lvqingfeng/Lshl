package com.lshl.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.view.WheelCurvedPicker;
import com.lshl.R;
import com.lshl.bean.AddressBean;
import com.lshl.databinding.SelectAddressPopBinding;
import com.lshl.utils.JsonFormFileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月31日
 * 时间：22:42
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class AddressSelectPopWindow extends AddPopWindow {

    private WheelCurvedPicker mWheelProvince;
    private WheelCurvedPicker mWheelCity;
    private ImageView mIvClose;
    private TextView mTvConfig;

    private List<AddressBean> mAddressData;
    private List<String> mProvinceData;
    private List<String> mCityData;

    private WheelChangeListener mChangeListener;

    private int mProvincePosition;
    private int mCityPosition;

    /**
     * 初始化一个PopupWindow
     *
     * @param context 上下文对象
     */
    public AddressSelectPopWindow(Activity context) {
        super(context, R.layout.layout_pop_select_address);

        SelectAddressPopBinding binding = SelectAddressPopBinding.bind(getWindowRootView());

        mWheelProvince = binding.wheelProvince;
        mWheelCity = binding.wheelCity;
        mIvClose = binding.ivClose;
        mTvConfig = binding.tvConfig;

        initData(context);
        init();
    }

    private void initData(Context context) {
        try {
            mAddressData = JsonFormFileUtils.getJsonArray(context, "address.json", AddressBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWheelChangeListener(WheelChangeListener listener) {
        mChangeListener = listener;
    }


    private void init() {

        /*设置显示字体的小*/
        mWheelProvince.setTextSize(18);
        mWheelCity.setTextSize(18);

        mWheelProvince.setItemCount(10);
        mWheelCity.setItemCount(10);

        mProvinceData = getAddressProvinceList(mAddressData);
        mWheelProvince.setData(mProvinceData);

        mWheelProvince.setOnWheelChangeListener(new AbstractWheelPicker.SimpleWheelChangeListener() {
            @Override
            public void onWheelSelected(int index, String data) {
                mProvincePosition = index;
                mCityData = getAddressCityList(mAddressData, mProvincePosition);
                mWheelCity.setData(mCityData);
                mWheelCity.setItemIndex(0);
                mCityPosition = 0;
            }
        });
        mWheelCity.setOnWheelChangeListener(new AbstractWheelPicker.SimpleWheelChangeListener() {
            @Override
            public void onWheelSelected(int index, String data) {
                mCityPosition = index;
            }
        });
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTvConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mChangeListener != null) {
                    String provinceNo = mAddressData.get(mProvincePosition).getId();
                    String cityNo = mAddressData.get(mProvincePosition).getSub().get(mCityPosition).getId();
                    mChangeListener.onWheelSelected(provinceNo, cityNo, mProvinceData.get(mProvincePosition), mCityData.get(mCityPosition));
                }
            }
        });

    }

    public void addressReset() {
        mWheelProvince.setItemIndex(0);
        mProvincePosition = 0;
        mCityData = getAddressCityList(mAddressData, mProvincePosition);
        mWheelCity.setData(mCityData);
        mCityPosition = 0;
        mWheelCity.setItemIndex(0);
    }

    private List<String> getAddressProvinceList(List<AddressBean> addressList) {

        List<String> provinceList = new ArrayList<>();

        for (AddressBean addressLinkageBean : addressList) {
            provinceList.add(addressLinkageBean.getCityname());
        }

        return provinceList;
    }

    private List<String> getAddressCityList(List<AddressBean> addressList, int provincePosition) {

        List<String> cityList = new ArrayList<>();
        List<AddressBean.SubBean> cityBeanList = addressList.get(provincePosition).getSub();
        for (AddressBean.SubBean cityBean : cityBeanList) {
            cityList.add(cityBean.getCityname());
        }

        return cityList;
    }


    public interface WheelChangeListener {
        /**
         * @param provinceNo   省id
         * @param cityNo       市id
         * @param provinceData 省名称
         * @param cityData     市名称
         */
        void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData);
    }

}
