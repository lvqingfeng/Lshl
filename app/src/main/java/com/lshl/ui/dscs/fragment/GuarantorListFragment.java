package com.lshl.ui.dscs.fragment;

import android.content.Context;
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
import com.lshl.bean.GuarantorListBean;
import com.lshl.databinding.FragmentGuarantorListBinding;
import com.lshl.databinding.GuarantorListItemBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 担保人列表
 */
public class GuarantorListFragment extends BaseFragment<FragmentGuarantorListBinding> {

    private String mId;
    private List<GuarantorListBean> mListData;
    private SimpleBindingAdapter<GuarantorListItemBinding, GuarantorListBean> mAdapter;

    private OnClickGoCallBack mCallBack;

    public static GuarantorListFragment newInstance(String id) {
        GuarantorListFragment fragment = new GuarantorListFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (OnClickGoCallBack) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString("id");
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
        return R.layout.fragment_guarantor_list;
    }


    @Override
    protected void initViews() {
        mListData = new ArrayList<>();
        loadListData();
        mDataBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadListData();
            }
        });
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                mCallBack.onClickGoGuarantorInfo(mListData.get(vh.getLayoutPosition()).getMg_img());
            }
        });
        mAdapter = new SimpleBindingAdapter<GuarantorListItemBinding, GuarantorListBean>(mListData, R.layout.item_layout_guarantor_list) {
            @Override
            public void onBindViewHolder(GuarantorListItemBinding binding, int position) {
                binding.setBean(mListData.get(position));
            }
        };
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    private void loadListData() {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getGuaranteeList(mId)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<List<GuarantorListBean>>>() {
                    @Override
                    public void onNext(HttpResult<List<GuarantorListBean>> result) {
                        mListData.clear();
                        mListData.addAll(result.getInfo());
                        mAdapter.notifyDataSetChanged();
                        mDataBinding.swipeRefresh.setRefreshing(false);
                    }
                })
        );
    }

    public interface OnClickGoCallBack {
        void onClickGoGuarantorInfo(String imageUrl);
    }
}
