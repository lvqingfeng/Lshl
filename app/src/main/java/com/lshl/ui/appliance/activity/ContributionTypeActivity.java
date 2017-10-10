package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

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
import com.lshl.bean.RankingBean;
import com.lshl.databinding.ActivityContributionTypeBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.adapter.AllRankAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.AddressSelectPopWindow;
import com.lshl.view.SearchContentPopWindow;

import java.util.ArrayList;
import java.util.List;

public class ContributionTypeActivity extends BaseActivity<ActivityContributionTypeBinding> implements SpringView.OnFreshListener {
    private List<RankingBean.InfoBean.ListBean> mList;
    private String title;
    private String type;
    private String mCityNo;
    private boolean isEnd;
    private String order = "up";
    private AddressSelectPopWindow addressSelectPopWindow;
    private AllRankAdapter allRankAdapter;
    private SearchContentPopWindow searchContentPopWindow;
    private PopupWindow popupWindow;

    public static void actionStart(Activity activity, String title, String type) {
        Intent intent = new Intent(activity, ContributionTypeActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        getRightView().setButtonDrawable(R.drawable.ic_vector_big_search);
    }

    @Override
    protected void initViews() {
        initPopuWindow();
        mDataBinding.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightOff();
                addressSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.shunxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAtLocation(mDataBinding.getRoot(), Gravity.BOTTOM, 0, 0);
            }
        });
        title = getIntent().getStringExtra("title");
        setTextTitleView(title, DEFAULT_TITLE_TEXT_COLOR);
        type = getIntent().getStringExtra("type");
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        setListData(mList);
        allRankAdapter = new AllRankAdapter(mList);
        mDataBinding.recyclerView.setAdapter(allRankAdapter);
        initLoadData();
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                SearchResultActivity.actionStart(ContributionTypeActivity.this, type, mList.get(vh.getLayoutPosition()).getDm_customer_uid());
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        loadAllData(page, type, null, null, order);
    }

    private void loadAllData(int p, String type, String info, String city, String order) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).updateRanking(String.valueOf(p), type, info, city, order),
                new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<RankingBean>() {
                    @Override
                    public void onNext(RankingBean result) {
                        if (result != null) {
                            isEnd = result.getInfo().getEnd() == 1;
                            mDataBinding.all.setText(result.getInfo().getTotalcontribution());
                            mList.addAll(result.getInfo().getList());
                            allRankAdapter.notifyDataSetChanged();
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

    private void initPopuWindow() {
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContentPopWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        addressSelectPopWindow = new AddressSelectPopWindow(ContributionTypeActivity.this);
        addressSelectPopWindow.setWheelChangeListener(new AddressSelectPopWindow.WheelChangeListener() {
            @Override
            public void onWheelSelected(String provinceNo, String cityNo, String provinceData, String cityData) {
                mDataBinding.address.setText("所在地:" + cityData);
                mCityNo = cityNo;
                mList.clear();
                loadAllData(1, type, null, cityNo, order);
            }
        });
        addressSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        searchContentPopWindow = new SearchContentPopWindow(ContributionTypeActivity.this);
        searchContentPopWindow.setEditHintSearch("请输入搜索内容");
        searchContentPopWindow.setSearchContentCallback(new SearchContentPopWindow.SearchContentCallback() {
            @Override
            public void onSearchCallback(String str) {
                mList.clear();
                loadAllData(1, type, str, null, order);
            }
        });
        View view = getLayoutInflater().inflate(R.layout.item_layout_shunxu_popu, null);
        popupWindow = new PopupWindow(mContext);
        popupWindow.setContentView(view);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        final RelativeLayout ll_parent = (RelativeLayout) view.findViewById(R.id.ll_shunxun_parent);
        final Button zheng = (Button) view.findViewById(R.id.item_popuwindow_zheng);
        final Button dao = (Button) view.findViewById(R.id.item_popupwindow_dao);
        final Button cancel = (Button) view.findViewById(R.id.item_popupwindow_shunxu_cancel);
        zheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = "up";
                mDataBinding.shunxun.setText("排列顺序:正序");
                mList.clear();
                loadAllData(1, type, null, mCityNo, order);
                popupWindow.dismiss();
                ll_parent.clearAnimation();
            }
        });
        dao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = "down";
                mDataBinding.shunxun.setText("排列顺序:倒序");
                mList.clear();
                loadAllData(1, type, null, mCityNo, order);
                popupWindow.dismiss();
                ll_parent.clearAnimation();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                ll_parent.clearAnimation();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contribution_type;
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    public void onRefresh() {
        mDataBinding.address.setText("所在地:" + "全国");
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }
}
