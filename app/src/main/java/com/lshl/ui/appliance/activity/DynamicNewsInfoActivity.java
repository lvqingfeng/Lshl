package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.lshl.bean.DynamicBean;
import com.lshl.bean.DynamicNewsInfo;
import com.lshl.databinding.ActivityDynamicNewsInfoBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.me.activity.DynamicDetailsActivity;
import com.lshl.ui.me.adapter.DynamicNewsInfoAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

public class DynamicNewsInfoActivity extends BaseActivity<ActivityDynamicNewsInfoBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private List<DynamicNewsInfo.InfoBean.ListBean> mListData;
    private DynamicNewsInfoAdapter dynamicNewsInfoAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, DynamicNewsInfoActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("通知", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mListData = new ArrayList<>();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        dynamicNewsInfoAdapter = new DynamicNewsInfoAdapter(mListData, DynamicNewsInfoActivity.this);
        mDataBinding.recyclerView.setAdapter(dynamicNewsInfoAdapter);
        setListData(mListData);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String mdid = mListData.get(vh.getLayoutPosition()).getDn_mdid();
                loadOtherData(mdid);

            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此条记录?", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        deleteDynamicNewsinfo(mListData.get(vh.getLayoutPosition()).getDn_id());
                    }
                });
            }
        });
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateDynamicNewsInfo(LoginHelper.getInstance().getUserToken(), String.valueOf(page)), new ProgressSubscriber<DynamicNewsInfo>(mContext, new SubscriberOnNextListener<DynamicNewsInfo>() {
            @Override
            public void onNext(DynamicNewsInfo result) {
                if (result != null) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mListData.addAll(result.getInfo().getList());
                    dynamicNewsInfoAdapter.notifyDataSetChanged();
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

    private void loadOtherData(String mdid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).updateDynamicDetails(LoginHelper
                        .getInstance().getUserToken(), mdid + "")
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<DynamicBean>() {
                    @Override
                    public void onNext(DynamicBean result) {
                        DynamicBean.InfoBean.ListBean listBean = result.getInfo().getList().get(0);
                        DynamicDetailsActivity.actionStart(DynamicNewsInfoActivity.this, listBean, DynamicDetailsActivity.DynamicDetails);
                    }
                }));
    }

    private void deleteDynamicNewsinfo(String mDid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).deleteDynamicNewsinfos(LoginHelper.getInstance().getUserToken()
                , mDid), new Subscriber<HttpResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpResult<String> result) {
                Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                onRefresh();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dynamic_news_info;
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
