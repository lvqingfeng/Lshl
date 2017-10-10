package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

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
import com.lshl.databinding.ActivityGoodsCommentBinding;
import com.lshl.ui.me.adapter.GoodsCommentAdapter;
import com.lshl.bean.GoodsCommentBean;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class GoodsCommentActivity extends BaseActivity<ActivityGoodsCommentBinding> {
    private List<GoodsCommentBean.InfoBean.ListBean> mList;
    private GoodsCommentAdapter goodsCommentAdapter;
    private String gdid;

    public static void actionStart(Activity activity, String gdid) {
        Intent intent = new Intent(activity, GoodsCommentActivity.class);
        intent.putExtra("gdid", gdid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("商品评论", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        gdid = getIntent().getStringExtra("gdid");
        mDataBinding.spComment.setHeader(new RotationHeader(mContext));
        mDataBinding.spComment.setFooter(new RotationFooter(mContext));
        mDataBinding.spComment.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadmore() {

            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mDataBinding.recyclerComment.setLayoutManager(manager);
        mDataBinding.recyclerComment.setItemAnimator(new DefaultItemAnimator());
        mDataBinding.recyclerComment.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        goodsCommentAdapter = new GoodsCommentAdapter(mList);
        mDataBinding.recyclerComment.setAdapter(goodsCommentAdapter);
        loadData();
    }

    private void loadData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateGoodsComment(gdid, null), new ProgressSubscriber<GoodsCommentBean>(mContext, new SubscriberOnNextListener<GoodsCommentBean>() {
            @Override
            public void onNext(GoodsCommentBean result) {
                List<GoodsCommentBean.InfoBean.ListBean> list = result.getInfo().getList();
                mList.addAll(list);
                goodsCommentAdapter.notifyDataSetChanged();
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_comment;
    }
}
