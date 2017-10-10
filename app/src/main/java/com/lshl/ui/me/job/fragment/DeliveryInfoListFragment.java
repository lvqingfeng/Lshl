package com.lshl.ui.me.job.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.lshl.base.SimpleBindingAdapter;
import com.lshl.bean.DeliveryResumeListBean;
import com.lshl.databinding.FragmentDeliveryInfoListBinding;
import com.lshl.databinding.PersonDeliverItemBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.me.job.JobInfoActivity;
import com.lshl.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 * 投递信息
 */
public class DeliveryInfoListFragment extends LazyFragment<FragmentDeliveryInfoListBinding>
        implements SpringView.OnFreshListener {

    private boolean isEnd;
    private List<DeliveryResumeListBean.ListBean> mListData;
    private SimpleBindingAdapter<PersonDeliverItemBinding, DeliveryResumeListBean.ListBean> mAdapter;

    public static DeliveryInfoListFragment newInstance() {
        DeliveryInfoListFragment fragment = new DeliveryInfoListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadData() {
        initLoadData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_delivery_info_list;
    }

    @Override
    protected void initViews() {
        mListData = new ArrayList<>();
        setListData(mListData);

        mDataBinding.springView.setListener(this);
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SimpleBindingAdapter<PersonDeliverItemBinding, DeliveryResumeListBean.ListBean>(mListData, R.layout.item_layout_person_deliver) {

            @Override
            public void onBindViewHolder(PersonDeliverItemBinding binding, int position) {
                binding.setDeliveryBean(mListData.get(position));
            }
        };
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                DeliveryResumeListBean.ListBean listBean = mListData.get(vh.getLayoutPosition());
                JobInfoActivity.actionStart(getActivity(), listBean.getR_uid(), listBean.getR_id());
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                if (mListData.get(vh.getLayoutPosition()).getR_uid().equals(LoginHelper.getInstance().getUserBean().getUid())) {
                    DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此记录?", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            deleteResumeDelivery(mListData.get(vh.getLayoutPosition()).getR_id());
                        }
                    });
                } else {
                    Toast.makeText(mContext, "只能删除自己的投递信息", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void deleteResumeDelivery(String rid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .deleteResumeDelivery(LoginHelper.getInstance().getUserToken(), rid)
                , new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<String> result) {
                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                            onRefresh();
                        } else {
                            Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).resumeList(LoginHelper.getInstance().getUserToken(), String.valueOf(page))
                , new Subscriber<HttpResult<DeliveryResumeListBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<DeliveryResumeListBean> result) {
                        isEnd = result.getInfo().getEnd() == 1;
                        mListData.addAll(result.getInfo().getList());
                        mAdapter.notifyDataSetChanged();
                        mDataBinding.springView.onFinishFreshAndLoad();
                        if (result.getInfo().getList().size() <= 0) {
                            mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                        } else {
                            mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                        }
                    }
                }
        );
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
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
