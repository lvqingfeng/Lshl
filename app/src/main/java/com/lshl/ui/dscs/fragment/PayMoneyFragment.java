package com.lshl.ui.dscs.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseFragment;
import com.lshl.bean.MutualDetailsBean;
import com.lshl.bean.WelfareDetailsBean;
import com.lshl.databinding.FragmentPayMoneyBinding;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;

import java.util.ArrayList;

/**
 * 最终放款的页面
 */
public class PayMoneyFragment extends BaseFragment<FragmentPayMoneyBinding> {

    private int mFromWhere;
    private String mId;
    private ArrayList<String> mList;

    public static PayMoneyFragment newInstance(int fromWhere, String id) {
        PayMoneyFragment fragment = new PayMoneyFragment();
        Bundle args = new Bundle();
        args.putInt("where", fromWhere);
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFromWhere = getArguments().getInt("where");
            mId = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pay_money;
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        if (mFromWhere == DscsProjectDetailsActivity.FROM_MA) {
            updateMutual();
            mDataBinding.webView.loadUrl(ApiService.MUTUAL_AID_MONEY_INFO + mId);
        } else if (mFromWhere == DscsProjectDetailsActivity.FROM_MB) {
            updateWelfare();
            mDataBinding.webView.loadUrl(ApiService.PUBLIC_WELFARE_MONEY_INFO + mId);
        }
    }

    private void updateWelfare() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).
                updateWelfare(mId), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<WelfareDetailsBean>() {
            @Override
            public void onNext(final WelfareDetailsBean result) {
                Glide.with(mContext).load(ApiClient.getPublicWelfare(result.getInfo().getPw_payment_img())).error(R.drawable.account_logo)
                        .into(mDataBinding.imageView);
                if (!TextUtils.isEmpty(result.getInfo().getPw_payment_img())) {
                    mDataBinding.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mList.clear();
                            mList.add(ApiClient.getPublicWelfare(result.getInfo().getPw_payment_img()));
                            Intent intent = new Intent();
                            intent.putExtra(PhotoShowActivity.SELECT_POSITION, 0);
                            intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                            intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mList);
                            intent.setClass(mContext, PhotoShowActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }));
    }

    private void updateMutual() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateMutual(mId), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<MutualDetailsBean>() {
            @Override
            public void onNext(final MutualDetailsBean result) {
                Glide.with(mContext).load(ApiClient.getMutualImage(result.getInfo()
                        .getMa_payment_img())).error(R.drawable.account_logo)
                        .into(mDataBinding.imageView);
                if (!TextUtils.isEmpty(result.getInfo().getMa_payment_img())) {
                    mDataBinding.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mList.clear();
                            mList.add(ApiClient.getMutualImage(result.getInfo().getMa_payment_img()));
                            Intent intent = new Intent();
                            intent.putExtra(PhotoShowActivity.SELECT_POSITION, 0);
                            intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                            intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mList);
                            intent.setClass(mContext, PhotoShowActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }));
    }
}
