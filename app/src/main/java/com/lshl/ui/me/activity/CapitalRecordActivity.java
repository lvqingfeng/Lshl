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
import com.lshl.bean.FundrecordBean;
import com.lshl.databinding.ActivityCapitalRecordBinding;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.me.adapter.FundRecordAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/*****
 * 资金记录
 */
public class CapitalRecordActivity extends BaseActivity<ActivityCapitalRecordBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private List<FundrecordBean.InfoBean.ListBean> mList;
    private FundRecordAdapter fundRecordAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CapitalRecordActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("资金记录", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        fundRecordAdapter = new FundRecordAdapter(mList);
        mDataBinding.recyclerView.setAdapter(fundRecordAdapter);
        setListData(mList);
        loadListData(1);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                final String dm_id = mList.get(vh.getLayoutPosition()).getDm_id();
                DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此条记录", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                                .deleteMoney(LoginHelper.getInstance().getUserToken(), dm_id), new Subscriber<HttpResult<String>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(HttpResult<String> result) {
                                if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                    onRefresh();
                                } else {
                                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(
                ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateFundRecord(LoginHelper.getInstance().getUserToken(),
                        String.valueOf(page)), new ProgressSubscriber<>(mContext,
                new SubscriberOnNextListener<FundrecordBean>() {
                    @Override
                    public void onNext(FundrecordBean result) {
                        if (result != null) {
                            isEnd = result.getInfo().getEnd() == 1;
                            mList.addAll(result.getInfo().getList());
                            fundRecordAdapter.notifyDataSetChanged();
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
        return R.layout.activity_capital_record;
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
