package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

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
import com.lshl.bean.LookHelpListBean;
import com.lshl.databinding.ActivityLookHelpListBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.adapter.LookHelpListAdapter;
import com.lshl.ui.me.activity.LookHelpDetailsActivity;
import com.lshl.ui.me.activity.SendLookHelpsActivity;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.AddressSelectPopWindow;

import java.util.ArrayList;
import java.util.List;

public class LookHelpListActivity extends BaseActivity<ActivityLookHelpListBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private String address = "全国";
    private List<LookHelpListBean.InfoBean.ListBean> mList;
    private LookHelpListAdapter lookHelpListAdapter;
    private AddressSelectPopWindow addressSelectPopWindow;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, LookHelpListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("找帮手", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setText("全国");
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                mList.clear();
                addressSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        lookHelpListAdapter = new LookHelpListAdapter(mList);
        mDataBinding.recyclerView.setAdapter(lookHelpListAdapter);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        setListData(mList);
        initLoadData();
        addressSelectPopWindow = new AddressSelectPopWindow(this);
        addressSelectPopWindow.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                setRightViewText(cityData, DEFAULT_TITLE_TEXT_COLOR);
                sendRequest(1, cityNo);
            }
        });
        addressSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                if (mList.size() <= 0) {
                    Toast.makeText(mContext, "暂时无法查看", Toast.LENGTH_SHORT).show();
                    return;
                }
                String fh_id = mList.get(vh.getLayoutPosition()).getFh_id();
                LookHelpDetailsActivity.actionStart(LookHelpListActivity.this, fh_id);
            }
        });
        mDataBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendLookHelpsActivity.actionStart(LookHelpListActivity.this, SendLookHelpsActivity.REQUEST_CODE_LOOK);
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        sendRequest(page, null);
    }

    private void sendRequest(int page, String cityNo) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateLookHelpList(null, String.valueOf(page), cityNo), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<LookHelpListBean>() {
            @Override
            public void onNext(LookHelpListBean result) {
                if (result.getStatus() == 1) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    lookHelpListAdapter.notifyDataSetChanged();
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
        return R.layout.activity_look_help_list;
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
        getRightView().setText("全国");
        initLoadData();
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SendLookHelpsActivity.REQUEST_CODE_LOOK) {
            if (resultCode == RESULT_OK) {
                onRefresh();
            }
        }
    }
}
