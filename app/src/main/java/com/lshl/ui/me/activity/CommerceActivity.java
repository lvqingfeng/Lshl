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
import com.lshl.bean.AleradyCommerceBean;
import com.lshl.databinding.ActivityCommerceBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.business.activity.ShangHuiDetailsActivity;
import com.lshl.ui.me.adapter.AlreadyCommerceAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心我的商会
 */
public class CommerceActivity extends BaseActivity<ActivityCommerceBinding> {
    private boolean isEnd;
    private List<AleradyCommerceBean.InfoBean.ListBean> mList;
    private AlreadyCommerceAdapter alreadyCommerceAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CommerceActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("我的商会", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                CommerceActivity.this.onRefresh(isEnd);
            }

            @Override
            public void onLoadmore() {
                onLoadMore(isEnd);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        alreadyCommerceAdapter = new AlreadyCommerceAdapter(mList);
        mDataBinding.recyclerView.setAdapter(alreadyCommerceAdapter);

        setListData(mList);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                AleradyCommerceBean.InfoBean.ListBean bean = mList.get(position);
                ShangHuiDetailsActivity.actionStart(CommerceActivity.this, null, bean.getBus_id(), bean.getBus_business_name(), true, 0);
            }
        });
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).aleradyJoinShanghui(LoginHelper.getInstance().getUserToken(), page + ""), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<AleradyCommerceBean>() {
            @Override
            public void onNext(AleradyCommerceBean result) {
                if (result != null) {
                    isEnd = result.getStatus() == 1;
                    mList.addAll(result.getInfo().getList());
                    alreadyCommerceAdapter.notifyDataSetChanged();
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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_commerce;
    }
}
