package com.lshl.ui.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.lshl.base.SimpleBindingAdapter;
import com.lshl.bean.NewsCommentBean;
import com.lshl.databinding.ActivityNewsCommentBinding;
import com.lshl.databinding.NewsCommentItemBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsCommentActivity extends BaseActivity<ActivityNewsCommentBinding>
        implements SpringView.OnFreshListener {

    private List<NewsCommentBean.ListBean> mListData;
    private boolean isEnd;
    private String mId;
    private SimpleBindingAdapter<NewsCommentItemBinding, NewsCommentBean.ListBean> mAdapter;

    public static void actionStart(Activity activity, String id) {
        Intent intent = new Intent(activity, NewsCommentActivity.class);
        intent.putExtra("id", id);
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
        mId = getIntent().getStringExtra("id");
        mListData = new ArrayList<>();
        setListData(mListData);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void initViews() {
        initRefreshView();
        initLoadData();
    }

    private void initRefreshView() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 2, 0, 0);
            }
        });
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SimpleBindingAdapter<NewsCommentItemBinding, NewsCommentBean.ListBean>(mListData, R.layout.item_layout_news_comment) {
            @Override
            public void onBindViewHolder(NewsCommentItemBinding binding, int position) {
                //binding.ivAvatar.setVisibility(mListData.get(position).getAm_anonymous().equals("1") ? View.GONE : View.VISIBLE);
                final NewsCommentBean.ListBean commentBean = mListData.get(position);
                binding.setCommentBean(commentBean);
                binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> imageList = new ArrayList<>();
                        imageList.add(ApiClient.getHxFriendsImage(commentBean.getAm_anonymous().equals("1") ? "" : commentBean.getAvatar()));
                        PhotoShowActivity.actionStart(mContext, imageList, 0);
                    }
                });
            }
        };
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_comment;
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).newsCommentList(mId, String.valueOf(page))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<NewsCommentBean>>() {
                    @Override
                    public void onNext(HttpResult<NewsCommentBean> result) {
                        isEnd = result.getInfo().getEnd() == 1;
                        mListData.addAll(result.getInfo().getList());
                        mAdapter.notifyDataSetChanged();
                        mDataBinding.springView.onFinishFreshAndLoad();
                    }
                })
        );
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
