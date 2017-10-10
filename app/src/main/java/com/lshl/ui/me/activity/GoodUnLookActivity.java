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
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.GoodUnLookBean;
import com.lshl.databinding.ActivityGoodUnLookBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.adapter.GoodUnLookAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class GoodUnLookActivity extends BaseActivity<ActivityGoodUnLookBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private String gd_id;
    private List<GoodUnLookBean.InfoBean.ListBean> mList;
    private GoodUnLookAdapter goodUnLookAdapter;

    public static void actionStart(Activity activity, String gd_id) {
        Intent intent = new Intent(activity, GoodUnLookActivity.class);
        intent.putExtra("gd_id", gd_id);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("关注了我", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        gd_id = getIntent().getStringExtra("gd_id");
        mList = new ArrayList<>();
        mDataBinding.springView.setListener(this);
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        setListData(mList);
        goodUnLookAdapter = new GoodUnLookAdapter(mList, GoodUnLookActivity.this);
        mDataBinding.recyclerView.setAdapter(goodUnLookAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                HxMemberDetailsActivity.actionStart(GoodUnLookActivity.this, "", mList.get(vh.getLayoutPosition()).getUid(), false);
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateGoodUnLook(gd_id), new ProgressSubscriber<GoodUnLookBean>(mContext, new SubscriberOnNextListener<GoodUnLookBean>() {
            @Override
            public void onNext(GoodUnLookBean result) {
                if (result.getStatus() == 1) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    goodUnLookAdapter.notifyDataSetChanged();
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
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_un_look;
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
