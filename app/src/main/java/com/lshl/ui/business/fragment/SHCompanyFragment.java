package com.lshl.ui.business.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
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
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.LazyFragment;
import com.lshl.bean.CityQiyeBean;
import com.lshl.databinding.FragmentAllrankBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.business.adapter.QiYeAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.RefreshHandler;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/11/3.
 */

public class SHCompanyFragment extends LazyFragment<FragmentAllrankBinding> {
    //private List<CityQiyeBean.InfoEntity.ListEntity> mList;
    private QiYeAdapter qiYeAdapter;
    private String buid;
    private boolean isEnd;
    private MyHandler handler;

    public static SHCompanyFragment newInstance() {
        Bundle args = new Bundle();
        SHCompanyFragment fragment = new SHCompanyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_allrank;
    }

    @Override
    protected void initViews() {
        handler = new MyHandler(this);
        buid = getArguments().getString("buid");
        mDataBinding.spRank.setHeader(new RotationHeader(mContext));
        mDataBinding.spRank.setFooter(new RotationFooter(mContext));
        mDataBinding.spRank.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                handler.onPullDownToRefresh(isEnd);
            }

            @Override
            public void onLoadmore() {
                handler.onPullUpToRefresh(isEnd);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerRank.setLayoutManager(manager);
        mDataBinding.recyclerRank.setItemAnimator(new DefaultItemAnimator());
        mDataBinding.recyclerRank.addItemDecoration(new DividerGridItemDecoration(mContext));
        // mList=new ArrayList<>();
        // qiYeAdapter = new QiYeAdapter(mList);
        mDataBinding.recyclerRank.setAdapter(qiYeAdapter);
        mDataBinding.recyclerRank.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerRank) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                // String id = mList.get(position).getId();
                //  QiYeDetailsActivity.actionStart(getActivity(),id);
            }
        });
    }

    @Override
    protected void loadData() {
        handler.onPullDownToRefresh(null);
    }

    private void loadData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateSHCompanyList(buid, page + ""), new ProgressSubscriber<>(mContext,
                new SubscriberOnNextListener<CityQiyeBean>() {
                    @Override
                    public void onNext(CityQiyeBean result) {
                        if (result != null) {
                            //isEnd=result.getStatus()==1;
                            //  List<CityQiyeBean.InfoEntity.ListEntity> list = result.getInfo().getList();
                            //  mList.addAll(list);
                            qiYeAdapter.notifyDataSetChanged();
                            mDataBinding.spRank.onFinishFreshAndLoad();
                        }
                    }
                }));
    }

    private static class MyHandler extends RefreshHandler {
        private final WeakReference<SHCompanyFragment> mFragment;

        public MyHandler(SHCompanyFragment fragment) {
            super(fragment.mDataBinding.spRank);
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void getListDatas(int page) {
            if (page == 1) {
                // mFragment.get().mList.clear();
            }
            mFragment.get().loadData(page);
        }
    }
}
