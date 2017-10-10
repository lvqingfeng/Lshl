package com.lshl.ui.info.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.RecommendFriendsBean;
import com.lshl.databinding.ActivityRecommendFriendBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.info.adapter.RecommendAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class RecommendFriendActivity extends BaseActivity<ActivityRecommendFriendBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private List<RecommendFriendsBean.InfoBean.ListBean> mList;
    private RecommendAdapter recommendAdapter;
    private BaiduMapLocationHelper locationHelper;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, RecommendFriendActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("附近的老乡", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        locationHelper = BaiduMapLocationHelper.getInstance().init(mContext);
        locationHelper.locationStart();
        mList = new ArrayList<>();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        recommendAdapter = new RecommendAdapter(mList, RecommendFriendActivity.this);
        mDataBinding.recyclerView.setAdapter(recommendAdapter);
        setListData(mList);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String uid = mList.get(vh.getLayoutPosition()).getUid();
                HxMemberDetailsActivity.actionStart(RecommendFriendActivity.this, "", uid, false);
            }
        });
        locationHelper.setLocationCallBack(new BaiduMapLocationHelper.OnLocationCallBack() {
            @Override
            public void callBackLatLng(BDLocation location, LatLng latLng) {
                getRightView().setText(location.getCity());
                updateJingwei(location.getLongitude(), location.getLatitude());
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .updateRecommendList(LoginHelper.getInstance().getUserToken(), String.valueOf(page))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<RecommendFriendsBean>() {
                    @Override
                    public void onNext(RecommendFriendsBean result) {
                        if (result.getStatus() == 1) {
                            isEnd = result.getInfo().getEnd() == 1;
                            mList.addAll(result.getInfo().getList());
                            recommendAdapter.notifyDataSetChanged();
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
        return R.layout.activity_recommend_friend;
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    private void updateJingwei(double jingdu, double weidu) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updatejingwei(LoginHelper.getInstance().getUserBean().getUid()
                , String.valueOf(jingdu) + "," + String.valueOf(weidu))
                , new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<String> result) {
                        if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                            onRefresh();
                        }
                    }
                });
    }
}
