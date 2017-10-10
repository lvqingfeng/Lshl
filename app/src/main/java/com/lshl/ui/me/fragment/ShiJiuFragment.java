package com.lshl.ui.me.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.lshl.bean.ShiJiuBean;
import com.lshl.databinding.FragmentShijiuBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.me.activity.ShiJiuDetailsActivity;
import com.lshl.ui.me.adapter.ShiJiuAdapter;
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

public class ShiJiuFragment extends LazyFragment<FragmentShijiuBinding> {
    private List<ShiJiuBean.InfoBean.ListBean> mList;
    private ShiJiuAdapter shiJiuAdapter;
    private boolean isEnd;
    private MyHandler mHandler;

    public static ShiJiuFragment newInstance() {
        Bundle args = new Bundle();
        ShiJiuFragment fragment = new ShiJiuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void loadData() {
        mHandler.onPullDownToRefresh(null);
    }

    private void loadData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.String)
                        .updateShiJiuList(LoginHelper.getInstance().getUserToken(), page + "")
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody result) {
                        if (result != null) {
                            try {
                                String string = result.string();
                                Gson gson = new Gson();
                                ShiJiuBean bean = gson.fromJson(string, ShiJiuBean.class);
                                isEnd = bean.getStatus() == 1;
                                mList.addAll(bean.getInfo().getList());
                                shiJiuAdapter.notifyDataSetChanged();
                                mDataBinding.spShijiu.onFinishFreshAndLoad();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shijiu;
    }

    @Override
    protected void initViews() {
        mHandler = new MyHandler(this);
        mDataBinding.spShijiu.setHeader(new RotationHeader(mContext));
        mDataBinding.spShijiu.setFooter(new RotationFooter(mContext));
        mDataBinding.spShijiu.setListener(new SpringView.OnFreshListener() {
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
        mDataBinding.recyclerShijiu.setLayoutManager(manager);
        mDataBinding.recyclerShijiu.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        shiJiuAdapter = new ShiJiuAdapter(mList);
        mDataBinding.recyclerShijiu.setAdapter(shiJiuAdapter);
        mDataBinding.recyclerShijiu.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerShijiu) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                String pids = mList.get(position).getGre_pid();
                int pid = Integer.parseInt(pids);
                String gids = mList.get(position).getGre_id();
                int gid = Integer.parseInt(gids);
                String gre_status = mList.get(position).getGre_status();
                ShiJiuDetailsActivity.actionStart(getActivity(), pid, gid, gre_status);
            }
        });
    }

    private static class MyHandler extends RefreshHandler {

        private final WeakReference<ShiJiuFragment> mFragment;

        public MyHandler(ShiJiuFragment fragment) {
            super(fragment.mDataBinding.spShijiu);
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
