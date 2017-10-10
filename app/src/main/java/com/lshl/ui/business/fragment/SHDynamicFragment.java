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
import com.lshl.databinding.FragmentAllrankBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.business.activity.SHDynamDetailsicActivity;
import com.lshl.ui.business.adapter.DongTaiAdapter;
import com.lshl.bean.DongTaiBean;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.RefreshHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class SHDynamicFragment extends LazyFragment<FragmentAllrankBinding> {
    private List<DongTaiBean.InfoEntity.ListEntity> mList;
    private DongTaiAdapter dongTaiAdapter;
    private String buid;
    private boolean isEnd;
    private MyHandler handler;

    public static SHDynamicFragment newInstance() {
        Bundle args = new Bundle();
        SHDynamicFragment fragment = new SHDynamicFragment();
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
        mList = new ArrayList<>();
        dongTaiAdapter = new DongTaiAdapter(mList);
        mDataBinding.recyclerRank.setAdapter(dongTaiAdapter);
        mDataBinding.recyclerRank.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerRank) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                String did = mList.get(position).getDid();
                SHDynamDetailsicActivity.actionStart(getActivity(), did);
            }
        });
    }

    @Override
    protected void loadData() {
        handler.onPullDownToRefresh(null);
    }

    private void loadData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateShDynamicList(buid, page + ""), new ProgressSubscriber<DongTaiBean>(mContext, new SubscriberOnNextListener<DongTaiBean>() {
            @Override
            public void onNext(DongTaiBean result) {
                if (result != null) {
                    isEnd = result.getStatus() == 1;
                    List<DongTaiBean.InfoEntity.ListEntity> list = result.getInfo().getList();
                    mList.addAll(list);
                    dongTaiAdapter.notifyDataSetChanged();
                    mDataBinding.spRank.onFinishFreshAndLoad();
                }
            }
        }));
    }

    private static class MyHandler extends RefreshHandler {

        private final WeakReference<SHDynamicFragment> mFragment;

        public MyHandler(SHDynamicFragment fragment) {
            super(fragment.mDataBinding.spRank);
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void getListDatas(int page) {
            if (page == 1) {
                mFragment.get().mList.clear();
            }
            mFragment.get().loadData(page);
        }
    }
}
