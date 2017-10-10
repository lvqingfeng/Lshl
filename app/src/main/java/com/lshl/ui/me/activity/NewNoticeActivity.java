package com.lshl.ui.me.activity;

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
import com.lshl.bean.NewNoticeBean;
import com.lshl.databinding.ActivityNewNoticeBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.me.adapter.NewNoticeAdapter;
import com.lshl.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 * 推送消息的列表
 */
public class NewNoticeActivity extends BaseActivity<ActivityNewNoticeBinding>
        implements SpringView.OnFreshListener {

    private boolean isEnd;
    private List<NewNoticeBean.ListBean> mListData;
    private NewNoticeAdapter mAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, NewNoticeActivity.class);
        activity.startActivity(intent);
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
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("通知消息", DEFAULT_TITLE_TEXT_COLOR);
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
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
        mAdapter = new NewNoticeAdapter(mListData);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                NewNoticeBean.ListBean listBean = mListData.get(vh.getLayoutPosition());
                listBean.setPi_status(0);
                mAdapter.notifyItemRangeChanged(vh.getLayoutPosition(), 1);

                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(
                                ApiService.class, RetrofitManager.RetrofitType.Object
                        ).lookUpPushInfo(LoginHelper.getInstance().getUserToken(), listBean.getPi_id())
                        , new Subscriber<HttpResult<String>>() {
                            @Override
                            public void onCompleted() {


                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(HttpResult<String> stringHttpResult) {

                            }
                        }
                );
                NoticeInfoActivity.actionStart(NewNoticeActivity.this, listBean);
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                final String piId = mListData.get(vh.getLayoutPosition()).getPi_id();
                DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此条信息?", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        deleteNewNotice(piId);
                    }
                });
            }
        });
    }

    private void deleteNewNotice(String pid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .deleteNewNotice(LoginHelper.getInstance().getUserToken(), pid), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                    onRefresh();
                } else {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    @Override
    protected void loadListData(int page) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).getMyPushList(LoginHelper.getInstance().getUserToken(), String.valueOf(page)), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<NewNoticeBean>>() {
                    @Override
                    public void onNext(HttpResult<NewNoticeBean> result) {
                        mListData.addAll(result.getInfo().getList());
                        isEnd = result.getInfo().getEnd() == 1;
                        if (result.getInfo().getList().size() > 0) {
                            mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                        }
                        mAdapter.notifyDataSetChanged();
                        mDataBinding.springView.onFinishFreshAndLoad();
                    }
                })
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_notice;
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
