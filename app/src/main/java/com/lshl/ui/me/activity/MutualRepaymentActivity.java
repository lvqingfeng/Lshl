package com.lshl.ui.me.activity;

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
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.MutualRepayBean;
import com.lshl.databinding.ActivityMutualRepaymentBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.me.adapter.MutualRepaymentAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MutualRepaymentActivity extends BaseActivity<ActivityMutualRepaymentBinding>
        implements SpringView.OnFreshListener {
    private List<MutualRepayBean.InfoBean> mList;
    private boolean isEnd = true;
    private MutualRepaymentAdapter mutualRepaymentAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MutualRepaymentActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("互助还款", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mutualRepaymentAdapter = new MutualRepaymentAdapter(mList);
        mDataBinding.recyclerView.setAdapter(mutualRepaymentAdapter);
        setListData(mList);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String maId = mList.get(vh.getLayoutPosition()).getMa_id();
                DscsProjectDetailsActivity.actionStart(MutualRepaymentActivity.this, DscsProjectDetailsActivity.FROM_MA, maId);
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateMutualRepay(LoginHelper.getInstance().getUserToken()), new ProgressSubscriber<MutualRepayBean>(mContext, new SubscriberOnNextListener<MutualRepayBean>() {
            @Override
            public void onNext(MutualRepayBean result) {
                if (result.getStatus() == 1) {
                    mList.addAll(result.getInfo());
                    mutualRepaymentAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                    if (result.getInfo().size() > 0) {
                        mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    } else {
                        mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                    }
                }
            }
        }));
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mutual_repayment;
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
