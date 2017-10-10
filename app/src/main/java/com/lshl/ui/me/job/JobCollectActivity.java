package com.lshl.ui.me.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

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
import com.lshl.base.BaseActivity;
import com.lshl.bean.RecruitBean;
import com.lshl.bean.RecruitListBean;
import com.lshl.databinding.ActivityJobCollectBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.job.PositionDetailsActivity;
import com.lshl.ui.appliance.job.RecruitAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏列表
 */
public class JobCollectActivity extends BaseActivity<ActivityJobCollectBinding>
        implements SpringView.OnFreshListener {
    private RecruitAdapter mAdapter;
    private boolean isEnd;
    private List<RecruitBean.InfoBean.ListBean> mList;

    private Constant.JobListType mJobType = Constant.JobListType.COLLECT;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, JobCollectActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("收藏列表", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
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
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        setListData(mList);
        mAdapter = new RecruitAdapter(mList, mJobType);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                PositionDetailsActivity.actionStart(JobCollectActivity.this, mList.get(vh.getLayoutPosition()).getEr_id());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_job_collect;
    }


    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    @Override
    protected void loadListData(int page) {

        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updateRecruList(LoginHelper.getInstance().getUserToken(), String.valueOf(page), mJobType.getType(), null, null),
                new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<RecruitListBean>() {
                    @Override
                    public void onNext(RecruitListBean result) {
                        if (result != null) {
                            isEnd = result.getInfo().getEnd() == 1;
//                            mList.addAll(result.getInfo().getList());
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
}
