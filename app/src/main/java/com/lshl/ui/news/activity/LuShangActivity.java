package com.lshl.ui.news.activity;

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
import com.lshl.bean.LuShangBean;
import com.lshl.databinding.ActivityLuShangBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.news.adapter.LuShangAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class LuShangActivity extends BaseActivity<ActivityLuShangBinding> implements SpringView.OnFreshListener {
    private boolean isEnd;
    private String type;
    private String title;
    private List<LuShangBean.InfoBean.ListBean> mListData;
    private LuShangAdapter luShangAdapter;

    public static void actionStart(Activity activity, String type, String title) {
        Intent intent = new Intent(activity, LuShangActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("title", title);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        setTextTitleView(title, DEFAULT_TITLE_TEXT_COLOR);
        mListData = new ArrayList<>();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recycler.setLayoutManager(manager);
        mDataBinding.recycler.addItemDecoration(new DividerGridItemDecoration(mContext));
        setListData(mListData);
        luShangAdapter = new LuShangAdapter(mListData);
        mDataBinding.recycler.setAdapter(luShangAdapter);
        initLoadData();
        mDataBinding.recycler.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recycler) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                LuShangBean.InfoBean.ListBean bean = mListData.get(vh.getLayoutPosition());
                NewsInfoActivity.actionStart(LuShangActivity.this, bean.getId(), ApiService.BASE_URL + bean.getImg());
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateLuShangList(type, String.valueOf(page)), new ProgressSubscriber<LuShangBean>(mContext, new SubscriberOnNextListener<LuShangBean>() {
            @Override
            public void onNext(LuShangBean result) {
                if (result.getStatus() == 1) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mListData.addAll(result.getInfo().getList());
                    luShangAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                    if (result.getInfo().getList().size() > 0) {
                        mDataBinding.recycler.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    } else {
                        mDataBinding.recycler.setBackgroundResource(R.mipmap.kongkongruye);
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
        return R.layout.activity_lu_shang;
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
