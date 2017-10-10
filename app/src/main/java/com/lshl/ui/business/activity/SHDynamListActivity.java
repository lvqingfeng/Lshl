package com.lshl.ui.business.activity;

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
import com.lshl.base.HttpResult;
import com.lshl.bean.SHDynamListBean;
import com.lshl.databinding.ActivityShdynamListBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.business.adapter.SHDynamListAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 商会动态列表
 */
public class SHDynamListActivity extends BaseActivity<ActivityShdynamListBinding>
        implements SpringView.OnFreshListener {

    private List<SHDynamListBean.ListBean> mListData;
    private SHDynamListAdapter mAdapter;
    private String mBuid;
    private boolean isEnd;

    public static void actionStart(Activity activity, String buid) {
        Intent intent = new Intent(activity, SHDynamListActivity.class);
        intent.putExtra("buid", buid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("动态列表", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mListData = new ArrayList<>();
        setListData(mListData);
        mBuid = getIntent().getStringExtra("buid");
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
        mAdapter = new SHDynamListAdapter(mListData);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String id = mListData.get(vh.getLayoutPosition()).getBd_id();
                SHDynamDetailsicActivity.actionStart(SHDynamListActivity.this, id);
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getSHDynamList(mBuid, String.valueOf(page))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<SHDynamListBean>>() {
                    @Override
                    public void onNext(HttpResult<SHDynamListBean> result) {
                        isEnd = result.getInfo().getEnd() == 1;
                        mListData.addAll(result.getInfo().getList());
                        mAdapter.notifyDataSetChanged();
                        mDataBinding.springView.onFinishFreshAndLoad();
                        if (result.getInfo().getList().size() > 0) {
                            mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                        } else {
                            mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                        }
                    }
                })
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shdynam_list;
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
