package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lshl.BaiduMapLocationHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.ServiceStationBean;
import com.lshl.databinding.ActivityHotServiceBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.adapter.StationListAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.AddressSelectPopWindow;

import java.util.ArrayList;
import java.util.List;

public class HotServiceActivity extends BaseActivity<ActivityHotServiceBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private AddressSelectPopWindow mPopWindow;
    private List<ServiceStationBean.InfoBean.ListBean> mList;
    private StationListAdapter stationListAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, HotServiceActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaiduMapLocationHelper.getInstance().init(mContext).locationStart();
        BaiduMapLocationHelper.getInstance().setLocationCallBack(new BaiduMapLocationHelper.OnLocationCallBack() {
            @Override
            public void callBackLatLng(BDLocation location, LatLng latLng) {
                getRightView().setText(location.getCity());
            }
        });
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("同城", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setText("定位中...");
    }

    @Override
    protected void initViews() {
        mPopWindow = new AddressSelectPopWindow(HotServiceActivity.this);
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotServiceActivity.this.lightOff();
                mPopWindow.addressReset();
                mPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mPopWindow.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                getRightView().setText(cityData);
                mList.clear();
                loadData(null, cityNo, 1);
            }
        });
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                HotServiceActivity.this.lightOn();
            }
        });
        mDataBinding.tvSearchGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mDataBinding.editSearchContent.getText())) {
                    String info = mDataBinding.editSearchContent.getText().toString();
                    mList.clear();
                    loadData(info, null, 1);
                }
            }
        });
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        stationListAdapter = new StationListAdapter(mList);
        mDataBinding.recyclerView.setAdapter(stationListAdapter);
        loadListData(1);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String name = mList.get(vh.getLayoutPosition()).getSs_name();
                String ss_id = mList.get(vh.getLayoutPosition()).getSs_id();
                ServiceStationActivity.actionStart(HotServiceActivity.this, name, ss_id);
            }
        });
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void loadListData(int page) {
        loadData(null, null, 1);
    }

    private void loadData(String info, String cityno, int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateServiceList(info, cityno, String.valueOf(page)), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ServiceStationBean>() {
            @Override
            public void onNext(ServiceStationBean result) {
                if (result != null) {
                    isEnd = result.getInfo().getEnd() == 1;
                    mList.addAll(result.getInfo().getList());
                    stationListAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                    if (result.getInfo().getList().size() <= 0) {
                        mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                    } else {
                        mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                    }
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot_service;
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
