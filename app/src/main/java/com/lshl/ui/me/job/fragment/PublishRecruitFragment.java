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
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.HttpResult;
import com.lshl.base.LazyFragment;
import com.lshl.bean.RecruitBean;
import com.lshl.bean.RecruitListBean;
import com.lshl.databinding.FragmentPublishRecruitBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.appliance.job.PositionDetailsActivity;
import com.lshl.ui.appliance.job.RecruitAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 * 发布招聘信息的列表
 */
public class PublishRecruitFragment extends LazyFragment<FragmentPublishRecruitBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private List<RecruitBean.InfoBean.ListBean> mList;
    private RecruitAdapter mAdapter;

    private Constant.JobListType mJobType = Constant.JobListType.COMMON;

    public static PublishRecruitFragment newInstance() {
        PublishRecruitFragment fragment = new PublishRecruitFragment();
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
        return R.layout.fragment_publish_recruit;
    }

    @Override
    protected void initViews() {

        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        setListData(mList);
        mAdapter = new RecruitAdapter(mList, mJobType);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                PositionDetailsActivity.actionStart(getActivity(), mList.get(vh.getLayoutPosition()).getEr_id());
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                final String er_uid = mList.get(vh.getLayoutPosition()).getEr_uid();
                if (er_uid.equals(LoginHelper.getInstance().getUserBean().getUid())) {
                    DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此内容?", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            deleteRecruit(er_uid);
                        }
                    });
                } else {
                    Toast.makeText(mContext, "只能删除自己发布的招聘", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateRecruList(LoginHelper.getInstance().getUserToken(), String.valueOf(page), mJobType.getType()
                        , null, null), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<RecruitListBean>() {
            @Override
            public void onNext(RecruitListBean result) {
                if (result != null) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                    if (result.getInfo().getList().size() > 0) {
                        mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    } else {
                        mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                    }
                }
            }
        }));
    }

    private void deleteRecruit(String rid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .deleteResumeReceived(LoginHelper.getInstance().getUserToken(), rid)
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
