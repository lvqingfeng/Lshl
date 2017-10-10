package com.lshl.ui.info.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hyphenate.easeui.EaseConstant;
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
import com.lshl.bean.RadarListBean;
import com.lshl.databinding.ActivityRadarListBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.recycler_listener.OnRecyclerItemLongClickListener;
import com.lshl.ui.info.adapter.RadarListAdapter;
import com.lshl.ui.info.chat.ChatActivity;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RadarListActivity extends BaseActivity<ActivityRadarListBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private List<RadarListBean.InfoBean.ListBean> mList;
    private RadarListAdapter radarListAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, RadarListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("好友雷达", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        mList = new ArrayList<>();
        setListData(mList);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        radarListAdapter = new RadarListAdapter(mList);
        mDataBinding.recyclerView.setAdapter(radarListAdapter);
        initLoadData();
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateRadarList(LoginHelper.getInstance().getUserToken(), String.valueOf(page)), new ProgressSubscriber<RadarListBean>(mContext, new SubscriberOnNextListener<RadarListBean>() {
            @Override
            public void onNext(RadarListBean result) {
                if (result != null) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    radarListAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                    if (result.getInfo().getList().size() > 0) {
                        mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    } else {
                        mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                    }
                }
            }
        }));
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                ChatActivity.actionStart(RadarListActivity.this, null, mList.get(vh.getLayoutPosition()).getHx_id(), EaseConstant.CHATTYPE_SINGLE, 0);
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemLongClick(final RecyclerView.ViewHolder vh) {
                final String pi_id = mList.get(vh.getLayoutPosition()).getPi_id();
                DialogUtils.alertDialog(mContext, "温馨提示", "您确定要删除此条信息?", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        deleteRadar(pi_id);
                    }
                });
            }
        });
    }

    private void deleteRadar(String pid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).deleteRadar(LoginHelper.getInstance().getUserToken(), pid), new ProgressSubscriber<HttpResult<String>>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_radar_list;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
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
