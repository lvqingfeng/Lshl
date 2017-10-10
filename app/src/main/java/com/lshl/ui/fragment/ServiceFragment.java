package com.lshl.ui.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.BaseFragment;
import com.lshl.base.LoadImageHolder;
import com.lshl.bean.HomePageBean;
import com.lshl.databinding.ItemFragmentServiceBinding;
import com.lshl.ui.MainActivity;
import com.lshl.ui.service.activity.activity.ServiceHomeActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/14.
 */

public class ServiceFragment extends BaseFragment<ItemFragmentServiceBinding> {
    private ServiceFragmentCallback mCallback;
    private CheckBox mRightView;
    private List<HomePageBean.InfoBean.BannerBean> bannerList;
    private List<String> mList;

    public static ServiceFragment newInstance() {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ServiceFragmentCallback) context;
        mRightView = ((MainActivity) context).getRightViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_fragment_service;
    }

    @Override
    protected void initViews() {
        if (mRightView != null) {
            View.OnClickListener listener = new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "选择地点", Toast.LENGTH_SHORT).show();
                    mRightView.setText("兰州");
                    mRightView.setCompoundDrawables(getResources().getDrawable(R.drawable.rp_arrow_forward, null), null, null, null);
                }
            };
            mCallback.onLocationClick(listener);
            mRightView.setOnClickListener(listener);
        }
        bannerList = new ArrayList<>();
        mList = new ArrayList<>();

        initRdGroupClickListener();
        loadWeb();
        loadBaseData();

    }


    private void initRdGroupClickListener() {
        mDataBinding.rdGroupService.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_direct_sales:
                        Toast.makeText(getActivity(), "鲁商直营", Toast.LENGTH_SHORT).show();
                        mDataBinding.rbDirectSales.setChecked(false);
                        break;
                    case R.id.rb_local_service:
                        Toast.makeText(getActivity(), "本地服务", Toast.LENGTH_SHORT).show();
                        mDataBinding.rbLocalService.setChecked(false);
                        break;
                    case R.id.rb_service_station:
                        Toast.makeText(getActivity(), "服务站", Toast.LENGTH_SHORT).show();
                        ServiceHomeActivity.actionStart(getActivity());
                        mDataBinding.rbServiceStation.setChecked(false);
                        break;
                }
            }
        });
    }

    public void loadWeb() {
        String url = "http://www.qq.com/";
        mDataBinding.webView.getSettings().setJavaScriptEnabled(true);
        mDataBinding.webView.loadUrl(url);
    }

    private void loadBaseData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.
                RetrofitType.Object).updateHomePageList(), new Subscriber<HomePageBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HomePageBean homePageBean) {
                HomePageBean.InfoBean info = homePageBean.getInfo();

                if (info.getBanner().size() > 0) {
                    bannerList.addAll(info.getBanner());
                    mList.clear();
                    for (int i = 0; i < bannerList.size(); i++) {
                        mList.add(ApiService.BASE_URL + bannerList.get(i).getBn_img());
                    }
                    mDataBinding.cbBanner.setPages(new CBViewHolderCreator<LoadImageHolder>() {
                        @Override
                        public LoadImageHolder createHolder() {
                            return new LoadImageHolder();
                        }
                    }, mList).setPageIndicator(new int[]{R.drawable.bg_banner_unselect, R.drawable.bg_banner_select});
                    mDataBinding.cbBanner.startTurning(3000);
                } else {
                    mDataBinding.cbBanner.setBackgroundResource(R.mipmap.account_logo);
                }
            }
        });
    }

    public interface ServiceFragmentCallback {
        void onLocationClick(View.OnClickListener listener);
    }
}
