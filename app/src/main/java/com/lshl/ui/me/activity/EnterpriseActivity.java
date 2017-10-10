package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.lshl.bean.EnterpeiseBean;
import com.lshl.databinding.ActivityEnterpriseBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.me.adapter.EnterpriseAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EnterpriseActivity extends BaseActivity<ActivityEnterpriseBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd = true;
    private List<EnterpeiseBean.InfoBean> mList;
    private EnterpriseAdapter enterpriseAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, EnterpriseActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("企业", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_add);
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEnterpriseActivity.actionStart(EnterpriseActivity.this);
            }
        });
    }

    @Override
    protected void initViews() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        setListData(mList);
        enterpriseAdapter = new EnterpriseAdapter(mList, EnterpriseActivity.this);
        mDataBinding.recyclerView.setAdapter(enterpriseAdapter);
        loadListData(1);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                EnterPriseDetailsActivity.actionStart(EnterpriseActivity.this, mList.get(vh.getLayoutPosition()).getEn_id());
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此条信息", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).deleteQiye(LoginHelper.getInstance().getUserToken(), mList.get(vh.getLayoutPosition()).getEn_id()), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
                            @Override
                            public void onNext(HttpResult<String> result) {
                                if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                    onRefresh();
                                } else {
                                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }));
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
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class,
                RetrofitManager.RetrofitType.Object).updateMyEnterprise(
                LoginHelper.getInstance().getUserToken(), null),
                new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<EnterpeiseBean>() {
                    @Override
                    public void onNext(EnterpeiseBean result) {
                        if (result != null) {
                            List<EnterpeiseBean.InfoBean> list = result.getInfo();
                            mList.addAll(list);
                            enterpriseAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();
                            if (list.size() > 0) {
                                mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            } else {
                                mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                            }
                        }
                    }
                }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enterprise;
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
