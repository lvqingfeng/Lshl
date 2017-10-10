package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.CompanySecondBean;
import com.lshl.databinding.ActivityCompanySecondBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.appliance.job.RecruitDetailsActivity;
import com.lshl.ui.me.adapter.CompanyRecruitAdapter;
import com.lshl.ui.me.adapter.QiyeGoodsAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CompanySecondActivity extends BaseActivity<ActivityCompanySecondBinding> {
    private List<CompanySecondBean.InfoBean.GoodBean> goodList;
    private List<CompanySecondBean.InfoBean.EnBean> companyList;
    private List<CompanySecondBean.InfoBean.ErBean> recuritList;
    private QiyeGoodsAdapter qiyeGoodsAdapter;
    private CompanyRecruitAdapter companyRecruitAdapter;
    private String enid;

    public static void actionStart(Activity activity, String enid) {
        Intent intent = new Intent(activity, CompanySecondActivity.class);
        intent.putExtra("enid", enid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("企业信息", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        enid = getIntent().getStringExtra("enid");
        goodList = new ArrayList<>();
        companyList = new ArrayList<>();
        recuritList = new ArrayList<>();
        int a[] = {R.color.text_red_color, R.color.text_blue, R.color.text_black, R.color.orange};
        mDataBinding.swipeRefresh.setColorSchemeColors(a);
        mDataBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                goodList.clear();
                recuritList.clear();
                companyList.clear();
                mDataBinding.swipeRefresh.setRefreshing(false);
                loadBaseData();
            }
        });
        initRecyclerView();
        loadBaseData();
        mDataBinding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterPriseDetailsActivity.actionStart(CompanySecondActivity.this, enid);
            }
        });
    }

    private void loadBaseData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateCompanySecondList(enid), new ProgressSubscriber<CompanySecondBean>(mContext, new SubscriberOnNextListener<CompanySecondBean>() {

            @Override
            public void onNext(CompanySecondBean result) {
                if (result.getStatus() == 1) {
                    goodList.addAll(result.getInfo().getGood());
                    qiyeGoodsAdapter.notifyDataSetChanged();
                    if (goodList.size() <= 0) {
                        mDataBinding.llGoods.setVisibility(View.GONE);
                    }
                    recuritList.addAll(result.getInfo().getEr());
                    companyRecruitAdapter.notifyDataSetChanged();
                    if (recuritList.size() <= 0) {
                        mDataBinding.llRecruit.setVisibility(View.GONE);
                    }
                    CompanySecondBean.InfoBean.EnBean en = result.getInfo().getEn();
                    mDataBinding.title.setText(en.getEn_name());
                    mDataBinding.info.setText(en.getEn_info());
                    Glide.with(mContext).load(ApiClient.getEnterPriseImage(en.getEn_img())).error(R.drawable.account_logo).into(mDataBinding.headImage);
                }
            }
        }));
    }

    private void initRecyclerView() {
        LinearLayoutManager goodsManner = new LinearLayoutManager(mContext);
        mDataBinding.recyclerGoods.setLayoutManager(goodsManner);
        mDataBinding.recyclerGoods.addItemDecoration(new DividerGridItemDecoration(mContext));
        qiyeGoodsAdapter = new QiyeGoodsAdapter(goodList);
        mDataBinding.recyclerGoods.setAdapter(qiyeGoodsAdapter);
        mDataBinding.recyclerGoods.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerGoods) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                GoodsDetailsActivity.actionStart(CompanySecondActivity.this, goodList.get(vh.getLayoutPosition()).getGd_id(), GoodsDetailsActivity.FROM_OTHER);
            }
        });

        mDataBinding.recyclerRecruit.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerRecruit.addItemDecoration(new DividerGridItemDecoration(mContext));
        companyRecruitAdapter = new CompanyRecruitAdapter(recuritList);
        mDataBinding.recyclerRecruit.setAdapter(companyRecruitAdapter);
        mDataBinding.recyclerRecruit.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerRecruit) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                RecruitDetailsActivity.actionStart(CompanySecondActivity.this, recuritList.get(vh.getLayoutPosition()).getEr_id(), RecruitDetailsActivity.FROM_OTHERS);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_second;
    }
}
