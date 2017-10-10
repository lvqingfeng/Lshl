package com.lshl.ui.me.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.LazyFragment;
import com.lshl.bean.ReputationBean;
import com.lshl.databinding.FragmentReputationListBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.appliance.activity.KouBeiDetailsActivity;
import com.lshl.ui.me.adapter.ReputationListAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

/**
 * 个人---口碑展示的Fragment
 */
public class ReputationListFragment extends LazyFragment<FragmentReputationListBinding>
        implements SpringView.OnFreshListener {

    private List<ReputationBean.ListBean> mListData;
    private Constant.ReputationType mReputationType;
    private boolean isEnd;

    private ReputationListAdapter mAdapter;

    /**
     * @param type 口碑的类型，分为红榜和黑榜
     * @return
     */
    public static ReputationListFragment newInstance(Constant.ReputationType type) {
        ReputationListFragment fragment = new ReputationListFragment();
        Bundle args = new Bundle();
        args.putSerializable("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListData = new ArrayList<>();
        setListData(mListData);
        if (getArguments() != null) {
            mReputationType = (Constant.ReputationType) getArguments().getSerializable("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reputation_list;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void initViews() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mAdapter = new ReputationListAdapter(mListData);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                KouBeiDetailsActivity.actionStart(getActivity(), mListData.get(vh.getLayoutPosition()).getS_id(), KouBeiDetailsActivity.FROM_PERSON);
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您确认要删除此条信息？", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        String s_id = mListData.get(vh.getLayoutPosition()).getS_id();
                        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                                RetrofitManager.RetrofitType.String).deleteKoubei(LoginHelper.getInstance().getUserToken(), s_id),
                                new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                                    @Override
                                    public void onNext(ResponseBody result) {
                                        try {
                                            String string = result.string();
                                            JSONObject object = JSON.parseObject(string);
                                            String status = object.getString("status");
                                            if (ApiService.STATUS_SUC.equals(status)) {
                                                onRefresh();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }));
                    }
                });
            }
        });
    }

    @Override
    protected void loadData() {
        initLoadData();
    }

    @Override
    protected void loadListData(int page) {
//        RetrofitManager.toSubscribe(
//                ApiClient.getApiService(
//                        ApiService.class, RetrofitManager.RetrofitType.Object
//                ).personalReputationList(LoginHelper.getInstance().getUserToken(), mReputationType.getValue(), String.valueOf(page))
//                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<ReputationBean>>() {
//                    @Override
//                    public void onNext(HttpResult<ReputationBean> result) {
//                        if (result.getInfo().getList().size() > 0) {
//                            mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
//                            isEnd = result.getInfo().getEnd() == 1;
//                            mListData.addAll(result.getInfo().getList());
//                            mAdapter.notifyDataSetChanged();
//                            mDataBinding.springView.onFinishFreshAndLoad();
//                        } else {
//                            mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
//                        }
//                    }
//                })
//        );
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
