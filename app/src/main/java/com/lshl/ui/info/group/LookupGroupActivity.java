package com.lshl.ui.info.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

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
import com.lshl.bean.GroupListBean;
import com.lshl.databinding.ActivityLookupGroupBinding;
import com.lshl.db.bean.HxGroupBean;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.info.adapter.LookupGroupAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找群组
 */
public class LookupGroupActivity extends BaseActivity<ActivityLookupGroupBinding> {

    private static final String GROUP_KEY = "group_key";

    private List<HxGroupBean> mListData = new ArrayList<>();
    private LookupGroupAdapter mAdapter;

    private String mGroupKey;//搜索群的关键字，可以是群id/群名称和群的关键字


    private boolean isEnd;

    public static void actionStart(Fragment fragment, String groupKey, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), LookupGroupActivity.class);
        intent.putExtra(GROUP_KEY, groupKey);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected void initFieldBeforeMethods() {

        mGroupKey = getIntent().getStringExtra(GROUP_KEY);
        setListData(mListData);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("搜索结果", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class,
                        RetrofitManager.RetrofitType.Object)
                        .lookupGroupList(mGroupKey, String.valueOf(page)),
                new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<GroupListBean>() {
                    @Override
                    public void onNext(GroupListBean result) {
                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {

                            isEnd = result.getInfo().getEnd() == 1;

                            mListData.addAll(result.getInfo().getList());
                            mAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();

                        }
                    }
                })
        );
    }


    @Override
    protected void initViews() {
        initRecyclerView();
        initSpringView();
        initLoadData();
    }

    private void initSpringView() {
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                LookupGroupActivity.this.onRefresh(isEnd);
            }

            @Override
            public void onLoadmore() {
                onLoadMore(isEnd);
            }
        });
    }

    private void initRecyclerView() {
        mDataBinding.recyclerGroupList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new LookupGroupAdapter(mContext, mListData);
        mDataBinding.recyclerGroupList.setAdapter(mAdapter);
        mDataBinding.recyclerGroupList.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerGroupList) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                RequestJoinGroupActivity.actionStart(LookupGroupActivity.this, mListData.get(vh.getLayoutPosition()));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lookup_group;
    }


}
