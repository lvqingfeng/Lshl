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
import com.lshl.base.HttpResult;
import com.lshl.bean.LookMeBean;
import com.lshl.databinding.ActivityLookMeDetailsBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.adapter.LooKMeAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class LookMeActivity extends BaseActivity<ActivityLookMeDetailsBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private List<LookMeBean.InfoBean.ListBean> mList;
    private LooKMeAdapter looKMeAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, LookMeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("谁看过我", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        looKMeAdapter = new LooKMeAdapter(mList, LookMeActivity.this);
        setListData(mList);
        mDataBinding.recyclerView.setAdapter(looKMeAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String uid = mList.get(vh.getLayoutPosition()).getRe_active_look();
                HxMemberDetailsActivity.actionStart(LookMeActivity.this, "", uid, false);
            }
        });
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateLookMeList(LoginHelper.getInstance().getUserToken(), String.valueOf(page)), new ProgressSubscriber<LookMeBean>(mContext, new SubscriberOnNextListener<LookMeBean>() {
            @Override
            public void onNext(LookMeBean result) {
                if (result.getStatus() == 1) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    looKMeAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                    if (result.getInfo().getList().size() > 0) {
                        mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    } else {
                        mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                    }
                    commitLookMe();
                }
            }
        }));
    }

    private void commitLookMe() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).commitLookMe(LoginHelper.getInstance().getUserToken()), new Subscriber<HttpResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpResult<String> result) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_look_me_details;
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
