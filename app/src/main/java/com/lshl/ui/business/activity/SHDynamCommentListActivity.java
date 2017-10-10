package com.lshl.ui.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.base.SimpleBindingAdapter;
import com.lshl.bean.SHDynamCommentBean;
import com.lshl.databinding.ActivityShdynamCommentListBinding;
import com.lshl.databinding.ShanghuiDynamCommentItemBinding;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 商会动态的评论列表
 */
public class SHDynamCommentListActivity extends BaseActivity<ActivityShdynamCommentListBinding>
        implements SpringView.OnFreshListener {

    private List<SHDynamCommentBean.ListBean> mListData;
    private String mBid;

    private boolean isEnd;
    private SimpleBindingAdapter<ShanghuiDynamCommentItemBinding, SHDynamCommentBean.ListBean> mAdapter;

    public static void actionStart(Activity activity, String bid) {
        Intent intent = new Intent(activity, SHDynamCommentListActivity.class);
        intent.putExtra("bid", bid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("评论列表", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mListData = new ArrayList<>();
        mBid = getIntent().getStringExtra("bid");
        setListData(mListData);
    }

    @Override
    protected void initViews() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SimpleBindingAdapter<ShanghuiDynamCommentItemBinding, SHDynamCommentBean.ListBean>(
                mListData, R.layout.item_layout_shanghui_dynam_comment) {
            @Override
            public void onBindViewHolder(ShanghuiDynamCommentItemBinding binding, int position) {

                final SHDynamCommentBean.ListBean bean = mListData.get(position);
                binding.setCommentBean(bean);
                binding.ivGrade.setImageResource(Constant.getGradeForResource(bean.getGrade()));
                binding.ivHead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bean.getAvatar() == null) {
                            return;
                        }
                        HxMemberDetailsActivity.actionStart(SHDynamCommentListActivity.this, "", bean.getUid(), false);
                    }
                });
            }
        };
        mDataBinding.recyclerView.setAdapter(mAdapter);
        initLoadData();
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getSHDynamicCommentList(mBid, String.valueOf(page))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<SHDynamCommentBean>>() {
                    @Override
                    public void onNext(HttpResult<SHDynamCommentBean> result) {
                        isEnd = result.getInfo().getEnd() == 1;
                        mListData.addAll(result.getInfo().getList());
                        mAdapter.notifyDataSetChanged();
                    }
                })
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shdynam_comment_list;
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
