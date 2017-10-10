package com.lshl.ui.me.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

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
import com.lshl.bean.PraiseListBean;
import com.lshl.databinding.ActivityClickPraiseBinding;
import com.lshl.databinding.ClickPraiseTitleBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.adapter.DianzanAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 点赞
 */
public class ClickPraiseActivity extends BaseActivity<ActivityClickPraiseBinding>
        implements SpringView.OnFreshListener {

    private ClickPraiseTitleBinding mTitleBinding;
    private boolean isSeeMe;//是否查看自己赞的别人
    private List<PraiseListBean.ListBean> mListData;
    private boolean isEnd;
    private DianzanAdapter mAdapter;

    private int mWhoPraiseMeNum;

    public static void actionStart(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), ClickPraiseActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initFieldBeforeMethods() {
        mListData = new ArrayList<>();
        setListData(mListData);
    }

    @Override
    public void finish() {
        setResult(RESULT_OK, new Intent().putExtra("num", String.valueOf(mWhoPraiseMeNum)));
        super.finish();
    }

    @Override
    protected void setupTitle() {
        View titleView = setTitleCenterViewRes(R.layout.layout_click_praise_title, false);
        mTitleBinding = DataBindingUtil.bind(titleView);
        openTitleLeftView(true);
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void initViews() {

        mTitleBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_btn_left://谁赞过我
                        isSeeMe = false;
                        initLoadData();
                        break;
                    case R.id.radio_btn_right://我赞过谁
                        isSeeMe = true;
                        initLoadData();
                        break;
                }
            }
        });
        mTitleBinding.radioBtnLeft.setChecked(true);

        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);

        mAdapter = new DianzanAdapter(mListData, ClickPraiseActivity.this);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                PraiseListBean.ListBean bean = mListData.get(vh.getLayoutPosition());
                String uid = "";
                if (isSeeMe) {
                    uid = bean.getP_cuid();
                } else {
                    uid = bean.getP_puid();
                }
                HxMemberDetailsActivity.actionStart(ClickPraiseActivity.this, "", uid, false);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_click_praise;
    }

    @Override
    protected void loadListData(int page) {
        String token = LoginHelper.getInstance().getUserToken();
        if (isSeeMe) {
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).putPraise(token, String.valueOf(page))
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<PraiseListBean>>() {
                        @Override
                        public void onNext(HttpResult<PraiseListBean> result) {
                            isEnd = result.getInfo().getEnd() == 1;
                            List<PraiseListBean.ListBean> listBeen = result.getInfo().getList();
                            if (listBeen.size() > 0) {
                                mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            } else {
                                mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                            }
                            mListData.addAll(listBeen);
                            mAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();
                        }
                    })
            );
        } else {
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).getPraise(token, String.valueOf(page))
                    , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<PraiseListBean>>() {
                        @Override
                        public void onNext(HttpResult<PraiseListBean> result) {
                            isEnd = result.getInfo().getEnd() == 1;
                            List<PraiseListBean.ListBean> listBeen = result.getInfo().getList();
                            if (listBeen.size() > 0) {
                                mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            } else {
                                mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                            }
                            mWhoPraiseMeNum = listBeen.size();
                            mListData.addAll(listBeen);
                            mAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();
                        }
                    })
            );
        }
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
