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
import com.lshl.base.HttpResult;
import com.lshl.bean.CityQiyeBean;
import com.lshl.databinding.ActivityCompanyListBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.adapter.CompanyAdapter;
import com.lshl.ui.me.activity.CompanySecondActivity;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.AddressSelectPopWindow;

import java.util.ArrayList;
import java.util.List;

public class CompanyListActivity extends BaseActivity<ActivityCompanyListBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private String mInfo = "";
    private String mCityId = "0";
    private List<CityQiyeBean.ListBean> mList;
    private CompanyAdapter companyAdapter;
    private AddressSelectPopWindow addressSelectPopWindow;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, CompanyListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("企业", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
        setRightViewText("全国", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightOff();
                addressSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
    }

    @Override
    protected void initViews() {
        addressSelectPopWindow = new AddressSelectPopWindow(this);
        addressSelectPopWindow.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                setRightViewText(cityData, DEFAULT_TITLE_TEXT_COLOR);
                mCityId = cityNo;
                mList.clear();
                onRefresh();
            }
        });
        addressSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mDataBinding.tvSearchGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchInfo = mDataBinding.editSearchContent.getText().toString().trim();
                if (TextUtils.isEmpty(searchInfo)) {
                    Toast.makeText(mContext, "搜索内容为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mInfo = searchInfo;
                    onRefresh();
                }

            }
        });

        mList = new ArrayList<>();
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        setListData(mList);
        companyAdapter = new CompanyAdapter(mList);
        mDataBinding.recyclerView.setAdapter(companyAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                CompanySecondActivity.actionStart(CompanyListActivity.this, mList.get(vh.getLayoutPosition()).getEn_id());
            }
        });
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateBusinessQiYe("1", mInfo, "", mCityId, String.valueOf(page)), new ProgressSubscriber<HttpResult<CityQiyeBean>>(mContext, new SubscriberOnNextListener<HttpResult<CityQiyeBean>>() {
            @Override
            public void onNext(HttpResult<CityQiyeBean> result) {
                isEnd = result.getInfo().getEnd() == 1;
                mList.addAll(result.getInfo().getList());
                companyAdapter.notifyDataSetChanged();
                mDataBinding.springView.onFinishFreshAndLoad();
                if (result.getInfo().getList().size() > 0) {
                    mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                } else {
                    mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_list;
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
