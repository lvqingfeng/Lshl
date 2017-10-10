package com.lshl.ui.me.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

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
import com.lshl.bean.NotJoinBean;
import com.lshl.databinding.FragmentNotjoinBinding;
import com.lshl.ui.me.activity.MyShangHuiActivity;
import com.lshl.ui.me.adapter.NotJoinAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.SharedPreferencesUtils;
import com.lshl.view.AddressSelectPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class NotJoinShanghuiFragment extends LazyFragment<FragmentNotjoinBinding> {

    private static final String ADDRESS_EXTRA = "address_extra";

    private List<NotJoinBean.InfoEntity> mList;
    private NotJoinAdapter notJoinAdapter;

    private AddressSelectPopWindow mAddressPop;

    private String mCityNo;


    public static NotJoinShanghuiFragment newInstance() {
        NotJoinShanghuiFragment notJoinShanghuiFragment = new NotJoinShanghuiFragment();
        Bundle bundle = new Bundle();
        notJoinShanghuiFragment.setArguments(bundle);
        return notJoinShanghuiFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notjoin;
    }

    @Override
    protected void initViews() {

        mAddressPop = new AddressSelectPopWindow(getActivity());
        mList = new ArrayList<>();
        notJoinAdapter = new NotJoinAdapter(mList);

        mDataBinding.receclerNotjoin.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDataBinding.receclerNotjoin.setAdapter(notJoinAdapter);
        mDataBinding.receclerNotjoin.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        mDataBinding.receclerNotjoin.setItemAnimator(new DefaultItemAnimator());

        mDataBinding.spNotjoin.setHeader(new RotationHeader(getActivity()));
        mDataBinding.spNotjoin.setFooter(new RotationFooter(getActivity()));
        mDataBinding.spNotjoin.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mDataBinding.spNotjoin.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mDataBinding.spNotjoin.onFinishFreshAndLoad();
            }
        });
        mAddressPop.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                mCityNo = cityNo;
                ((MyShangHuiActivity) getActivity()).mTvSelectAddressName.setText(getString(R.string.current_area_s, cityData));
                loadData();
            }
        });

        ((MyShangHuiActivity) getActivity()).mBtnSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyShangHuiActivity) getActivity()).lightOff();
                mAddressPop.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mAddressPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ((MyShangHuiActivity) getActivity()).lightOn();
            }
        });
    }

    @Override
    protected void loadData() {

        mList.clear();
        String cityNo = (String) SharedPreferencesUtils.getParam(mContext, SharedPreferencesUtils.SHARED_CURRENT_AREA_CODE, "");
        if (TextUtils.isEmpty(mCityNo))
            mCityNo = cityNo;

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object)
                        .noJoinShanghui(LoginHelper.getInstance().getUserToken(), mCityNo)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<NotJoinBean>() {
                    @Override
                    public void onNext(NotJoinBean bean) {
                        if (bean != null) {
                            mList.addAll(bean.getInfo());
                            notJoinAdapter.notifyDataSetChanged();
                        }
                    }
                })
        );
    }
}
