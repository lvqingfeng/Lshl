package com.lshl.ui.appliance.activity;

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
import com.lshl.bean.YouqingBean;
import com.lshl.databinding.ActivitySpecificShoppingBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.adapter.YouqingAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/******
 * 友情链接列表
 */
public class SpecificShoppingActivity extends BaseActivity<ActivitySpecificShoppingBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd = true;
    private List<YouqingBean.InfoBean> mListData;
    private YouqingAdapter youqingAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SpecificShoppingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("友情链接", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mListData = new ArrayList<>();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        youqingAdapter = new YouqingAdapter(mListData, SpecificShoppingActivity.this);
        mDataBinding.recyclerView.setAdapter(youqingAdapter);
        setListData(mListData);
        initLoadData();
        //列表的点击事件
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                SpecificDetailsActivity.actionStart(SpecificShoppingActivity.this, mListData.get(vh.getLayoutPosition()).getUrl());
            }
        });
    }

    //刷新
    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    //加载数据
    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateyouqing(""), new ProgressSubscriber<YouqingBean>(mContext, new SubscriberOnNextListener<YouqingBean>() {
            @Override
            public void onNext(YouqingBean result) {
                if (result != null) {
                    if (result.getStatus() == 1) {
                        mListData.addAll(result.getInfo());
                        youqingAdapter.notifyDataSetChanged();
                        if (result.getInfo().size() > 0) {
                            mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                        } else {
                            mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                        }
                    }
                } else {
                    mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_specific_shopping;
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
