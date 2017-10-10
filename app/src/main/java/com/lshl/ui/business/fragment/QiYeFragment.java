package com.lshl.ui.business.fragment;

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
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.HttpResult;
import com.lshl.base.LazyFragment;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.bean.CityQiyeBean;
import com.lshl.databinding.QiyeFragmentBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.task.TaskBase;
import com.lshl.ui.business.adapter.QiYeAdapter;
import com.lshl.ui.fragment.BusinBringFragment;
import com.lshl.ui.me.activity.EnterPriseDetailsActivity;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 企业列表
 */

public class QiYeFragment extends LazyFragment<QiyeFragmentBinding> implements SpringView.OnFreshListener {

    private List<CityQiyeBean.ListBean> mList;
    private QiYeAdapter qiYeAdapter;
    private boolean isEnd;

    private String mInfo;
    private String mCityId;

    public static QiYeFragment newInstance(String cityId) {
        Bundle args = new Bundle();
        args.putString("cityId", cityId);
        QiYeFragment fragment = new QiYeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.qiye_fragment;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.spShanghuiqiyeRefresh;
    }

    @Override
    protected void initViews() {

        mCityId = getArguments().getString("cityId");

        mDataBinding.spShanghuiqiyeRefresh.setHeader(new RotationHeader(mContext));
        mDataBinding.spShanghuiqiyeRefresh.setFooter(new RotationFooter(mContext));
        mDataBinding.spShanghuiqiyeRefresh.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                QiYeFragment.this.onRefresh(isEnd);
            }

            @Override
            public void onLoadmore() {
                onLoadMore(isEnd);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(mContext);

        mDataBinding.recyclerShanghuiQiye.setLayoutManager(manager);
        mDataBinding.recyclerShanghuiQiye.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerShanghuiQiye.setItemAnimator(new DefaultItemAnimator());

        mList = new ArrayList<>();
        setListData(mList);
        qiYeAdapter = new QiYeAdapter(mList);
        mDataBinding.recyclerShanghuiQiye.setAdapter(qiYeAdapter);
        initLoadData();
        mDataBinding.recyclerShanghuiQiye.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerShanghuiQiye) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                final int position = vh.getLayoutPosition();
                checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                    @Override
                    public void onSuccess(AuthorityCheckBean bean) {
                        String id = mList.get(position).getEn_id();
                        EnterPriseDetailsActivity.actionStart(getActivity(), id);
                    }
                });
            }
        });

    }

    public void loadListData() {
        mInfo = ((BusinBringFragment) getParentFragment()).mSearchInfo;
        mCityId = ((BusinBringFragment) getParentFragment()).mCityId;
        mList.clear();
        loadListData(1);
    }

    @Override
    protected void loadData() {
        initLoadData();
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updateBusinessQiYe("1", mInfo, "", mCityId, String.valueOf(page))
                , new Subscriber<HttpResult<CityQiyeBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<CityQiyeBean> result) {
                        if (result != null) {
                            isEnd = result.getInfo().getEnd() == 1;
                            List<CityQiyeBean.ListBean> list = result.getInfo().getList();
                            mList.addAll(list);
                            qiYeAdapter.notifyDataSetChanged();
                            mDataBinding.spShanghuiqiyeRefresh.onFinishFreshAndLoad();
                            if (result.getInfo().getList().size() > 0) {
                                mDataBinding.recyclerShanghuiQiye.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            } else {
                                mDataBinding.recyclerShanghuiQiye.setBackgroundResource(R.mipmap.kongkongruye);
                            }
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }
}
