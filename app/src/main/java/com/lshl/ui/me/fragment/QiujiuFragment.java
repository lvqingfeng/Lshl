package com.lshl.ui.me.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
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
import com.lshl.bean.QiuJiuBean;
import com.lshl.databinding.FragmentQiujiuBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.map.timely_help.HelpListActivity;
import com.lshl.ui.me.adapter.QiujiuAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.RefreshHandler;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/10/24.
 */

public class QiujiuFragment extends LazyFragment<FragmentQiujiuBinding> {
    private List<QiuJiuBean.InfoBean.ListBean> mList;
    private QiujiuAdapter qiujiuAdapter;
    private boolean isEnd;
    private MyHandler mHandler;

    public static QiujiuFragment newInstance() {
        Bundle args = new Bundle();
        QiujiuFragment fragment = new QiujiuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_qiujiu;
    }

    @Override
    protected void initViews() {
        mHandler = new MyHandler(this);
        mDataBinding.spQiujiu.setHeader(new RotationHeader(mContext));
        mDataBinding.spQiujiu.setFooter(new RotationFooter(mContext));
        mDataBinding.spQiujiu.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mHandler.onPullDownToRefresh(isEnd);
            }

            @Override
            public void onLoadmore() {
                mHandler.onPullUpToRefresh(isEnd);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerQiujiu.setLayoutManager(manager);
        mDataBinding.recyclerQiujiu.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerQiujiu.setItemAnimator(new DefaultItemAnimator());
        mList = new ArrayList<>();
        qiujiuAdapter = new QiujiuAdapter(mList);
        mDataBinding.recyclerQiujiu.setAdapter(qiujiuAdapter);
        mDataBinding.recyclerQiujiu.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerQiujiu) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                String pid = mList.get(position).getPre_id();
                HelpListActivity.actionStart(getActivity(), pid);
            }
        });
    }

    @Override
    public void loadData() {
        mHandler.onPullDownToRefresh(null);
    }

    private void loadData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updateQiuJiuList(LoginHelper.getInstance().getUserToken(), page + ""),
                new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody result) {
                        if (result != null) {
                            try {
                                String string = result.string();
                                Gson gson = new Gson();
                                QiuJiuBean bean = gson.fromJson(string, QiuJiuBean.class);
                                if (bean != null) {
                                    isEnd = bean.getStatus() == 1;
                                    mList.addAll(bean.getInfo().getList());
                                    qiujiuAdapter.notifyDataSetChanged();
                                    mDataBinding.spQiujiu.onFinishFreshAndLoad();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }));
    }

    private static class MyHandler extends RefreshHandler {

        private final WeakReference<QiujiuFragment> mFragment;

        public MyHandler(QiujiuFragment fragment) {
            super(fragment.mDataBinding.spQiujiu);
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
