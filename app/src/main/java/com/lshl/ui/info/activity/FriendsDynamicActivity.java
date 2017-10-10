package com.lshl.ui.info.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.lshl.bean.DynamicBean;
import com.lshl.databinding.ActivityFriendsDynamicBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.me.activity.DynamicDetailsActivity;
import com.lshl.ui.me.adapter.DynamicsListAdapter;
import com.lshl.utils.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人的圈子动态
 */
public class FriendsDynamicActivity extends BaseActivity<ActivityFriendsDynamicBinding>
        implements SpringView.OnFreshListener {

    private String mUid;
    private String mReadlName;
    private List<DynamicBean.InfoBean.ListBean> mListData;

    private boolean isEnd;
    private DynamicsListAdapter mAdapter;

    public static void actionStart(Activity activity, String realName, String uid) {
        Intent intent = new Intent(activity, FriendsDynamicActivity.class);
        intent.putExtra("realname", realName);
        intent.putExtra("uid", uid);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mListData = new ArrayList<>();
        Intent intent = getIntent();
        mUid = intent.getStringExtra("uid");
        mReadlName = intent.getStringExtra("realname");
    }

    @Override
    protected void setupTitle() {
        setTextTitleView(mReadlName, DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        setListData(mListData);
        mDataBinding.springView.setListener(this);
        mDataBinding.springView.setHeader(new RotationHeader(mContext));
        mDataBinding.springView.setFooter(new RotationFooter(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new DynamicsListAdapter(mListData, this);
        mDataBinding.recyclerView.setAdapter(mAdapter);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                DynamicBean.InfoBean.ListBean bean = mListData.get(position);
                DynamicDetailsActivity.actionStart(FriendsDynamicActivity.this, bean, DynamicDetailsActivity.DynamicDetails);
            }
        });
        initLoadData();
    }

    @Override
    protected void loadListData(int page) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .dynamicPeopleList(LoginHelper.getInstance().getUserToken(), mUid, String.valueOf(page))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<DynamicBean>() {
                    @Override
                    public void onNext(DynamicBean result) {
                        if (result.getStatus().equals(ApiService.STATUS_SUC)) {
                            List<DynamicBean.InfoBean.ListBean> list = result.getInfo().getList();
                            isEnd = result.getInfo().getEnd() == 1;
                            mListData.addAll(list);
                            mAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();
                            if (list.size() > 0) {
                                mDataBinding.recyclerView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.windowBackgroundColor));
                            } else {
                                mDataBinding.recyclerView.setBackgroundResource(R.mipmap.kongkongruye);
                            }
                        } else {
                            Toast.makeText(mContext, "暂无数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DynamicDetailsActivity.DynamicDetails) {
                onRefresh();
            }
        }

    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friends_dynamic;
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
