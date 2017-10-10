package com.lshl.ui.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.base.SimpleBindingAdapter;
import com.lshl.bean.SHTalkAboutListBean;
import com.lshl.databinding.ActivityShtalkAboutBinding;
import com.lshl.databinding.SHTalkAboutItemBinding;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.view.SendOpinionPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 咨询商会
 */
public class SHTalkAboutActivity extends BaseActivity<ActivityShtalkAboutBinding>
        implements SpringView.OnFreshListener {

    private List<SHTalkAboutListBean.ListBean> mListData;
    private SimpleBindingAdapter<SHTalkAboutItemBinding, SHTalkAboutListBean.ListBean> mAdapter;

    private String mBusinessName;
    private String mBid;
    private boolean isEnd;

    private SendOpinionPopWindow mSendQuestionPop;

    public static void actionStart(Activity activity, String businessName, String bid) {
        Intent intent = new Intent(activity, SHTalkAboutActivity.class);
        intent.putExtra("name", businessName);
        intent.putExtra("bid", bid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("对话商会", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mListData = new ArrayList<>();
        setListData(mListData);
        Intent intent = getIntent();
        mBusinessName = intent.getStringExtra("name");
        mBid = intent.getStringExtra("bid");
        mSendQuestionPop = new SendOpinionPopWindow(this, new SendOpinionPopWindow.SendOpinionCallback() {
            @Override
            public void onCallback(String inputText, boolean isAnonymous) {
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.Object
                        ).businessContact(LoginHelper.getInstance().getUserToken(), mBid, inputText)
                        , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                            @Override
                            public void onNext(HttpResult<String> result) {
                                showMessage("提问成功");
                                onRefresh();
                            }
                        })
                );
            }
        });
        mSendQuestionPop.setAnonymousGone();
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
        mDataBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 20);
            }
        });
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SimpleBindingAdapter<SHTalkAboutItemBinding, SHTalkAboutListBean.ListBean>(mListData
                , R.layout.item_layout_sh_talk_about) {

            @Override
            public void onBindViewHolder(SHTalkAboutItemBinding binding, final int position) {
                binding.setBusinessName(mBusinessName);

                binding.setTalkAboutBean(mListData.get(position));
                Constant.getGradeForAll(mListData.get(position).getGrade(), binding.image);
                binding.headImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(mListData.get(position).getBct_uid())) {
                            HxMemberDetailsActivity.actionStart(SHTalkAboutActivity.this, "", mListData.get(position).getUid(), false);
                        } else {
                            HxMemberDetailsActivity.actionStart(SHTalkAboutActivity.this, "", mListData.get(position).getBct_uid(), false);
                        }
                    }
                });
            }
        };
        mDataBinding.recyclerView.setAdapter(mAdapter);
        initLoadData();
        mDataBinding.btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSendQuestionPop.showPopupWindow(mDataBinding.getRoot());
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getSHTalkAboutList(LoginHelper.getInstance().getUserToken(), mBid, String.valueOf(page))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<SHTalkAboutListBean>>() {
                    @Override
                    public void onNext(HttpResult<SHTalkAboutListBean> result) {
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
        return R.layout.activity_shtalk_about;
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
