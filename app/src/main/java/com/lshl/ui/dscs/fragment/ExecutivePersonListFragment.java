package com.lshl.ui.dscs.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseFragment;
import com.lshl.base.HttpResult;
import com.lshl.base.SimpleBindingAdapter;
import com.lshl.bean.ExecutivePersonListBean;
import com.lshl.databinding.ExecutiveListItemBinding;
import com.lshl.databinding.FragmentExecutivePersonListBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 执行人列表
 */
public class ExecutivePersonListFragment extends BaseFragment<FragmentExecutivePersonListBinding> {

    private String mPwid;
    private List<ExecutivePersonListBean> mListData;
    private SimpleBindingAdapter<ExecutiveListItemBinding, ExecutivePersonListBean> mAdapter;

    public static ExecutivePersonListFragment newInstance(String id) {
        ExecutivePersonListFragment fragment = new ExecutivePersonListFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPwid = getArguments().getString("id");
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
        return R.layout.fragment_executive_person_list;
    }

    @Override
    protected void initViews() {
        mListData = new ArrayList<>();
        mDataBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadListData();
            }
        });
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SimpleBindingAdapter<ExecutiveListItemBinding, ExecutivePersonListBean>(mListData, R.layout.item_layout_executive_person_list) {
            @Override
            public void onBindViewHolder(ExecutiveListItemBinding binding, int position) {
                binding.setBean(mListData.get(position));
            }
        };
        mDataBinding.recyclerView.setAdapter(mAdapter);
        loadListData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                HxMemberDetailsActivity.actionStart(getActivity(), "", mListData.get(vh.getLayoutPosition()).getEp_uid(), false);
            }
        });
    }

    private void loadListData() {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getExecutivePersonList(mPwid)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<List<ExecutivePersonListBean>>>() {
                    @Override
                    public void onNext(HttpResult<List<ExecutivePersonListBean>> result) {
                        mListData.clear();
                        mListData.addAll(result.getInfo());
                        mAdapter.notifyDataSetChanged();
                        mDataBinding.swipeRefresh.setRefreshing(false);
                    }
                })
        );
    }

}
