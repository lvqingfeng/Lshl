package com.lshl.ui.business.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.HttpResult;
import com.lshl.base.LazyFragment;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.bean.ShangHuiBean;
import com.lshl.databinding.ShanghuiFragmentBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.task.TaskBase;
import com.lshl.ui.business.activity.ShangHuiDetailsActivity;
import com.lshl.ui.business.adapter.SHAdapter;
import com.lshl.ui.fragment.BusinBringFragment;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 商会列表
 */

public class ShangHuiFragment extends LazyFragment<ShanghuiFragmentBinding> {

    private static final int EXIT_SH_REQUEST_CODE = 0x000121;

    private List<ShangHuiBean.ListBean> mList;
    private SHAdapter mAdapter;
    private boolean isEnd;
    private String mInfo;
    private String mCityId;

    private int mClcikPosition;


    public static ShangHuiFragment newInstance(String cityId) {
        Bundle args = new Bundle();
        args.putString("cityId", cityId);
        ShangHuiFragment fragment = new ShangHuiFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EXIT_SH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mList.get(mClcikPosition).setBus_business_number(String.valueOf(Integer.decode(mList.get(mClcikPosition).getBus_business_number()) - 1));
                mList.get(mClcikPosition).setAdd(0);
                mAdapter.notifyItemChanged(mClcikPosition);
            }
        }
    }

    @Override
    protected void loadData() {
        initLoadData();
    }

    public void loadListData() {
        mInfo = ((BusinBringFragment) getParentFragment()).mSearchInfo;
        mCityId = ((BusinBringFragment) getParentFragment()).mCityId;
        mList.clear();
        loadListData(1);
    }

    @Override
    protected void loadListData(int page) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updateBusinessInfo(LoginHelper.getInstance().getUserToken(), mCityId, mInfo, page + "")
                , new Subscriber<HttpResult<ShangHuiBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<ShangHuiBean> result) {
                        if (result != null) {
                            isEnd = result.getInfo().getEnd() == 1;
                            List<ShangHuiBean.ListBean> list = result.getInfo().getList();
                            mList.addAll(list);
                            mAdapter.notifyDataSetChanged();
                            mDataBinding.spShanghuiRefresh.onFinishFreshAndLoad();
                            if (result.getInfo().getList().size() > 0) {
                                mDataBinding.receclerShanghuiList.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            } else {
                                mDataBinding.receclerShanghuiList.setBackgroundResource(R.mipmap.kongkongruye);
                            }
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.shanghui_fragment;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.spShanghuiRefresh;
    }

    @Override
    protected void initViews() {
        mCityId = getArguments().getString("cityId");
        initListData();
    }

    private void initListData() {
        mDataBinding.spShanghuiRefresh.setHeader(new RotationHeader(mContext));
        mDataBinding.spShanghuiRefresh.setFooter(new RotationFooter(mContext));
        mDataBinding.spShanghuiRefresh.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                ShangHuiFragment.this.onRefresh(isEnd);
            }

            @Override
            public void onLoadmore() {
                onLoadMore(isEnd);
            }
        });
        mDataBinding.receclerShanghuiList.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.receclerShanghuiList.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.receclerShanghuiList.setItemAnimator(new DefaultItemAnimator());
        mList = new ArrayList<>();
        setListData(mList);
        mAdapter = new SHAdapter(mList);
        mDataBinding.receclerShanghuiList.setAdapter(mAdapter);
        mDataBinding.receclerShanghuiList.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.receclerShanghuiList) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mClcikPosition = vh.getLayoutPosition();
                checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                    @Override
                    public void onSuccess(AuthorityCheckBean bean) {

                        ShangHuiBean.ListBean listBean = mList.get(mClcikPosition);
                        String buid = listBean.getBus_id();
                        String title = listBean.getBus_business_name();
                        boolean isJoin = listBean.getAdd() == 1;
                        ShangHuiDetailsActivity.actionStart(null, ShangHuiFragment.this, buid, title, isJoin, EXIT_SH_REQUEST_CODE);
                    }
                });
            }
        });
    }
}
