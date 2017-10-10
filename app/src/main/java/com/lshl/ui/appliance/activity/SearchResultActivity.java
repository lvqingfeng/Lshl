package com.lshl.ui.appliance.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.SearchResultBean;
import com.lshl.databinding.ActivitySearchResultBinding;
import com.lshl.ui.appliance.adapter.SearchResultAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends BaseActivity<ActivitySearchResultBinding> {
    private String type;
    private String uid;
    private String name = "";
    private String cityname = "";
    private List<SearchResultBean.InfoBean.ListBean> mList;
    private SearchResultAdapter searchResultAdapter;

    public static void actionStart(Activity activity, String type, String uid) {
        Intent intent = new Intent(activity, SearchResultActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("uid", uid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("搜索结果", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        type = getIntent().getStringExtra("type");
        uid = getIntent().getStringExtra("uid");
        LinearLayoutManager manager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mList = new ArrayList<>();
        searchResultAdapter = new SearchResultAdapter(mList);
        mDataBinding.recyclerView.setAdapter(searchResultAdapter);
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).updateSearchResult(type, uid)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<SearchResultBean>() {
                    @Override
                    public void onNext(SearchResultBean result) {
                        if (result != null) {
                            name = result.getInfo().getName();
                            cityname = result.getInfo().getCityname();
                            Glide.with(mContext).load(ApiService.BASE_URL + result.getInfo().getImg()).transform(new GlideCircleTransform(mContext)).error(R.mipmap.account_logo).into(mDataBinding.headImage);
                            mDataBinding.info.setText(name + "\n" + cityname);
                            mDataBinding.num.setText("累计捐助(次)" + result.getInfo().getAll_num());
                            mDataBinding.money.setText("累计金额(元)" + result.getInfo().getAll_money());
                            mList.addAll(result.getInfo().getList());
                            searchResultAdapter.notifyDataSetChanged();
                        }
                    }
                }));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }
}
