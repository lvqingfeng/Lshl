package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.HttpResult;
import com.lshl.bean.IndustryBean;
import com.lshl.databinding.ActivityIndustryBinding;
import com.lshl.ui.me.adapter.ChooseIndustryAdapter;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IndustryActivity extends BaseActivity<ActivityIndustryBinding> {
    private List<IndustryBean.InfoBean> mList;
    private ArrayList<IndustryBean.InfoBean> newList;
    private ChooseIndustryAdapter chooseIndustryAdapter;
    private List<String> mListData;
    private String sb;
    private int nums;

    public static void actionStart(Activity activity, String sb, int nums, int requestCode) {
        Intent intent = new Intent(activity, IndustryActivity.class);
        intent.putExtra("sb", sb);
        intent.putExtra("nums", nums);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("行业分类", DEFAULT_TITLE_TEXT_COLOR);
        setRightViewText("保存", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        sb = getIntent().getStringExtra("sb");
        nums = getIntent().getIntExtra("nums", -1);
        mList = new ArrayList<>();
        mListData = new ArrayList<>();
        newList = new ArrayList<>();
        final LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mDataBinding.recyclerView.setLayoutManager(manager);
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        chooseIndustryAdapter = new ChooseIndustryAdapter(mList);
        mDataBinding.recyclerView.setAdapter(chooseIndustryAdapter);
        loadBaseData();
//        chooseIndustryAdapter.setRecyclerViewOnItemClickListener(new ChooseIndustryAdapter.RecyclerViewOnItemClickListener() {
//            @Override
//            public void onItemClickListener(View view, int position) {
//                chooseIndustryAdapter.setSelectItem(position);
//                chooseIndustryAdapter.notifyDataSetChanged();
//            }
//        });
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Integer, Boolean> map = chooseIndustryAdapter.getMap();
                mListData.clear();
                newList.clear();
                for (int i = 0; i < map.size(); i++) {
                    if (map.get(i)) {
                        newList.add(mList.get(i));
                        mListData.add(mList.get(i).getIn_id() + "-" + mList.get(i).getIn_name());
                        Log.d("屮艸芔茻", "你选了第：" + i + "项");
                    }
                }
                if (newList.size() > 0) {
                    if (newList.size() + nums < 6 || mListData.size() + nums < 6) {
                        StringBuilder builder = new StringBuilder();
                        ListUtils.appendIdList(mListData, builder);
                        if (sb.length() <= 0) {
                            updateIndustry(builder.toString());
                        } else {
                            updateIndustry(builder.toString() + "," + sb);
                        }
                    } else {
                        Toast.makeText(mContext, "行业类型不能超过五个(⊙o⊙)", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "请选择行业", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateIndustry(String industry) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .setIndustry(LoginHelper.getInstance().getUserToken(), industry), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                if (ApiService.STATUS_SUC.equals(result.getStatus())) {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(mContext, result.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    private void loadBaseData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateIndustry(""), new ProgressSubscriber<IndustryBean>(mContext, new SubscriberOnNextListener<IndustryBean>() {
            @Override
            public void onNext(IndustryBean result) {
                if (result.getStatus() == 1) {
                    mList.addAll(result.getInfo());
                    chooseIndustryAdapter.notifyDataSetChanged();
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_industry;
    }
}
